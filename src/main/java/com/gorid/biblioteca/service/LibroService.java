package com.gorid.biblioteca.service;

import com.gorid.biblioteca.entity.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroService {
    List<Libro> listarLibros();
    List<Libro> buscarPorTitulo(String titulo);
    Libro guardarLibro(Libro libro);
    Libro obtenerLibro(Long id);
    boolean existeIsbn(String isbn);
    void eliminarLibro(Long id);
    Integer totalDisponibles();
    Optional<Libro> buscarPorId(Long id);
}
