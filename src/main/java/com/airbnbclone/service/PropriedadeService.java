package com.airbnbclone.service;

import com.airbnbclone.dto.PropriedadeRequest;
import com.airbnbclone.dto.PropriedadeResponse;
import com.airbnbclone.model.Endereco;
import com.airbnbclone.model.Propriedade;
import com.airbnbclone.model.Usuario;
import com.airbnbclone.repository.PropriedadeRepository;
import com.airbnbclone.repository.UsuarioRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PropriedadeService {

    private final PropriedadeRepository propriedadeRepository;
    private final UsuarioRepository usuarioRepository;
    private final MongoTemplate mongoTemplate;
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PropriedadeService(PropriedadeRepository propriedadeRepository,
                              UsuarioRepository usuarioRepository,
                              MongoTemplate mongoTemplate) {
        this.propriedadeRepository = propriedadeRepository;
        this.usuarioRepository = usuarioRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<PropriedadeResponse> listar(String cidade, String precoMax, String anfitriaoId) {
        Query query = new Query();
        List<Criteria> criterias = new ArrayList<>();

        if (cidade != null && !cidade.isBlank())
            criterias.add(Criteria.where("endereco.cidade").regex("^" + cidade + "$", "i"));
        if (precoMax != null && !precoMax.isBlank())
            criterias.add(Criteria.where("preco_por_noite").lte(Double.parseDouble(precoMax)));
        if (anfitriaoId != null && !anfitriaoId.isBlank())
            criterias.add(Criteria.where("anfitriao_id").is(anfitriaoId));

        if (!criterias.isEmpty())
            query.addCriteria(new Criteria().andOperator(criterias.toArray(new Criteria[0])));

        query.with(Sort.by(Sort.Direction.DESC, "data_cadastro"));

        List<Propriedade> propriedades = mongoTemplate.find(query, Propriedade.class);

        Set<String> anfitriaoIds = propriedades.stream()
                .map(Propriedade::getAnfitriaoId).collect(Collectors.toSet());
        Map<String, Usuario> anfitrioes = usuarioRepository.findAllById(anfitriaoIds)
                .stream().collect(Collectors.toMap(Usuario::getId, u -> u));

        return propriedades.stream()
                .map(p -> toResponse(p, anfitrioes))
                .collect(Collectors.toList());
    }

    public PropriedadeResponse criar(PropriedadeRequest req) {
        if (req.anfitriaoId() == null || req.titulo() == null || req.descricao() == null
                || req.precoPorNoite() == null || req.endereco() == null)
            throw new IllegalArgumentException("Campos obrigatorios ausentes.");

        Endereco end = req.endereco();
        if (end.getCidade() == null || end.getEstado() == null || end.getPais() == null)
            throw new IllegalArgumentException("Endereco incompleto: cidade, estado e pais sao obrigatorios.");

        Usuario anfitriao = usuarioRepository.findById(req.anfitriaoId())
                .orElseThrow(() -> new IllegalArgumentException("Anfitriao nao encontrado."));
        if (!Set.of("anfitriao", "ambos").contains(anfitriao.getTipo()))
            throw new IllegalArgumentException("Somente anfitrioes podem cadastrar propriedades.");

        Propriedade propriedade = new Propriedade();
        propriedade.setAnfitriaoId(req.anfitriaoId());
        propriedade.setTitulo(req.titulo().strip());
        propriedade.setDescricao(req.descricao().strip());
        propriedade.setPrecoPorNoite(req.precoPorNoite());
        propriedade.setEndereco(end);
        propriedade.setComodidades(req.comodidades() != null
                ? req.comodidades().stream().filter(c -> !c.isBlank()).map(String::strip).toList()
                : List.of());
        propriedade.setDataCadastro(LocalDateTime.now());

        Propriedade salva = propriedadeRepository.save(propriedade);
        return toResponse(salva, Map.of(anfitriao.getId(), anfitriao));
    }

    public Optional<Propriedade> buscarEntidade(String id) {
        return propriedadeRepository.findById(id);
    }

    public PropriedadeResponse toResponse(Propriedade propriedade, Map<String, Usuario> anfitrioes) {
        Usuario anfitriao = anfitrioes.get(propriedade.getAnfitriaoId());
        return new PropriedadeResponse(
                propriedade.getId(),
                propriedade.getAnfitriaoId(),
                anfitriao != null ? anfitriao.getNome() : "Anfitriao",
                propriedade.getTitulo(),
                propriedade.getDescricao(),
                propriedade.getPrecoPorNoite(),
                propriedade.getEndereco(),
                propriedade.getComodidades(),
                propriedade.getDataCadastro() != null
                        ? propriedade.getDataCadastro().format(DATE_FMT) : null
        );
    }
}
