package com.gorid.biblioteca.repository;

import com.gorid.biblioteca.entity.Notificacion;
import com.gorid.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    boolean existsByPrestamoAndLeidaFalse(Prestamo prestamo);

    List<Notificacion> findByUsuarioCorreoAndLeidaFalseAndPrestamoEstadoOrderByFechaCreacionDesc(
            String correo, String estado);
}
