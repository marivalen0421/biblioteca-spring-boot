package com.gorid.biblioteca.service.impl;

import com.gorid.biblioteca.entity.Libro;
import com.gorid.biblioteca.repository.LibroRepository;
import com.gorid.biblioteca.service.LibroService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImpl implements LibroService {
    private final LibroRepository libroRepository;

    public LibroServiceImpl(LibroRepository libroRepository){
        this.libroRepository = libroRepository;
    }
    @Override
    public List<Libro> listarLibros(){
        return libroRepository.findAll();
    }
    @Override
    public List<Libro> buscarPorTitulo(String titulo){
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }
    @Override
    public Optional<Libro> buscarPorId(Long id){
        return libroRepository.findById(id);
    }

    @Override
    public Libro guardarLibro(Libro libro) {

        Libro libroExistente = libroRepository.findByIsbn(libro.getIsbn()).orElse(null);

        if (libroExistente != null &&
                !libroExistente.getId().equals(libro.getId())) {

            throw new RuntimeException("El ISBN ya existe");
        }

        return libroRepository.save(libro);
    }
    @Override
    public Libro obtenerLibro(Long id){
        return libroRepository.findById(id).orElse(null);

    }
    @Override
    public void eliminarLibro(Long id){
        libroRepository.deleteById(id);
    }
    @Override
    public boolean existeIsbn(String isbn){
        return libroRepository.findByIsbn(isbn).isPresent();
    }
    @Override
    public Integer totalDisponibles(){
        return libroRepository.countByDisponiblesGreaterThan(0);
    }
}
