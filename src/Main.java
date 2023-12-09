import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaClassificacao sistema = new SistemaClassificacao();
        Scanner scanner = new Scanner(System.in);

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("Menu:");
            System.out.println("1. Adicionar estudante");
            System.out.println("2. Remover estudante");
            System.out.println("3. Buscar estudante");
            System.out.println("4. Classificar estudantes por nota");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer do scanner

                switch (opcao) {
                    case 1:
                        System.out.print("Digite o nome do estudante: ");
                        String nome = scanner.nextLine();
                        System.out.print("Digite a nota do estudante: ");
                        double nota = scanner.nextDouble();
                        scanner.nextLine(); // Limpar o buffer do scanner
                        Estudante estudante = new Estudante(nome, nota);
                        if (sistema.adicionarEstudante(estudante)) {
                            System.out.println("Estudante adicionado com sucesso.");
                        } else {
                            System.out.println("Erro ao adicionar estudante. Estudante já existe.");
                        }
                        break;
                    case 2:
                        System.out.print("Digite o nome do estudante a remover: ");
                        String nomeRemover = scanner.nextLine();
                        if (sistema.removerEstudante(nomeRemover)) {
                            System.out.println("Estudante removido com sucesso.");
                        } else {
                            System.out.println("Erro ao remover estudante. Estudante não encontrado.");
                        }
                        break;
                    case 3:
                        System.out.print("Digite o nome do estudante a buscar: ");
                        String nomeBuscar = scanner.nextLine();
                        Estudante estudanteEncontrado = sistema.buscarEstudante(nomeBuscar);
                        if (estudanteEncontrado != null) {
                            System.out.println("Estudante encontrado: " + estudanteEncontrado.getNome() +
                                    " - " + estudanteEncontrado.getNota());
                        } else {
                            System.out.println("Estudante não encontrado.");
                        }
                        break;
                    case 4:
                        List<Estudante> estudantesClassificados = sistema.classificarEstudantes();
                        System.out.println("Estudantes classificados por nota:");
                        for (Estudante est : estudantesClassificados) {
                            System.out.println(est.getNome() + " - " + est.getNota());
                        }
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro: Entrada inválida. Tente novamente.");
                scanner.nextLine(); // Limpar o buffer do scanner
            }
        }

        scanner.close();
    }
}