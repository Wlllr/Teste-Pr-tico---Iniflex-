package br.com.projetos.gerenciadorFuncionarios.service;

import br.com.projetos.gerenciadorFuncionarios.model.Funcionario;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;


import static java.nio.file.Files.readAllLines;

public class Service {
    List<Funcionario> listaFuncionarios = new ArrayList<>();
    Scanner teclado = new Scanner(System.in);

    public Service(String cargaDeDados) throws IOException {
        List<String> dadosFuncionario = readAllLines(Path.of(cargaDeDados));

        inserirFuncionarios(dadosFuncionario);
    }

    //Funçao responsavel por pegar carga de dados dos funcionarios e realizar inserçao
    private void inserirFuncionarios(List<String> dadosDoFuncionario) {
        for (String cadaLinha : dadosDoFuncionario) {

            Funcionario novoFuncionario = construaFuncionario(cadaLinha);
            listaFuncionarios.add(novoFuncionario);
        }
    }

    //Responsavel por pegar a carga, separar pelo ;
    private Funcionario construaFuncionario(String cadaLinha) {
        String[] partesDados = cadaLinha.split(";");

        return construindoFuncionario(partesDados);
    }

    //Responsavel por pegar as partes separadas e construir o objeto Funcionario
    private Funcionario construindoFuncionario(String[] partesDados) {
        return new Funcionario(
            partesDados[0],
            formatarData(partesDados[1]),
            formatarSalario(partesDados[2]),
            partesDados[3]
        );
    }

    private LocalDate formatarData(String data) {
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return LocalDate.parse(data, formatadorData);
    }

    private BigDecimal formatarSalario(String salario) {
        String salarioNormalizado = salario.replace(",", ".");
        return new BigDecimal(salarioNormalizado);
    }

    public void imprimirFuncionarios() {
        formatadorTodosFuncionarios();
        for (Funcionario cadaFuncionario : listaFuncionarios) {
            System.out.println(cadaFuncionario);
        }
    }

    private static void formatadorTodosFuncionarios() {
        System.out.println(" ");
        String formatoColunas = "%-20s %-15s %-15s %-15s";
        System.out.println(String.format(formatoColunas, "Nome", "Data Nasc.", "Salário", "Função"));
        System.out.println("-------------------------------------------------------------");
    }

    public void removerFuncionario() {
        System.out.print("Digite o nome do funcionário a ser removido: ");
        String nomeRemover = teclado.nextLine();

        Funcionario funcARemover = encontrarFuncionario(nomeRemover);
        verifPRemocao(funcARemover);
    }

    private void verifPRemocao(Funcionario funcARemover) {
        if (funcARemover != null) {
            listaFuncionarios.remove(funcARemover);
            System.out.println("Funcionário removido com sucesso.");
        } else {
            System.out.println("Nenhum funcionário com este nome foi encontrado.");
        }
    }

    public Funcionario encontrarFuncionario(String nome){
        Funcionario funcionarioEncontrado;
        for (Funcionario cadaFuncionario : listaFuncionarios) {
            if (cadaFuncionario.getNome().equals(nome)) {
                funcionarioEncontrado = cadaFuncionario;
                return funcionarioEncontrado;
            }
        }
        return null;
    }

    public void atualizarSalario() {
        System.out.print("Digite o percentual de aumento (ex: 0.10 para 10%): ");
        BigDecimal percentual = new BigDecimal(teclado.nextLine());

        for (Funcionario cadaFuncionario : listaFuncionarios) {
            BigDecimal salarioAjustado = calculoSalario(cadaFuncionario, percentual);
            cadaFuncionario.setSalario(salarioAjustado);
        }
        imprimirFuncionarios();
    }

    private static BigDecimal calculoSalario(Funcionario cadaFuncionario, BigDecimal percentual) {
        BigDecimal salario = cadaFuncionario.getSalario();
        return salario.add(salario.multiply(percentual));
    }

    public void agrupar() {
        TreeMap<String, List<Funcionario>> treeFuncionarios = new TreeMap<>();
        for (Funcionario cadaFuncionario : listaFuncionarios) {
            String funcao = cadaFuncionario.getFuncao();

            verificarFuncao(treeFuncionarios, funcao);
            treeFuncionarios.get(funcao).add(cadaFuncionario);
        }

        exibirAgrupamento(treeFuncionarios);
    }

    private static void verificarFuncao(TreeMap<String, List<Funcionario>> treeFuncionarios, String funcao) {
        if (!treeFuncionarios.containsKey(funcao)) {
            treeFuncionarios.put(funcao, new ArrayList<>());
        }
    }

    private static void exibirAgrupamento(TreeMap<String, List<Funcionario>> treeFuncionarios) {
        String formatoColunas = formatadorDeSaidas("\nAgrupamento por Função:\n", "Função");

        for (Map.Entry<String, List<Funcionario>> entry : treeFuncionarios.entrySet()) {
            for (Funcionario cadaFuncionario : entry.getValue()) {
                System.out.println(String.format(formatoColunas,
                        cadaFuncionario.getNome(),
                        cadaFuncionario.getFuncao()));
            }
            System.out.println();
        }
    }

    private static String formatadorDeSaidas(String x, String Função) {
        String formatoColunas = "%-20s %-15s";
        System.out.println(x);
        System.out.println(String.format(formatoColunas, "Nome", Função));
        System.out.println("---------------------------------------------");
        return formatoColunas;
    }

