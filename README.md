#  Sistema de Gestión de Biblioteca

Sistema web desarrollado con **Java** y **Spring Boot** para administrar una biblioteca universitaria. La aplicación permite gestionar libros, estudiantes y préstamos mediante un sistema de autenticación con roles.

---

##  Características

### 👨‍💼 Administrador

- Inicio de sesión.
- Gestión de libros (Crear, Editar, Eliminar y Consultar).
- Gestión de estudiantes.
- Creación automática de usuarios para los estudiantes.
- Gestión de préstamos.
- Registro de devoluciones.
- Consulta de préstamos vencidos.
- Dashboard administrativo.

### Estudiante

- Inicio de sesión.
- Catálogo de libros.
- Visualización del detalle de cada libro.
- Consulta de préstamos realizados.
- Administración del perfil.
- Cambio de contraseña.

---

# 🛠 Tecnologías utilizadas

| Tecnología | Uso |
|------------|-----|
| Java 24 | Lenguaje principal |
| Spring Boot | Framework Backend |
| Spring MVC | Arquitectura MVC |
| Spring Security | Autenticación y autorización |
| Spring Data JPA | Persistencia de datos |
| Hibernate | ORM |
| Thymeleaf | Motor de plantillas |
| MySQL | Base de datos |
| Bootstrap 5 | Diseño responsivo |
| HTML5 | Interfaz |
| CSS3 | Estilos |
| Font Awesome | Iconografía |
| Maven | Gestión de dependencias |
| Git | Control de versiones |
| GitHub | Repositorio |

---

# Estructura del proyecto

```
src
│
├── controller
├── service
├── repository
├── entity
├── config
│
├── resources
│   ├── static
│   │     ├── css
│   │     ├── img
│   │
│   └── templates
│
└── BibliotecaApplication.java
```

---

#  Roles del sistema

## Administrador

- Gestiona libros
- Gestiona estudiantes
- Gestiona préstamos
- Consulta préstamos vencidos
- Administra usuarios

## Estudiante

- Consulta catálogo
- Visualiza detalles de libros
- Consulta sus préstamos
- Edita su perfil
- Cambia su contraseña


#  Instalación

## Clonar el proyecto

```bash
git clone https://github.com/marivalen0421/biblioteca-spring-boot.git
```

Entrar al proyecto

```bash
cd biblioteca-spring-boot
```

Crear la base de datos

```sql
CREATE DATABASE biblioteca;
```

Configurar el archivo:

```
application.properties
```

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Ejecutar

```bash
mvn spring-boot:run
```

---

#  Funcionalidades implementadas

- ✔ Inicio de sesión
- ✔ Roles de usuario
- ✔ CRUD de libros
- ✔ CRUD de estudiantes
- ✔ Gestión de préstamos
- ✔ Préstamos vencidos
- ✔ Catálogo
- ✔ Perfil
- ✔ Cambio de contraseña
- ✔ Validaciones
- ✔ Diseño responsive

---
