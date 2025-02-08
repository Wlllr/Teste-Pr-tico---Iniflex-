package br.com.projetos.gerenciadorFuncionarios.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNasc, BigDecimal salario, String funcao) {
        super(nome, dataNasc);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getSalarioFormatado() {
        NumberFormat formatarSalario = NumberFormat.getInstance(new Locale("pt", "BR"));
        formatarSalario.setMinimumFractionDigits(2);
        formatarSalario.setMaximumFractionDigits(2);
        //formatarSalario.setGroupingUsed(true);
        return formatarSalario.format(salario);
    }

    public String getDataNascFormatada() {
        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return getDataNasc().format(dataFormatada);
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @Override
    public String toString() {
        String formatoColunas = "%-20s %-15s %-15s %-15s";
        return String.format(formatoColunas, getNome(), getDataNascFormatada(), getSalarioFormatado(), funcao);
    }
}
