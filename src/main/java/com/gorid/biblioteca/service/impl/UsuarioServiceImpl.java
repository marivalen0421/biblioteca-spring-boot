package com.gorid.biblioteca.service.impl;

import com.gorid.biblioteca.entity.Estudiante;
import com.gorid.biblioteca.entity.Rol;
import com.gorid.biblioteca.entity.Usuario;
import com.gorid.biblioteca.repository.EstudianteRepository;
import com.gorid.biblioteca.repository.UsuarioRepository;
import com.gorid.biblioteca.service.LibroService;
import com.gorid.biblioteca.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EstudianteRepository estudianteRepository;

    public UsuarioServiceImpl(EstudianteRepository estudianteRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.estudianteRepository = estudianteRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario buscarPorCorreo(String correo){
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }
    @Override
    public Usuario guardarUsuario(Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }
    @Override
    public List<Usuario> listarUsuario(){
        return usuarioRepository.findAll();
    }
    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    @Override
    public void cambiarEstado(Long id) {

        Usuario usuario = buscarPorId(id);

        usuario.setActivo(!usuario.isActivo());

        usuarioRepository.save(usuario);

    }
    @Override
    public void cambiarRol(Long id) {

        Usuario usuario = buscarPorId(id);

        if(usuario.getRol() == Rol.ADMIN){

            usuario.setRol(Rol.ESTUDIANTE);

        }else{

            usuario.setRol(Rol.ADMIN);

        }

        usuarioRepository.save(usuario);

    }
    @Override
    public void restablecerPassword(Long id) {

        Usuario usuario = buscarPorId(id);

        Estudiante estudiante = estudianteRepository
                .findByUsuario(usuario)
                .orElse(null);

        if(estudiante != null){

            usuario.setPassword(
                    passwordEncoder.encode(estudiante.getDocumento())
            );
            usuario.setPasswordTemporal(true);

            usuarioRepository.save(usuario);

        }

    }

    @Transactional
    @Override
    public void actualizarPerfil(String correoActual, String nombre, String correo,
                                 String programa, Integer semestre) {
        if (nombre == null || nombre.isBlank() || correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("El nombre y el correo son obligatorios");
        }
        Usuario usuario = usuarioRepository.findByCorreo(correoActual)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        String correoNormalizado = correo.trim().toLowerCase(Locale.ROOT);

        usuarioRepository.findByCorreo(correoNormalizado)
                .filter(otroUsuario -> !otroUsuario.getId().equals(usuario.getId()))
                .ifPresent(otroUsuario -> {
                    throw new IllegalArgumentException("El correo ya está registrado");
                });

        usuario.setNombre(nombre.trim());
        usuario.setCorreo(correoNormalizado);

        estudianteRepository.findByUsuario(usuario).ifPresent(estudiante -> {
            if (programa == null || programa.isBlank() || semestre == null || semestre < 1) {
                throw new IllegalArgumentException("El programa y el semestre son obligatorios");
            }
            estudiante.setNombre(nombre.trim());
            estudiante.setCorreo(correoNormalizado);
            estudiante.setPrograma(programa == null ? null : programa.trim());
            estudiante.setSemestre(semestre);
            estudianteRepository.save(estudiante);
        });
        usuarioRepository.save(usuario);
    }

    @Transactional
    @Override
    public void cambiarPasswordPropia(String correo, String passwordActual,
                                      String passwordNueva) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        if (!passwordEncoder.matches(passwordActual, usuario.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual no es correcta");
        }
        usuario.setPassword(passwordEncoder.encode(passwordNueva));
        usuario.setPasswordTemporal(false);
        usuarioRepository.save(usuario);
    }


}
