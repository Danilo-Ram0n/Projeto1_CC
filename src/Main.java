import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String EVENTOS_ARQUIVO = "events.data";
    private static List<Evento> eventos = new ArrayList<>();

    public static void main(String[] args) {
        carregarEventos();

        Scanner scanner = new Scanner(System.in);
        boolean logado = false;
        Cliente clienteLogado = null;

        while (!logado) {
            System.out.println("Selecione uma opção:");
            System.out.println("1. Cadastrar");
            System.out.println("2. Login");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                System.out.println("Cadastro:");
                System.out.println("Nome: ");
                String nome = scanner.nextLine();
                System.out.println("Idade: ");
                int idade = scanner.nextInt();
                scanner.nextLine();
                System.out.println("CPF: ");
                int cpf = scanner.nextInt();
                scanner.nextLine();

                Cliente cliente = Cliente.cadastrar(nome, idade, cpf);
                if (cliente != null) {
                    clienteLogado = cliente;
                    logado = true;
                }
            } else if (opcao == 2) {
                System.out.println("Login:");
                System.out.println("CPF: ");
                int cpf = scanner.nextInt();
                scanner.nextLine();

                Cliente cliente = Cliente.login(cpf);
                if (cliente != null) {
                    clienteLogado = cliente;
                    logado = true;
                } else {
                    System.out.println("Cliente não encontrado. Tente novamente.");
                }
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }


        while (logado) {
            System.out.println("Selecione uma opção:");
            System.out.println("1. Ver eventos");
            System.out.println("2. Criar evento");
            System.out.println("3. Entrar em evento");
            System.out.println("4. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Eventos:");
                    for (Evento evento : eventos) {
                        System.out.println("Nome: " + evento.nome);
                        System.out.println("Endereço: " + evento.endereco);
                        System.out.println("Categoria: " + evento.TipoCategoria());
                        System.out.println("Horário: " + evento.horario);
                        System.out.println("Descrição: " + evento.descricao);
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.println("Criar Evento:");
                    System.out.println("Nome: ");
                    String nomeEvento = scanner.nextLine();
                    System.out.println("Endereço: ");
                    String endereco = scanner.nextLine();
                    System.out.println("Categoria (1 para Festa, 2 para Evento Esportivo, 3 para Show): ");
                    int categoria = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Horário (Formato: yyyy-MM-dd HH:mm): ");
                    String horarioStr = scanner.nextLine();
                    LocalDateTime horario = LocalDateTime.parse(horarioStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    System.out.println("Descrição: ");
                    String descricao = scanner.nextLine();

                    Evento evento = new Evento(nomeEvento, endereco, categoria, horario, descricao);
                    eventos.add(evento);
                    salvarEvento(evento);
                    break;
                case 3:
                    break;
                case 4:
                    logado = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    private static void salvarEvento(Evento evento) {
        try (FileWriter fw = new FileWriter(EVENTOS_ARQUIVO, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(evento.nome + ";" + evento.endereco + ";" + evento.categoria + ";" +
                    evento.horario.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ";" + evento.descricao);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar evento: " + e.getMessage());
        }
    }

    private static void carregarEventos() {
        try (Scanner scanner = new Scanner(new File(EVENTOS_ARQUIVO))) {
            while (scanner.hasNextLine()) {
                String[] dados = scanner.nextLine().split(";");
                LocalDateTime horario = LocalDateTime.parse(dados[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                eventos.add(new Evento(dados[0], dados[1], Integer.parseInt(dados[2]), horario, dados[4]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de eventos não encontrado. Criando novo arquivo.");
        }
    }
}
