package com.airbnbclone.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "reservas")
public class Reserva {
    @Id
    private String id;
    @Field("propriedade_id")
    private String propriedadeId;
    @Field("hospede_id")
    private String hospedeId;
    private Datas datas;
    @Field("valor_total")
    private Double valorTotal;
    private String status;
    @Field("data_reserva")
    private LocalDateTime dataReserva;

    public Reserva() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPropriedadeId() { return propriedadeId; }
    public void setPropriedadeId(String propriedadeId) { this.propriedadeId = propriedadeId; }
    public String getHospedeId() { return hospedeId; }
    public void setHospedeId(String hospedeId) { this.hospedeId = hospedeId; }
    public Datas getDatas() { return datas; }
    public void setDatas(Datas datas) { this.datas = datas; }
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getDataReserva() { return dataReserva; }
    public void setDataReserva(LocalDateTime dataReserva) { this.dataReserva = dataReserva; }
}
