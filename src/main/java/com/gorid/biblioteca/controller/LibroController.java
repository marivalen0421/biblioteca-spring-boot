package com.gorid.biblioteca.controller;

import com.gorid.biblioteca.entity.Libro;
import com.gorid.biblioteca.service.LibroService;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LibroController {
    private final LibroService libroService;

    public LibroController(LibroService libroService){
        this.libroService = libroService;
    }

    @GetMapping("/libros")
    public String listarLibros(Model model){
        model.addAttribute("libros", libroService.listarLibros());
        return "libros";
    }
    @GetMapping("/libros/nuevo")
    public String mostrarFormulario(Model model){
        model.addAttribute("libro", new Libro());
        return "nuevoLibro";
    }

    @PostMapping("/libros")
    public String guardarLibro(@Valid @ModelAttribute Libro libro, BindingResult result,
                               RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return "nuevoLibro";
        }
        try {
            libroService.guardarLibro(libro);
            redirectAttributes.addFlashAttribute("success", "Libro registrado correctamente.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/libros/nuevo";
        }
        return "redirect:/libros";
    }
    @GetMapping("/libros/editar/{id}")
    public String editarLibro(@PathVariable("id") Long id, Model model){
        Libro libro = libroService.obtenerLibro(id);

        model.addAttribute("libro", libro);

        return "editarLibro";
    }
    @PostMapping("/libros/editar/{id}")
    public String actualizarLibro(@PathVariable("id") Long id, @ModelAttribute Libro libro,
                                  RedirectAttributes redirectAttributes){
        Libro libroExistente = libroService.obtenerLibro(id);

        libroExistente.setTitulo(libro.getTitulo());
        libroExistente.setAnio(libro.getAnio());
        libroExistente.setAutor(libro.getAutor());
        libroExistente.setCantidad(libro.getCantidad());
        libroExistente.setCategoria(libro.getCategoria());
        libroExistente.setDisponibles(libro.getDisponibles());
        libroExistente.setIsbn(libro.getIsbn());

        libroService.guardarLibro(libroExistente);
        redirectAttributes.addFlashAttribute("success", "Libro actualizado correctamente.");

        return "redirect:/libros";
    }
    @PostMapping("/libros/eliminar/{id}")
    public String eliminarLibro(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        libroService.eliminarLibro(id);
        redirectAttributes.addFlashAttribute("success", "Libro eliminado correctamente.");

        return "redirect:/libros";
    }
    @GetMapping("/libros/buscar")
    public String buscarLibro(@RequestParam("titulo") String titulo, Model model){
        model.addAttribute("libros",
                libroService.buscarPorTitulo(titulo));
        return "libros";
    }

}
