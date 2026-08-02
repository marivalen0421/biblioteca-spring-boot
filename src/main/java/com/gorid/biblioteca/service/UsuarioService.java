package com.gorid.biblioteca.service;

import com.gorid.biblioteca.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario buscarPorCorreo(String correo);

    Usuario guardarUsuario(Usuario usuario);

    List<Usuario> listarUsuario();
    Usuario buscarPorId(Long id);

    void cambiarEstado(Long id);

    void cambiarRol(Long id);

    void restablecerPassword(Long id);

    void actualizarPerfil(String correoActual, String nombre, String correo,
                          String programa, Integer semestre);

    void cambiarPasswordPropia(String correo, String passwordActual,
                               String passwordNueva);
}
