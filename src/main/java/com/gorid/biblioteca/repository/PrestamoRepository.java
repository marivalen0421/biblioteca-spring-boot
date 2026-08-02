package com.gorid.biblioteca.repository;

import com.gorid.biblioteca.entity.Estudiante;
import com.gorid.biblioteca.entity.Libro;
import com.gorid.biblioteca.entity.Prestamo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    boolean existsByEstudianteAndLibroAndEstado(
            Estudiante estudiante,
            Libro libro,
            String estado

    );
    Integer countByEstadoAndFechaDevolucionBefore(String estado, LocalDate fecha);
    Integer countByEstado(String estado);

    List<Prestamo> findByEstadoAndFechaDevolucionBefore(
            String estado,
            LocalDate fecha
    );
    List<Prestamo> findAllByOrderByFechaPrestamoDesc(Pageable pageable);
    List<Prestamo> findByEstudianteUsuarioCorreo(String correo);
}
