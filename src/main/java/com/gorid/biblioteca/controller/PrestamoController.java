package com.gorid.biblioteca.controller;

import com.gorid.biblioteca.entity.Prestamo;
import com.gorid.biblioteca.service.EstudianteService;
import com.gorid.biblioteca.service.LibroService;
import com.gorid.biblioteca.service.PrestamoService;
import com.gorid.biblioteca.service.NotificacionService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class PrestamoController {
    private final PrestamoService prestamoService;
    private final LibroService libroService;
    private final EstudianteService estudianteService;
    private final NotificacionService notificacionService;

    public PrestamoController(EstudianteService estudianteService, PrestamoService prestamoService,
                              LibroService libroService, NotificacionService notificacionService) {
        this.estudianteService = estudianteService;
        this.prestamoService = prestamoService;
        this.libroService = libroService;
        this.notificacionService = notificacionService;
    }
    @GetMapping("/prestamos")
    public String listarPrestamos(Model model){
        model.addAttribute("prestamos", prestamoService.listarPrestamo());
        return "prestamos";
    }
    @GetMapping("/prestamos/nuevo")
    public String nuevoPrestamo(Model model){
        model.addAttribute("prestamo", new Prestamo());
        model.addAttribute("libros", libroService.listarLibros());
        model.addAttribute("estudiantes", estudianteService.listarEstudiantes());

        return "nuevoPrestamo";
    }
    @PostMapping("/prestamos")
    public String guardarPrestamo(@ModelAttribute Prestamo prestamo, Model model,
                                  org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes){

        try{
            prestamo.setFechaPrestamo(LocalDate.now());
            prestamo.setFechaDevolucion(LocalDate.now().plusDays(10));
            prestamo.setEstado("Prestado");

            prestamoService.guardarPrestamo(prestamo);
            redirectAttributes.addFlashAttribute("success", "Préstamo registrado correctamente.");

            return "redirect:/prestamos";

        }catch (Exception e){

            e.printStackTrace();

            System.out.println("ENTRÓ AL CATCH");

            model.addAttribute("error", e.getMessage());
            model.addAttribute("prestamo", prestamo);
            model.addAttribute("libros", libroService.listarLibros());
            model.addAttribute("estudiantes", estudianteService.listarEstudiantes());

            return "nuevoPrestamo";
        }
    }
    @PostMapping("/prestamos/devolver/{id}")
    public String devolverPrestamo(@PathVariable("id") Long id,
                                   org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes){

        prestamoService.devolverPrestamo(id);
        redirectAttributes.addFlashAttribute("success", "Préstamo marcado como devuelto.");

        return "redirect:/prestamos";
    }
    @PostMapping("/prestamos/eliminar/{id}")
    public String eliminarPrestamo(@PathVariable("id") Long id,
                                   org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes){
        prestamoService.eliminarPrestamo(id);
        redirectAttributes.addFlashAttribute("success", "Préstamo eliminado correctamente.");

        return "redirect:/prestamos";
    }
    @GetMapping("/prestamos/detalle/{id}")
    public String detallePrestamo(@PathVariable("id") Long id, Model model){
        model.addAttribute("prestamo", prestamoService.obtenerPrestamo(id));

        return "detallePrestamo";
    }
    @GetMapping("/prestamos/vencidos")
    public String prestamosVencidos(Model mode){
        mode.addAttribute("prestamos", prestamoService.prestamosVencidos());

        return "prestamosVencidos";
    }

    @PostMapping("/prestamos/vencidos/{id}/notificar")
    public String notificarRetraso(@PathVariable("id") Long id,
                                   org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            notificacionService.notificarPrestamoVencido(id);
            redirectAttributes.addFlashAttribute("success", "Notificación enviada al estudiante.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/prestamos/vencidos";
    }
}
