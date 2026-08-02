package com.gorid.biblioteca.service;

import com.gorid.biblioteca.entity.Notificacion;

import java.util.List;

public interface NotificacionService {
    void notificarPrestamoVencido(Long prestamoId);
    List<Notificacion> listarNoLeidas(String correo);
}
