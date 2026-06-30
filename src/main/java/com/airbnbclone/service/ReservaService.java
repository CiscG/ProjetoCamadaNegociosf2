package com.airbnbclone.service;

import com.airbnbclone.dto.OcupacaoResponse;
import com.airbnbclone.dto.PropriedadeResponse;
import com.airbnbclone.dto.ReservaRequest;
import com.airbnbclone.dto.ReservaResponse;
import com.airbnbclone.model.Datas;
import com.airbnbclone.model.Propriedade;
import com.airbnbclone.model.Reserva;
import com.airbnbclone.model.Usuario;
import com.airbnbclone.repository.PropriedadeRepository;
import com.airbnbclone.repository.ReservaRepository;
import com.airbnbclone.repository.UsuarioRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final PropriedadeRepository propriedadeRepository;
    private final UsuarioRepository usuarioRepository;
    private final MongoTemplate mongoTemplate;
    private final PropriedadeService propriedadeService;
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ReservaService(ReservaRepository reservaRepository,
                          PropriedadeRepository propriedadeRepository,
                          UsuarioRepository usuarioRepository,
                          MongoTemplate mongoTemplate,
                          PropriedadeService propriedadeService) {
        this.reservaRepository = reservaRepository;
        this.propriedadeRepository = propriedadeRepository;
        this.usuarioRepository = usuarioRepository;
        this.mongoTemplate = mongoTemplate;
        this.propriedadeService = propriedadeService;
    }

    public ReservaResponse criar(ReservaRequest req) {
        LocalDate checkin = LocalDate.parse(req.checkin(), DATE_FMT);
        LocalDate checkout = LocalDate.parse(req.checkout(), DATE_FMT);

        if (!checkout.isAfter(checkin))
            throw new IllegalArgumentException("Checkout deve ser posterior ao checkin.");

        Propriedade propriedade = propriedadeRepository.findById(req.propriedadeId())
                .orElseThrow(() -> new IllegalArgumentException("Propriedade nao encontrada."));

        Usuario hospede = usuarioRepository.findById(req.hospedeId())
                .orElseThrow(() -> new IllegalArgumentException("Hospede nao encontrado."));
        if (!Set.of("hospede", "ambos").contains(hospede.getTipo()))
            throw new IllegalArgumentException("Somente hospedes podem fazer reservas.");

        Query conflictQuery = new Query(
                Criteria.where("propriedade_id").is(req.propriedadeId())
                        .and("status").is("confirmada")
                        .and("datas.checkin").lt(checkout)
                        .and("datas.checkout").gt(checkin)
        );
        if (mongoTemplate.exists(conflictQuery, Reserva.class))
            throw new ConflictException("Esta propriedade ja esta reservada para estas datas.");

        long noites = ChronoUnit.DAYS.between(checkin, checkout);

        Reserva reserva = new Reserva();
        reserva.setPropriedadeId(req.propriedadeId());
        reserva.setHospedeId(req.hospedeId());
        reserva.setDatas(new Datas(checkin, checkout));
        reserva.setValorTotal(propriedade.getPrecoPorNoite() * noites);
        reserva.setStatus("confirmada");
        reserva.setDataReserva(LocalDateTime.now());

        Reserva salva = reservaRepository.save(reserva);

        Map<String, Usuario> anfitrioes = usuarioRepository.findById(propriedade.getAnfitriaoId())
                .map(u -> Map.of(u.getId(), u)).orElse(Map.of());
        PropriedadeResponse propriedadeResponse = propriedadeService.toResponse(propriedade, anfitrioes);

        return toResponse(salva, propriedadeResponse);
    }

    public List<ReservaResponse> listar(String hospedeId, String anfitriaoId, String status) {
        Query query = new Query();
        List<Criteria> criterias = new ArrayList<>();

        if (hospedeId != null && !hospedeId.isBlank())
            criterias.add(Criteria.where("hospede_id").is(hospedeId));
        if (status != null && !status.isBlank())
            criterias.add(Criteria.where("status").is(status));

        if (anfitriaoId != null && !anfitriaoId.isBlank()) {
            List<Propriedade> propriedadesAnfitriao = propriedadeRepository.findByAnfitriaoId(anfitriaoId);
            if (propriedadesAnfitriao.isEmpty()) return List.of();
            List<String> propriedadeIds = propriedadesAnfitriao.stream()
                    .map(Propriedade::getId).toList();
            criterias.add(Criteria.where("propriedade_id").in(propriedadeIds));
        }

        if (!criterias.isEmpty())
            query.addCriteria(new Criteria().andOperator(criterias.toArray(new Criteria[0])));

        query.with(Sort.by(Sort.Direction.DESC, "data_reserva"));

        List<Reserva> reservas = mongoTemplate.find(query, Reserva.class);

        Set<String> propriedadeIds = reservas.stream()
                .map(Reserva::getPropriedadeId).collect(Collectors.toSet());
        Map<String, Propriedade> propriedadesMap = propriedadeRepository.findAllById(propriedadeIds)
                .stream().collect(Collectors.toMap(Propriedade::getId, p -> p));

        Set<String> anfitriaoIds = propriedadesMap.values().stream()
                .map(Propriedade::getAnfitriaoId).collect(Collectors.toSet());
        Map<String, Usuario> anfitrioes = usuarioRepository.findAllById(anfitriaoIds)
                .stream().collect(Collectors.toMap(Usuario::getId, u -> u));

        return reservas.stream().map(r -> {
            Propriedade p = propriedadesMap.get(r.getPropriedadeId());
            PropriedadeResponse propResp = p != null ? propriedadeService.toResponse(p, anfitrioes) : null;
            return toResponse(r, propResp);
        }).collect(Collectors.toList());
    }

    public List<OcupacaoResponse> ocupacao(String propriedadeId) {
        Query query = new Query(
                Criteria.where("propriedade_id").is(propriedadeId).and("status").is("confirmada")
        );
        return mongoTemplate.find(query, Reserva.class).stream()
                .map(r -> new OcupacaoResponse(
                        r.getDatas().getCheckin().format(DATE_FMT),
                        r.getDatas().getCheckout().format(DATE_FMT)))
                .collect(Collectors.toList());
    }

    public ReservaResponse toResponse(Reserva reserva, PropriedadeResponse propriedade) {
        return new ReservaResponse(
                reserva.getId(),
                reserva.getPropriedadeId(),
                reserva.getHospedeId(),
                reserva.getDatas().getCheckin().format(DATE_FMT),
                reserva.getDatas().getCheckout().format(DATE_FMT),
                reserva.getValorTotal(),
                reserva.getStatus(),
                reserva.getDataReserva().format(DATE_FMT),
                propriedade
        );
    }
}
