package com.gorid.biblioteca.repository;

import com.gorid.biblioteca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByTituloContainingIgnoreCase(String Titulo);

    Optional<Libro> findByIsbn(String isbn);
    Integer countByDisponiblesGreaterThan(int disponibles);
}
