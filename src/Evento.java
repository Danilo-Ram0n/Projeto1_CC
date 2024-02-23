import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Evento {
    public String nome;
    public String endereco;
    public int categoria;
    public LocalDateTime horario;
    public String descricao;

    private static List<Evento> listaEventos = new ArrayList<>();

    public Evento(String nome, String endereco, int categoria, LocalDateTime horario, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
        listaEventos.add(this);
    }

    public static List<Evento> getListaEventos() {
        return listaEventos;
    }

    public String TipoCategoria() {
        switch (categoria) {
            case 1:
                return "Festa";
            case 2:
                return "Evento Esportivo";
            case 3:
                return "Show";
            default:
                return "Categoria não listada";
        }
    }

    public boolean jaOcorreu() {
        LocalDateTime agora = LocalDateTime.now();
        return horario.isBefore(agora);
    }

    public static List<Evento> proximosEventos() {
        LocalDateTime agora = LocalDateTime.now();
        List<Evento> eventosProximos = new ArrayList<>();
        for (Evento evento : listaEventos) {
            if (evento.horario.isAfter(agora)) {
                eventosProximos.add(evento);
            }
        }
        return eventosProximos;
    }
}
