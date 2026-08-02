package com.gorid.biblioteca.controller;

import com.gorid.biblioteca.entity.Libro;
import com.gorid.biblioteca.service.EstudianteService;
import com.gorid.biblioteca.service.LibroService;
import com.gorid.biblioteca.service.PrestamoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final LibroService libroService;
    private final EstudianteService estudianteService;
    private final PrestamoService prestamoService;

    public HomeController(EstudianteService estudianteService, LibroService libroService, PrestamoService prestamoService) {
        this.estudianteService = estudianteService;
        this.libroService = libroService;
        this.prestamoService = prestamoService;
    }

    @GetMapping("/")
    public String inicio(Model model){
        model.addAttribute("totalLibros", libroService.listarLibros().size());

        model.addAttribute("totalEstudiantes",
                estudianteService.listarEstudiantes().size());

        model.addAttribute("totalPrestamos", prestamoService.listarPrestamo().size());

        model.addAttribute("prestamos", prestamoService.listarPrestamo());
        model.addAttribute("prestamosVencidos", prestamoService.contarPv());
        model.addAttribute("ultimosPrestamos", prestamoService.ultimoPrestamo());
        model.addAttribute("librosDisponibles",
                libroService.listarLibros()
                        .stream()
                        .mapToInt(Libro::getDisponibles)
                        .sum());

        model.addAttribute("librosPrestados",
                libroService.listarLibros()
                        .stream()
                        .mapToInt(l -> l.getCantidad() - l.getDisponibles())
                        .sum());
        model.addAttribute("prestados",
                prestamoService.listarPrestamo()
                        .stream()
                        .filter(p -> p.getEstado().equals("Prestado"))
                        .count());

        model.addAttribute("devueltos",
                prestamoService.listarPrestamo()
                        .stream()
                        .filter(p -> p.getEstado().equals("Devuelto"))
                        .count());
        return "index";
    }
}
