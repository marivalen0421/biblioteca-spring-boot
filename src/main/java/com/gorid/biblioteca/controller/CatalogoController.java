package com.gorid.biblioteca.controller;

import com.gorid.biblioteca.entity.Usuario;
import com.gorid.biblioteca.service.UsuarioService;
import com.gorid.biblioteca.service.NotificacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class CatalogoController {

    private final UsuarioService usuarioService;
    private final NotificacionService notificacionService;

    public CatalogoController(UsuarioService usuarioService, NotificacionService notificacionService) {
        this.usuarioService = usuarioService;
        this.notificacionService = notificacionService;
    }

    @GetMapping("/catalogo")
    public String catalogo(Model model, Principal principal){
        Usuario usuario = usuarioService.buscarPorCorreo(principal.getName());
        model.addAttribute("usuario", usuario);
        model.addAttribute("notificaciones", notificacionService.listarNoLeidas(usuario.getCorreo()));

        return "catalogo";
    }


}
