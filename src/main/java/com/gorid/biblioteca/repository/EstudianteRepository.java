package com.gorid.biblioteca.repository;

import com.gorid.biblioteca.entity.Estudiante;
import com.gorid.biblioteca.entity.Libro;
import com.gorid.biblioteca.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    List<Estudiante> findByNombreContainingIgnoreCase(String nombre);
    Optional<Estudiante> findByDocumento(String documento);
    Optional<Estudiante> findByCorreo(String correo);
    Optional<Estudiante> findByUsuario(Usuario usuario);
}
