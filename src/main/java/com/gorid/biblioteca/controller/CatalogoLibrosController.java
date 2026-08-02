package com.gorid.biblioteca.controller;

import com.gorid.biblioteca.entity.Libro;
import com.gorid.biblioteca.service.LibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CatalogoLibrosController {
    private final LibroService libroService;

    public CatalogoLibrosController(LibroService libroService) {
        this.libroService = libroService;
    }
    @GetMapping("/catalogoL")
    public String catalogoL(Model model){
        model.addAttribute("libros", libroService.listarLibros());

        return "catalogoL";
    }

    @GetMapping("/catalogoL/detalle/{id}")
    public String detalle(@PathVariable("id") Long id, Model model){
        Libro libro = libroService.buscarPorId(id).orElseThrow(()-> new RuntimeException("libro no encontrado"));
        model.addAttribute("libro", libro);

        return "detalleLibro";
    }
}
