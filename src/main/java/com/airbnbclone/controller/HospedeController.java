package com.airbnbclone.controller;

import com.airbnbclone.dto.HospedeRequest;
import com.airbnbclone.dto.HospedeResponse;
import com.airbnbclone.service.HospedeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HospedeController {

    private final HospedeService hospedeService;

    public HospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    @GetMapping("/hospedes")
    public ResponseEntity<?> listar() {
        List<HospedeResponse> hospedes = hospedeService.listar();
        return ResponseEntity.ok(hospedes);
    }

    @GetMapping("/hospedes/{id}")
    public ResponseEntity<?> buscar(@PathVariable String id) {
        return hospedeService.buscar(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(Map.of("erro", "Hospede nao encontrado.")));
    }

    @PostMapping("/hospedes")
    public ResponseEntity<?> criar(@RequestBody HospedeRequest req) {
        HospedeResponse hospede = hospedeService.criar(req);
        return ResponseEntity.status(201).body(Map.of("hospede", hospede));
    }
}
