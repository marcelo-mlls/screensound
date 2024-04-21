package br.com.alura.screensound.principal;

import br.com.alura.screensound.model.Artista;
import br.com.alura.screensound.model.Musica;
import br.com.alura.screensound.model.TipoArtista;
import br.com.alura.screensound.repository.ArtistaRepository;
import br.com.alura.screensound.serivice.ConsultaChatGpt;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final ArtistaRepository repository;
    private Scanner leitura = new Scanner(System.in);

    public Principal(ArtistaRepository repository) {
        this.repository = repository;
    }

    public void exibeMenu() {
        var opcao = -1;

        while (opcao!= 9) {
            var menu = """
                    *** Screen Sound Músicas ***                    
                                        
                    1- Cadastrar artistas
                    2- Cadastrar músicas
                    3- Listar músicas
                    4- Buscar músicas por artistas
                    5- Pesquisar dados sobre um artista
                                    
                    9 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtista();
                    break;
                case 5:
                    pesquisarDadosDoArtista();
                    break;
                case 9:
                    System.out.println("Encerrando a aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void pesquisarDadosDoArtista() {
        System.out.println("Pesquisar sobre qual Artista? ");
        var texto = leitura.nextLine();
        var resposta = ConsultaChatGpt.obterInformacao(texto);
        System.out.println(resposta.trim());
    }

    private void buscarMusicasPorArtista() {
        System.out.println("Buscar Músicas de qual Artista? ");
        var nomeArtista = leitura.nextLine();
        List<Musica> musicas = repository.buscaMusicaPorArtista(nomeArtista);
        musicas.forEach(System.out::println);
    }

    private void listarMusicas() {
        List<Artista> artistas = repository.findAll();
        artistas.forEach(a->a.getMusicas().forEach(System.out::println));
    }

    private void cadastrarMusicas() {
        System.out.println("Cadastrar mísica de qual artista? ");
        var nome = leitura.nextLine();
        Optional<Artista> artista = repository.findByNomeContainingIgnoreCase(nome);
        if(artista.isPresent()){
            System.out.println("Digite o nome da Música");
            var nomeMusica  = leitura.nextLine();
            Musica musica = new Musica(nomeMusica);
            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);
            repository.save(artista.get());

        }else {
            System.out.println("Artista não encontrado");
        }
    }

    private void cadastrarArtistas() {
        var cadastrarNovo = "S";

        while (cadastrarNovo.equalsIgnoreCase("s")) {
            System.out.println("Informe o nome do Artista: ");
            var nome = leitura.nextLine();
            System.out.println("Informe o tipo [solo, dupla, banda]");
            var tipo = leitura.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome, tipoArtista);
            repository.save(artista);
            System.out.println("Cadastrar novo artista? (S/N)");
            cadastrarNovo = leitura.nextLine();
        }
    }
}
