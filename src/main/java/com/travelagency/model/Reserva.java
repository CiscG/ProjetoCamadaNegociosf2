package com.travelagency.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Classe Reserva com suporte a persistência
 * Requisito: Persistência e recuperação de objetos
 */
@Document(collection = "reservas")
public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum StatusReserva {
        PENDENTE, CONFIRMADA, CANCELADA, CONCLUIDA
    }

    @Id
    private String id;
    
    private String clienteId;
    private String viagemId;
    private Integer quantidade;
    private StatusReserva status;
    private LocalDateTime dataReserva;
    private LocalDateTime dataConfirmacao;
    private String observacoes;

    // Construtores
    public Reserva() {
        this.dataReserva = LocalDateTime.now();
        this.status = StatusReserva.PENDENTE;
    }

    public Reserva(String clienteId, String viagemId, Integer quantidade) {
        this();
        this.clienteId = clienteId;
        this.viagemId = viagemId;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getViagemId() {
        return viagemId;
    }

    public void setViagemId(String viagemId) {
        this.viagemId = viagemId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    public LocalDateTime getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(LocalDateTime dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id='" + id + '\'' +
                ", clienteId='" + clienteId + '\'' +
                ", viagemId='" + viagemId + '\'' +
                ", quantidade=" + quantidade +
                ", status=" + status +
                ", dataReserva=" + dataReserva +
                '}';
    }
}
