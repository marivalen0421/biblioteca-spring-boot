package com.gorid.biblioteca.service;

import com.gorid.biblioteca.entity.Prestamo;

import java.util.List;
import java.util.Optional;

public interface PrestamoService {
    List<Prestamo> listarPrestamo();

    Prestamo guardarPrestamo(Prestamo prestamo);
    Prestamo obtenerPrestamo(Long id);
    void eliminarPrestamo(Long id);
    void devolverPrestamo(Long id);
    Integer prestamosAct();
    List<Prestamo> buscarPorCorreoUsuario(String correo);
    Optional<Prestamo> buscarPorId(Long id);
    Integer prestamosVenc();
    List<Prestamo> prestamosVencidos();
    long contarPv();
    List<Prestamo> ultimoPrestamo();
}
