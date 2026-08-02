package com.gorid.biblioteca.entity;

import jakarta.persistence.*;

@Entity
@Table(name= "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String documento;
    private String correo;
    private String programa;
    private Integer semestre;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Estudiante() {
    }

    public Estudiante(String correo, String documento, Long id, String nombre, String programa, Integer semestre, Usuario usuario) {
        this.correo = correo;
        this.documento = documento;
        this.id = id;
        this.nombre = nombre;
        this.programa = programa;
        this.semestre = semestre;
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
