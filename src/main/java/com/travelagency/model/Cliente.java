package com.travelagency.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Classe Cliente com demonstração de Wrapper classes e Autoboxing
 * Requisito: Wrapper classes e Autoboxing
 * Requisito: Persistência e recuperação de objetos
 */
@Document(collection = "clientes")
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    
    // Wrapper classes - Autoboxing em ação
    private Integer idade;           // Integer - wrapper de int
    private Double renda;            // Double - wrapper de double
    private Boolean ativo;           // Boolean - wrapper de boolean
    private Long ultimoAcesso;       // Long - wrapper de long
    
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    // Construtores
    public Cliente() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.ativo = true; // Autoboxing: true (boolean) -> Boolean
    }

    public Cliente(String nome, String email, String telefone, String cpf, 
                   Integer idade, Double renda) {
        this();
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.idade = idade;   // Autoboxing: int -> Integer
        this.renda = renda;   // Autoboxing: double -> Double
    }

    // Métodos com Autoboxing
    /**
     * Incrementa a idade com unboxing automático
     */
    public void fazerAniversario() {
        if (this.idade != null) {
            this.idade++; // Unboxing: Integer -> int, depois Autoboxing: int -> Integer
        }
    }

    /**
     * Verifica se cliente é menor de idade com unboxing
     */
    public Boolean isMenorIdade() {
        if (this.idade != null) {
            return this.idade < 18; // Unboxing: Integer -> int, retorna boolean -> Boolean (autoboxing)
        }
        return null;
    }

    /**
     * Calcula bônus baseado em renda com operações de autoboxing
     */
    public Double calcularBonus(Double percentual) {
        if (this.renda != null && percentual != null) {
            Double bonus = this.renda * (percentual / 100.0); // Autoboxing: double -> Double
            return bonus;
        }
        return 0.0; // Autoboxing: double -> Double
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Double getRenda() {
        return renda;
    }

    public void setRenda(Double renda) {
        this.renda = renda;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(Long ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", idade=" + idade +
                ", renda=" + renda +
                ", ativo=" + ativo +
                '}';
    }
}
