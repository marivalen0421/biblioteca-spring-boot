package com.gorid.biblioteca.service.impl;

import com.gorid.biblioteca.entity.Libro;
import com.gorid.biblioteca.entity.Prestamo;
import com.gorid.biblioteca.repository.LibroRepository;
import com.gorid.biblioteca.repository.PrestamoRepository;
import com.gorid.biblioteca.service.PrestamoService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoServiceImpl implements PrestamoService {
    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository, LibroRepository libroRepository){
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
    }

    @Override
    public List<Prestamo> listarPrestamo(){
        return prestamoRepository.findAll();
    }

    @Override
    public Prestamo guardarPrestamo(Prestamo prestamo){
        Libro libro = prestamo.getLibro();

        if (libro.getDisponibles()<= 0){
            throw new RuntimeException("No hay Libros disponibles");
        }
        if(prestamoRepository.existsByEstudianteAndLibroAndEstado(
                prestamo.getEstudiante(),
                prestamo.getLibro(),
                "Prestado"
        )){
            throw new RuntimeException("El estudiante ya tiene ese libro prestado");
        }

        libro.setDisponibles(libro.getDisponibles()-1);
        libroRepository.save(libro);
        prestamo.setEstado("Prestado");
        return prestamoRepository.save(prestamo);

    }
    @Override
    public Prestamo obtenerPrestamo(Long id){
        return prestamoRepository.findById(id).orElse(null);
    }
    @Override
    public void eliminarPrestamo(Long id){
        prestamoRepository.deleteById(id);
    }
    @Override
    public void devolverPrestamo(Long id){
        Prestamo prestamo = prestamoRepository.findById(id).orElse(null);

        if (prestamo !=null && prestamo.getEstado().equals("Prestado")){
            prestamo.setEstado("Devuelto");
            prestamo.setFecha(LocalDate.now());
            Libro libro = prestamo.getLibro();
            libro.setDisponibles(libro.getDisponibles()+1);

            libroRepository.save(libro);
            prestamoRepository.save(prestamo);

        }

    }
    @Override
    public Integer prestamosAct(){
        return prestamoRepository.countByEstado("Prestado");
    }
    @Override
    public Integer prestamosVenc(){
        return prestamoRepository.countByEstadoAndFechaDevolucionBefore("Prestado", LocalDate.now());
    }
    @Override
    public List<Prestamo> prestamosVencidos(){
        return prestamoRepository.findByEstadoAndFechaDevolucionBefore("Prestado", LocalDate.now());
    }
    @Override
    public long contarPv(){
        return prestamoRepository.findByEstadoAndFechaDevolucionBefore("Prestado", LocalDate.now()).size();
    }
    @Override
    public List<Prestamo> ultimoPrestamo(){
        return prestamoRepository.findAllByOrderByFechaPrestamoDesc(PageRequest.of(0,5));
    }
    @Override
    public List<Prestamo> buscarPorCorreoUsuario(String correo) {
        return prestamoRepository.findByEstudianteUsuarioCorreo(correo);
    }
    @Override
    public Optional<Prestamo> buscarPorId(Long id){
        return prestamoRepository.findById(id);
    }

}
