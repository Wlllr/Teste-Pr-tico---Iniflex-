package br.com.projetos.gerenciadorFuncionarios.model;

import java.time.LocalDate;

public class Pessoa {
    private String nome;
    private LocalDate dataNasc;

    public Pessoa(String nome, LocalDate dataNasc) {
        this.nome = nome;
        this.dataNasc = dataNasc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    @Override
    public String toString() {
        return "br.com.projetos.gerenciadorFuncionarios.model.Pessoa{" +
                "nome='" + nome + '\'' +
                ", data_nascimento=" + dataNasc +
                '}';
    }
}
