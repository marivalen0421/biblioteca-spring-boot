package com.gorid.biblioteca.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ManejadorErroresController {

    @ExceptionHandler(IllegalArgumentException.class)
    public String manejarErrorDeValidacion(IllegalArgumentException exception, Model model) {
        model.addAttribute("titulo", "No fue posible completar la acción");
        model.addAttribute("mensaje", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String manejarErrorGeneral(Exception exception, Model model) {
        model.addAttribute("titulo", "Ocurrió un error inesperado");
        model.addAttribute("mensaje", "Intenta nuevamente. Si el problema continúa, contacta al administrador.");
        return "error";
    }
}
