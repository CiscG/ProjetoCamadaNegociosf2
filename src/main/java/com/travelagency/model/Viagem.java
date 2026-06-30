package com.travelagency.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Record para representar uma Viagem
 * Requisito: Records, Text blocks e Blocos estáticos de código
 * Requisito: Persistência e recuperação de objetos
 */
@Document(collection = "viagens")
public class Viagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Bloco estático para logging
    static {
        System.out.println("""
            ╔════════════════════════════════════════╗
            ║  Viagem - Modelo de Persistência      ║
            ║  Agência de Viagens - Sistema v1.0    ║
            ╚════════════════════════════════════════╝
            """);
    }

    @Id
    private String id;
    
    private String destino;
    private TipoViagem tipo;
    private LocalDate dataPartida;
    private LocalDate dataRetorno;
    private BigDecimal preco;
    private Integer vagas;
    private String descricao;
    private Boolean ativa;

    // Construtores
    public Viagem() {
        this.ativa = true;
    }

    public Viagem(String destino, TipoViagem tipo, LocalDate dataPartida, 
                  LocalDate dataRetorno, BigDecimal preco, Integer vagas, String descricao) {
        this();
        this.destino = destino;
        this.tipo = tipo;
        this.dataPartida = dataPartida;
        this.dataRetorno = dataRetorno;
        this.preco = preco;
        this.vagas = vagas;
        this.descricao = descricao;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public TipoViagem getTipo() {
        return tipo;
    }

    public void setTipo(TipoViagem tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(LocalDate dataPartida) {
        this.dataPartida = dataPartida;
    }

    public LocalDate getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(LocalDate dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Viagem viagem = (Viagem) o;
        return Objects.equals(id, viagem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Viagem{" +
                "id='" + id + '\'' +
                ", destino='" + destino + '\'' +
                ", tipo=" + tipo +
                ", dataPartida=" + dataPartida +
                ", dataRetorno=" + dataRetorno +
                ", preco=" + preco +
                ", vagas=" + vagas +
                ", ativa=" + ativa +
                '}';
    }
}
