import java.util.ArrayList;
import java.util.List;

public class Cliente {
    public String nome;
    public int idade;
    public int cpf;

    private List<Evento> eventosInscritos;

    private static List<Cliente> clientes = new ArrayList<>();
    private static Cliente usuarioLogado;

    public Cliente(String nome, int idade, int cpf) {
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.eventosInscritos = new ArrayList<>();
    }

    public static Cliente cadastrar(String nome, int idade, int cpf) {
        Cliente cliente = new Cliente(nome, idade, cpf);
        clientes.add(cliente);
        return cliente;
    }

    public static Cliente login(int cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.cpf == cpf) {
                usuarioLogado = cliente;
                return cliente;
            }
        }
        return null;
    }

    public static Cliente getUsuarioLogado() {
        return usuarioLogado;
    }

    public void entrarEvento(Evento evento) {
        eventosInscritos.add(evento);
        System.out.println("Você entrou no evento: " + evento.nome);
    }

    public List<Evento> getEventosInscritos() {
        return eventosInscritos;
    }
}
