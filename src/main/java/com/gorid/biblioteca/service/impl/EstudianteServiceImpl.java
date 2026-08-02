package com.gorid.biblioteca.service.impl;

import com.gorid.biblioteca.entity.Estudiante;
import com.gorid.biblioteca.entity.Rol;
import com.gorid.biblioteca.entity.Usuario;
import com.gorid.biblioteca.repository.EstudianteRepository;
import com.gorid.biblioteca.repository.UsuarioRepository;
import com.gorid.biblioteca.service.EstudianteService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Locale;

@Service
public class EstudianteServiceImpl implements EstudianteService {
    private final EstudianteRepository estudianteRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.estudianteRepository = estudianteRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Estudiante> listarEstudiantes(){
        return estudianteRepository.findAll();
    }
    @Transactional
    @Override
    public Estudiante guardarEstudiante(Estudiante estudiante){
        if (estudianteRepository.findByDocumento(estudiante.getDocumento()).isPresent()){
            throw new RuntimeException("Ya existe un estudiante con ese documento");
        }
        if (usuarioRepository.findByCorreo(estudiante.getCorreo()).isPresent()){
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(estudiante.getNombre());
        usuario.setCorreo(estudiante.getCorreo());

        usuario.setPassword(passwordEncoder.encode(estudiante.getDocumento()));
        usuario.setPasswordTemporal(true);
        usuario.setRol(Rol.ESTUDIANTE);
        usuario.setActivo(true);
        usuarioRepository.save(usuario);
        estudiante.setUsuario(usuario);
        return estudianteRepository.save(estudiante);
    }
    @Override
    public Estudiante obtenerEstudiante(Long id){
        return estudianteRepository.findById(id).orElse(null);
    }


    @Override
    public List<Estudiante> buscarPorNombre(String nombre) {
        return estudianteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public void eliminarEstudiante(Long id){
        estudianteRepository.deleteById(id);
    }
    @Override
    public boolean existeDocumento(String documento){
        return estudianteRepository.findByDocumento(documento).isPresent();

    }
    @Override
    public Estudiante buscarPorCorreo(String correo){
        return estudianteRepository.findByCorreo(correo).orElse(null);
    }

    @Transactional
    @Override
    public Estudiante actualizarEstudiante(Long id, Estudiante datos) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        String correoNormalizado = datos.getCorreo().trim().toLowerCase(Locale.ROOT);

        estudianteRepository.findByDocumento(datos.getDocumento())
                .filter(otroEstudiante -> !otroEstudiante.getId().equals(id))
                .ifPresent(otroEstudiante -> {
                    throw new IllegalArgumentException("Ya existe un estudiante con ese documento");
                });

        Usuario usuario = estudiante.getUsuario();
        usuarioRepository.findByCorreo(correoNormalizado)
                .filter(otroUsuario -> !otroUsuario.getId().equals(usuario.getId()))
                .ifPresent(otroUsuario -> {
                    throw new IllegalArgumentException("Ya existe un usuario con ese correo");
                });

        estudiante.setNombre(datos.getNombre().trim());
        estudiante.setDocumento(datos.getDocumento().trim());
        estudiante.setCorreo(correoNormalizado);
        estudiante.setPrograma(datos.getPrograma().trim());
        estudiante.setSemestre(datos.getSemestre());
        usuario.setNombre(estudiante.getNombre());
        usuario.setCorreo(correoNormalizado);
        usuarioRepository.save(usuario);
        return estudianteRepository.save(estudiante);
    }
}
