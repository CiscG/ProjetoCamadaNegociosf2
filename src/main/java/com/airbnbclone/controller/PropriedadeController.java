package com.airbnbclone.controller;

import com.airbnbclone.dto.OcupacaoResponse;
import com.airbnbclone.dto.PropriedadeRequest;
import com.airbnbclone.dto.PropriedadeResponse;
import com.airbnbclone.service.PropriedadeService;
import com.airbnbclone.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PropriedadeController {

    private final PropriedadeService propriedadeService;
    private final ReservaService reservaService;

    public PropriedadeController(PropriedadeService propriedadeService, ReservaService reservaService) {
        this.propriedadeService = propriedadeService;
        this.reservaService = reservaService;
    }

    @GetMapping("/propriedades")
    public ResponseEntity<?> listar(
            @RequestParam(required = false) String cidade,
            @RequestParam(name = "preco_max", required = false) String precoMax,
            @RequestParam(name = "anfitriao_id", required = false) String anfitriaoId) {
        List<PropriedadeResponse> propriedades = propriedadeService.listar(cidade, precoMax, anfitriaoId);
        return ResponseEntity.ok(propriedades);
    }

    @PostMapping("/propriedades")
    public ResponseEntity<?> criar(@RequestBody PropriedadeRequest req) {
        PropriedadeResponse propriedade = propriedadeService.criar(req);
        return ResponseEntity.status(201).body(Map.of("propriedade", propriedade));
    }

    @GetMapping("/propriedades/{id}/ocupacao")
    public ResponseEntity<?> ocupacao(@PathVariable String id) {
        List<OcupacaoResponse> ocupacao = reservaService.ocupacao(id);
        return ResponseEntity.ok(ocupacao);
    }
}
