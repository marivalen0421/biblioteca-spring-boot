package com.gorid.biblioteca.service.impl;

import com.gorid.biblioteca.entity.Notificacion;
import com.gorid.biblioteca.entity.Prestamo;
import com.gorid.biblioteca.repository.NotificacionRepository;
import com.gorid.biblioteca.repository.PrestamoRepository;
import com.gorid.biblioteca.service.NotificacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificacionServiceImpl implements NotificacionService {
    private final NotificacionRepository notificacionRepository;
    private final PrestamoRepository prestamoRepository;

    public NotificacionServiceImpl(NotificacionRepository notificacionRepository,
                                  PrestamoRepository prestamoRepository) {
        this.notificacionRepository = notificacionRepository;
        this.prestamoRepository = prestamoRepository;
    }

    @Transactional
    @Override
    public void notificarPrestamoVencido(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));
        if (!"Prestado".equals(prestamo.getEstado())
                || !prestamo.getFechaDevolucion().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("El préstamo no está vencido");
        }
        if (notificacionRepository.existsByPrestamoAndLeidaFalse(prestamo)) {
            return;
        }

        Notificacion notificacion = new Notificacion();
        notificacion.setUsuario(prestamo.getEstudiante().getUsuario());
        notificacion.setPrestamo(prestamo);
        notificacion.setMensaje("Tienes retraso en la devolución de: "
                + prestamo.getLibro().getTitulo() + ". Fecha límite: "
                + prestamo.getFechaDevolucion() + ".");
        notificacionRepository.save(notificacion);
    }

    @Override
    public List<Notificacion> listarNoLeidas(String correo) {
        return notificacionRepository
                .findByUsuarioCorreoAndLeidaFalseAndPrestamoEstadoOrderByFechaCreacionDesc(correo, "Prestado");
    }
}
