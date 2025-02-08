import br.com.projetos.gerenciadorFuncionarios.service.Service;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Service service;
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Inicializa o gerenciador com a carga de dados inicial
            // Inserção de todos os funcionarios
            service = new Service("Dados/Funcionarios.txt");

            int opcao;
            do {
                limparTela();
                opcao = obterEscolhaMenu();
                processarEscolhaMenu(opcao);
            } while (opcao != 10);
        } catch (IOException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }
    }

    private static int obterEscolhaMenu() {
        System.out.println("\n--- Menu de Gerenciamento de Funcionários ---\n");
        System.out.println("1. Listar Funcionários");
        System.out.println("2. Remover Funcionário");
        System.out.println("3. Atualizar Salários");
        System.out.println("4. Agrupar por Função");
        System.out.println("5. Listar Aniversariantes do Mês");
        System.out.println("6. Encontrar Funcionário Mais Velho");
        System.out.println("7. Listar Funcionários Por Ordem Alfabética");
        System.out.println("8. Total Salário");
        System.out.println("9. Quantidade de Salarários Minimos");
        System.out.println("10. Sair");

        System.out.print("\nEscolha uma opção: ");
        int opcao = teclado.nextInt();
        teclado.nextLine(); // Limpar buffer
        return opcao;
    }

    private static void processarEscolhaMenu(int opcao) {
        switch (opcao) {
            case 1:
                service.imprimirFuncionarios();
                pausa();
                break;
            case 2:
                service.removerFuncionario();
                pausa();
                break;
            case 3:
                service.atualizarSalario();
                pausa();
                break;
            case 4:
                service.agrupar();
                pausa();
                break;
            case 5:
                service.aniversarianteMes();
                pausa();
                break;
            case 6:
                service.encontrarFuncionarioMaisVelho();
                pausa();
                break;
            case 7:
                service.listaFuncionariosOrdenada();
                pausa();
                break;
            case 8:
                service.exibirTotalSalarioFuncionarios();
                pausa();
                break;
            case 9:
                service.qtdeSalariosMinimos();
                pausa();
                break;
            case 10:
                System.out.println("Saindo do sistema...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                pausa();
        }
    }

    private static void limparTela() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ex) {
        }
    }

    private static void pausa() {
        System.out.print("\nTecle ENTER para continuar.");
        teclado.nextLine();
    }
}