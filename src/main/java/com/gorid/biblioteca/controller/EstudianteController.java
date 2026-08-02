package com.gorid.biblioteca.controller;

import com.gorid.biblioteca.entity.Estudiante;
import com.gorid.biblioteca.service.EstudianteService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EstudianteController {
    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService){
        this.estudianteService =estudianteService;
    }

    @GetMapping("/estudiantes")
    public String listarEstudiantes(Model model){
        model.addAttribute("estudiantes", estudianteService.listarEstudiantes());
        return "estudiantes";
    }
    @GetMapping("/estudiantes/nuevo")
    public String mostrarFormulario(Model model){
        model.addAttribute("estudiantes", new Estudiante());
        return "nuevoEstudiante";
    }

    @PostMapping("/estudiantes")
    public String guardarEstudiante(@ModelAttribute Estudiante estudiante, RedirectAttributes redirectAttributes){
        try {
            estudianteService.guardarEstudiante(estudiante);
            redirectAttributes.addFlashAttribute("success", "Estudiante registrado correctamente. Su usuario es el correo y la contraseña inicial es el número de documento.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/estudiantes/nuevo";
        }
        return "redirect:/estudiantes";
    }
    @GetMapping("/estudiantes/editar/{id}")
    public String editarEstudiante(@PathVariable("id") Long id, Model model){
        model.addAttribute("estudiantes", estudianteService.obtenerEstudiante(id));
        return "editarEstudiante";
    }
    @PostMapping("/estudiantes/editar/{id}")
    public String actualizarEstudiantes(@PathVariable("id") Long id, @ModelAttribute Estudiante estudiante,
                                        RedirectAttributes redirectAttributes){
        try {
            estudianteService.actualizarEstudiante(id, estudiante);
            redirectAttributes.addFlashAttribute("success", "Estudiante actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/estudiantes/editar/" + id;
        }
        return "redirect:/estudiantes";
    }
    @PostMapping("/estudiantes/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        estudianteService.eliminarEstudiante(id);
        redirectAttributes.addFlashAttribute("success", "Estudiante eliminado correctamente.");
        return "redirect:/estudiantes";
    }
    @GetMapping("/estudiantes/buscar")
    public String buscarEstudiante(@RequestParam("nombre") String nombre, Model model){
        model.addAttribute("estudiantes",
                estudianteService.buscarPorNombre(nombre));
        return "estudiantes";
    }


}
