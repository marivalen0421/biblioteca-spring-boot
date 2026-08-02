package com.gorid.biblioteca.controller;

import com.gorid.biblioteca.entity.Estudiante;
import com.gorid.biblioteca.entity.Usuario;
import com.gorid.biblioteca.service.EstudianteService;
import com.gorid.biblioteca.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    private final UsuarioService usuarioService;
    private final EstudianteService estudianteService;

    public PerfilController(UsuarioService usuarioService,
                            EstudianteService estudianteService) {

        this.usuarioService = usuarioService;
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public String perfil(Model model, Principal principal) {

        Usuario usuario = usuarioService.buscarPorCorreo(principal.getName());

        Estudiante estudiante =
                estudianteService.buscarPorCorreo(usuario.getCorreo());

        model.addAttribute("usuario", usuario);
        model.addAttribute("estudiante", estudiante);

        return "perfil";
    }

    @PostMapping("/datos")
    public String actualizarDatos(
            Principal principal,
            @RequestParam("nombre") String nombre,
            @RequestParam("correo") String correo,
            @RequestParam(value = "programa", required = false) String programa,
            @RequestParam(value = "semestre", required = false) Integer semestre,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        try {

            boolean correoCambiado =
                    !principal.getName().equalsIgnoreCase(correo.trim());

            usuarioService.actualizarPerfil(
                    principal.getName(),
                    nombre,
                    correo,
                    programa,
                    semestre
            );

            if (correoCambiado) {

                if (request.getSession(false) != null) {
                    request.getSession(false).invalidate();
                }

                SecurityContextHolder.clearContext();

                redirectAttributes.addFlashAttribute(
                        "success",
                        "Datos actualizados. Inicia sesión con tu nuevo correo."
                );

                return "redirect:/login";
            }

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Datos actualizados correctamente."
            );

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "redirect:/perfil";
    }

    @PostMapping("/password")
    public String cambiarPassword(
            Principal principal,
            @RequestParam("passwordActual") String passwordActual,
            @RequestParam("passwordNueva") String passwordNueva,
            @RequestParam("confirmarPassword") String confirmarPassword,
            RedirectAttributes redirectAttributes) {

        if (!passwordNueva.equals(confirmarPassword)) {

            redirectAttributes.addFlashAttribute(
                    "passwordError",
                    "La nueva contraseña y su confirmación no coinciden."
            );

            return "redirect:/perfil";
        }

        if (passwordNueva.length() < 8) {

            redirectAttributes.addFlashAttribute(
                    "passwordError",
                    "La nueva contraseña debe tener al menos 8 caracteres."
            );

            return "redirect:/perfil";
        }

        try {

            usuarioService.cambiarPasswordPropia(
                    principal.getName(),
                    passwordActual,
                    passwordNueva
            );

            redirectAttributes.addFlashAttribute(
                    "passwordSuccess",
                    "Contraseña actualizada correctamente."
            );

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "passwordError",
                    e.getMessage()
            );
        }

        return "redirect:/perfil";
    }

}