    public void aniversarianteMes() {
        // Solicita o mes ou meses ao usuário
        System.out.print("Digite os meses para listar aniversariantes (ex: 10 12): ");
        String[] mesesDigitados = teclado.nextLine().split(" ");

        Month[] meses = convertePMonth(mesesDigitados);

        // Verifica aniversariantes
        boolean encontrouAniversariante = false;
        encontrouAniversariante = encontrarAniversariante(meses, encontrouAniversariante);

        // Exibe mensagem se nenhum aniversariante for encontrado
        if (!encontrouAniversariante) {
            System.out.println("Nenhum aniversariante neste(s) mês(es)!");
        }
    }

    private boolean encontrarAniversariante(Month[] meses, boolean encontrouAniversariante) {
        for (Funcionario funcionario : listaFuncionarios) {
            Month mesAniversario = funcionario.getDataNasc().getMonth();

            // Verifica se o mês de aniversário está na lista de meses fornecidos
            for (Month mes : meses) {
                if (mesAniversario == mes) {
                    System.out.println("Nome: " + funcionario.getNome() + " - " + funcionario.getFuncao());
                    encontrouAniversariante = true;
                    break;
                }
            }
        }
        return encontrouAniversariante;
    }

    private static Month[] convertePMonth(String[] mesesDigitados) {
        // Converte os meses digitados para o tipo Month
        Month[] meses = new Month[mesesDigitados.length];
        for (int i = 0; i < mesesDigitados.length; i++) {
            int mes = Integer.parseInt(mesesDigitados[i]);
            meses[i] = Month.of(mes); // Converte o número para o enum Month
        }
        return meses;
    }

    public void encontrarFuncionarioMaisVelho() {
        Funcionario maisVelho = listaFuncionarios.get(0);

        for (Funcionario cadaFuncionario : listaFuncionarios) {
            if (cadaFuncionario.getDataNasc().isBefore(maisVelho.getDataNasc())) {
                maisVelho = cadaFuncionario;
            }
        }

        Period idade = Period.between(maisVelho.getDataNasc(), LocalDate.now());
        System.out.println("E o vovô da Empresa é o... " + maisVelho.getNome()
                + "!" + " Com " + idade.getYears() + " anos de idade. Parabéns!");
    }

    public void listaFuncionariosOrdenada() {
        formatadorListaOrdenada();
        listaFuncionarios.sort(Comparator.comparing(Funcionario::getNome));
        for (Funcionario cadaFuncionario : listaFuncionarios) {
            System.out.println(cadaFuncionario.getNome());
        }
    }

    private static void formatadorListaOrdenada() {
        String formatoColunas = "%-20s";
        System.out.println("\nAgrupamento por Ordem Alfabetica:\n");
        System.out.println(String.format(formatoColunas, "Nome"));
        System.out.println("---------------------------------------------");
    }

    public void exibirTotalSalarioFuncionarios() {
        String formatoColunas = formatadorDeSaidas("\nAgrupamento por Ordem Alfabética:\n", "Salário");

        BigDecimal acumulador = varreAcumulandoSalarios(formatoColunas);

        NumberFormat acumuladorFormatado = formatoSaidaNumerico();

        // Exibe o total dos Dados
        System.out.println("---------------------------------------------");
        System.out.println(String.format(formatoColunas, "Total:", acumuladorFormatado.format(acumulador)));
    }

    private static NumberFormat formatoSaidaNumerico() {
        // Formata o total acumulado
        NumberFormat acumuladorFormatado = NumberFormat.getInstance(new Locale("pt", "BR"));
        acumuladorFormatado.setMinimumFractionDigits(2);
        acumuladorFormatado.setMaximumFractionDigits(2);
        return acumuladorFormatado;
    }

    private BigDecimal varreAcumulandoSalarios(String formatoColunas) {
        BigDecimal acumulador = new BigDecimal(0);

        // Itera sobre a lista de funcionários e exibe nome e salário
        for (Funcionario cadaFuncionario : listaFuncionarios) {
            BigDecimal salario = cadaFuncionario.getSalario();
            acumulador = acumulador.add(salario); // Acumula o salário

            // Formata o salário para exibição
            NumberFormat formatarSalario = formatoSaidaNumerico();

            // Exibe o nome e o salário formatado
            System.out.println(String.format(formatoColunas,
                    cadaFuncionario.getNome(),
                    formatarSalario.format(salario)));
        }
        return acumulador;
    }

    public void qtdeSalariosMinimos() {
        BigDecimal salarioMinimo = new BigDecimal(1212.00);

        String formatoColunas = formatadorDeSaidas("\nQuantidade de Salários Mínimos:\n", "Qtd. Salários Mín.");

        for (Funcionario cadaFuncionario : listaFuncionarios) {
            BigDecimal qtdeSalariosMin = calculoQtdeSalMin(cadaFuncionario, salarioMinimo);

            System.out.println(String.format(formatoColunas, cadaFuncionario.getNome(), qtdeSalariosMin.toPlainString()));
        }
    }

    private static BigDecimal calculoQtdeSalMin(Funcionario cadaFuncionario, BigDecimal salarioMinimo) {
        BigDecimal salario = cadaFuncionario.getSalario();
        BigDecimal qtdeSalariosMin = salario.divideToIntegralValue(salarioMinimo);
        qtdeSalariosMin = qtdeSalariosMin.stripTrailingZeros();
        return qtdeSalariosMin;
    }


}
