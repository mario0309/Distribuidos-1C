# BasketHub

BasketHub es una aplicación web desarrollada con Spring Boot para la gestión de usuarios, equipos y jugadores de baloncesto.

El proyecto ha sido realizado para la asignatura de Sistemas Distribuidos como continuación de la práctica anterior, mejorando la interfaz, la seguridad y la organización general de la aplicación.

---

## Tecnologías utilizadas

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- Thymeleaf
- Bootstrap
- MySQL
- Docker
- SonarQube
- Google Maps

---

## Funcionalidades principales

- Página principal con diseño tipo dashboard
- Login personalizado y logout seguro
- Mantenimiento de usuarios
- CRUD de equipos
- CRUD de jugadores
- Control de acceso por roles
- Pantalla 403 de acceso denegado
- Protección para no borrar el último usuario administrador
- Integración básica de Google Maps
- Análisis estático con SonarQube

---

## Roles del sistema

### ADMIN
- Acceso completo
- Gestiona usuarios, equipos y jugadores

### MANAGER
- Gestiona equipos y jugadores
- No puede acceder al módulo de usuarios

### PLAYER
- Solo lectura
- Puede consultar equipos, jugadores y mapa

---

## Usuarios por defecto

Al arrancar la aplicación se crean automáticamente estos usuarios si no existen:

- admin / admin
- manager / manager
- player / player

También se crean automáticamente los roles:

- ROLE_ADMIN
- ROLE_MANAGER
- ROLE_PLAYER

---

## Base de datos

La aplicación utiliza MySQL para almacenar la información de usuarios, roles, equipos y jugadores.

---

## Ejecución del proyecto

### Requisito importante
Para ejecutar la aplicación es necesario usar **JDK 24**.

### Arranque
El proyecto incluye un archivo **Arrancar_APP.bat** para facilitar la ejecución.

Pasos:
1. Asegurarse de tener configurado **JDK 24**
2. Ejecutar el archivo **Arrancar_APP.bat**

### Con Docker
El proyecto incluye:
- Dockerfile
- docker-compose.yml

para facilitar el despliegue.

---

## Calidad del código

Se ha utilizado SonarQube para analizar la calidad del código, revisar vulnerabilidades y detectar mejoras posibles.

---

## Autor

Mario Carcedo

Proyecto realizado para Sistemas Distribuidos.
