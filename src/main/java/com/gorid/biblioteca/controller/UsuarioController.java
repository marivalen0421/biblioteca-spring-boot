package com.gorid.biblioteca.controller;

import com.gorid.biblioteca.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model){
        model.addAttribute("usuarios", service.listarUsuario());

        return "usuarios";
    }
    @PostMapping("/usuarios/estado/{id}")
    public String cambiarEstado(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        service.cambiarEstado(id);
        redirectAttributes.addFlashAttribute("success", "Estado del usuario actualizado.");

        return "redirect:/usuarios";
    }

    @PostMapping("/usuarios/rol/{id}")
    public String cambiarRol(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        service.cambiarRol(id);
        redirectAttributes.addFlashAttribute("success", "Rol del usuario actualizado.");
        return "redirect:/usuarios";
    }
    @PostMapping("/usuarios/password/{id}")
    public String restablecerPassword(@PathVariable Long id, RedirectAttributes redirectAttributes){

        service.restablecerPassword(id);
        redirectAttributes.addFlashAttribute("success", "Contraseña restablecida. El usuario deberá cambiarla al ingresar.");

        return "redirect:/usuarios";

    }
}
