# 🎮 GameLibrary - Sistema de Biblioteca de Videojuegos

## 📌 Nombre del Proyecto
**GameLibrary** — Sistema de Biblioteca de Videojuegos

---

## 👨‍💻 Integrantes del Equipo

- Jorge Luis Vilcapuma Trujillo
- Oscar Heyton Sanchez Arias

---

## 📝 Explicación General del Sistema

GameLibrary es una aplicación de escritorio desarrollada en Java utilizando Programación Orientada a Objetos (POO) y arquitectura MVC.

El sistema permite administrar una biblioteca digital de videojuegos, facilitando el registro, búsqueda, actualización y eliminación de información relacionada con juegos y categorías.

La aplicación cuenta con una interfaz gráfica desarrollada en Java Swing y conexión a una base de datos MySQL para almacenar la información de manera persistente.

---

## ⚙️ CRUDs Desarrollados

### 🎮 CRUD de Juegos
Permite:
- Registrar videojuegos
- Listar videojuegos
- Editar videojuegos
- Eliminar videojuegos

### 🗂️ CRUD de Categorías
Permite:
- Registrar categorías
- Listar categorías
- Editar categorías
- Eliminar categorías

---

## 🛠️ Herramientas y Lenguajes Utilizados

| Herramienta / Lenguaje | Uso |
|------------------------|-----|
| Java | Desarrollo del sistema |
| Java Swing | Interfaz gráfica |
| MySQL | Base de datos |
| Maven | Gestión de dependencias |
| Git y GitHub | Control de versiones |
| Docker | Contenedor MySQL |
| IntelliJ IDEA | Entorno de desarrollo |

---

## 📂 Arquitectura Utilizada

El proyecto utiliza el patrón MVC:

```bash
src/main/java/pe/vallegrande/gamelibrary
│
├── controller
├── model
├── view
└── database