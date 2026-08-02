package com.gorid.biblioteca.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.ISBN;

@Entity
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String correo;

    private String password;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    private String foto;
    private boolean activo = true;
    private boolean passwordTemporal = false;

    public Usuario() {
    }

    public Usuario(boolean activo, String correo, String foto, Long id, String nombre, String password, Rol rol) {
        this.activo = activo;
        this.correo = correo;
        this.foto = foto;
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isPasswordTemporal() {
        return passwordTemporal;
    }

    public void setPasswordTemporal(boolean passwordTemporal) {
        this.passwordTemporal = passwordTemporal;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
