package com.gorid.biblioteca.controller;

import com.gorid.biblioteca.entity.Prestamo;
import com.gorid.biblioteca.entity.Usuario;
import com.gorid.biblioteca.service.PrestamoService;
import com.gorid.biblioteca.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class MisPrestamosController {
    private final PrestamoService prestamoService;

    private final UsuarioService usuarioService;

    public MisPrestamosController(PrestamoService prestamoService, UsuarioService usuarioService) {
        this.prestamoService = prestamoService;
        this.usuarioService = usuarioService;
    }
    @GetMapping("/mis-prestamos")
    public String listar(Model model, Principal principal){
        Usuario usuario = usuarioService.buscarPorCorreo(principal.getName());

        model.addAttribute("usuario", usuario);

        model.addAttribute("prestamos", prestamoService.buscarPorCorreoUsuario(usuario.getCorreo()));

        return "misPrestamos";
    }
    @GetMapping("/mis-prestamos/detalle/{id}")
    public String detallePrestamo(@PathVariable("id") Long id, Model model, Principal principal) {
        Usuario usuario = usuarioService.buscarPorCorreo(principal.getName());

        Prestamo prestamo = prestamoService.buscarPorId(id).orElseThrow(() -> new RuntimeException("Prestamo no encontrado"));
        if (!prestamo.getEstudiante().getUsuario().getCorreo().equals(usuario.getCorreo())){
            return "redirect:/mis-prestamos";
        }
        model.addAttribute("prestamo", prestamo);

        return "detallePrestamoEstudiante";
    }
}
