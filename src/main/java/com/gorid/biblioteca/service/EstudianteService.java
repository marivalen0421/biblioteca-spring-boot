package com.gorid.biblioteca.service;

import com.gorid.biblioteca.entity.Estudiante;
import com.gorid.biblioteca.entity.Libro;

import java.util.List;

public interface EstudianteService {

    List<Estudiante> listarEstudiantes();

    Estudiante guardarEstudiante(Estudiante estudiante);
    Estudiante buscarPorCorreo(String correo);
    Estudiante obtenerEstudiante(Long id);
    List<Estudiante> buscarPorNombre(String nombre);
    boolean existeDocumento(String documento);

    Estudiante actualizarEstudiante(Long id, Estudiante estudiante);

    void eliminarEstudiante(Long id);
}
