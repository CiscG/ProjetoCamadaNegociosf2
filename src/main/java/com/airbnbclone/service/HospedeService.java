package com.airbnbclone.service;

import com.airbnbclone.dto.HospedeRequest;
import com.airbnbclone.dto.HospedeResponse;
import com.airbnbclone.model.Usuario;
import com.airbnbclone.repository.UsuarioRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HospedeService {

    private final UsuarioRepository usuarioRepository;
    private final MongoTemplate mongoTemplate;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public HospedeService(UsuarioRepository usuarioRepository, MongoTemplate mongoTemplate) {
        this.usuarioRepository = usuarioRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<HospedeResponse> listar() {
        Query query = new Query(
                new Criteria().orOperator(
                        Criteria.where("tipo").is("hospede"),
                        Criteria.where("tipo").is("ambos")
                )
        );
        return mongoTemplate.find(query, Usuario.class).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<HospedeResponse> buscar(String id) {
        return usuarioRepository.findById(id)
                .filter(u -> "hospede".equals(u.getTipo()) || "ambos".equals(u.getTipo()))
                .map(this::toResponse);
    }

    public HospedeResponse criar(HospedeRequest req) {
        if (req.nome() == null || req.email() == null || req.senha() == null)
            throw new IllegalArgumentException("Nome, email e senha sao obrigatorios.");

        if (usuarioRepository.findByEmail(req.email().trim().toLowerCase()).isPresent())
            throw new IllegalArgumentException("Email ja cadastrado.");

        Usuario hospede = new Usuario();
        hospede.setNome(req.nome().strip());
        hospede.setEmail(req.email().trim().toLowerCase());
        hospede.setSenhaHash(passwordEncoder.encode(req.senha()));
        hospede.setTipo("hospede");
        hospede.setDataCadastro(LocalDateTime.now());

        Usuario salvo = usuarioRepository.save(hospede);
        return toResponse(salvo);
    }

    private HospedeResponse toResponse(Usuario u) {
        return new HospedeResponse(
                u.getId(),
                u.getNome(),
                u.getEmail(),
                u.getTipo(),
                u.getDataCadastro() != null ? u.getDataCadastro().format(DATE_FMT) : null
        );
    }
}
