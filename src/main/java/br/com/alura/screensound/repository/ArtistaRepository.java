package br.com.alura.screensound.repository;

import br.com.alura.screensound.model.Artista;
import br.com.alura.screensound.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    Optional<Artista> findByNomeContainingIgnoreCase(String nome);

    @Query("select m from Artista a join a.musicas m where a.nome ilike %:nome%")
    List<Musica> buscaMusicaPorArtista(String nome);
}
