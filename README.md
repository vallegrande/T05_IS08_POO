# 🎮 GameLibrary — Sistema de Biblioteca de Videojuegos

> **Curso:** IS08 — Programación Orientada a Objetos  
> **Instituto:** I.E.S.T.P. Valle Grande  
> **Ciclo:** 2025

---

## 👥 Integrantes del Equipo

| Nombre | GitHub | CRUD a cargo |
|--------|--------|--------------|
| [Integrante 1] | @usuario1 | Gestión de Juegos |
| [Integrante 2] | @usuario2 | Gestión de Categorías |

---

## 📋 Descripción General

**GameLibrary** es una aplicación de escritorio desarrollada en Java con arquitectura MVC que simula un catálogo personal de videojuegos. Permite gestionar una biblioteca digital organizando los juegos por categorías, registrando información relevante como desarrollador, plataforma, precio y disponibilidad.

---

## 🗂️ CRUDs Desarrollados

### 1. 🏷️ Gestión de Categorías
- **Integrante responsable:** [Nombre]
- Crear, leer, actualizar y eliminar categorías
- Validación de nombres duplicados
- Control de estado activo/inactivo (JCheckBox)
- Restricción de eliminación si tiene juegos asociados

### 2. 🎯 Gestión de Juegos
- **Integrante responsable:** [Nombre]
- Crear, leer, actualizar y eliminar videojuegos
- Relación con categorías via JComboBox
- Validación de año (1970–2030), precio numérico y campos obligatorios
- Estado de disponibilidad en catálogo (JCheckBox)

---

## 🛠️ Herramientas y Tecnologías

| Categoría | Tecnología |
|-----------|-----------|
| Lenguaje | Java 17 |
| UI Framework | Java Swing (JFrame, JPanel, JLabel, JTextField, JCheckBox, JComboBox, JTable, JOptionPane) |
| Arquitectura | MVC (Model – View – Controller) |
| Gestor de dependencias | Apache Maven |
| Base de datos | MySQL 8.x |
| Contenedor BD | Docker |
| IDE | IntelliJ IDEA |
| Control de versiones | Git + GitHub |
| Flujo de trabajo | GitFlow + Commits Convencionales |

---

## 🗄️ Estructura del Proyecto

```
src/
└── main/
    └── java/
        └── pe/vallegrande/gamelibrary/
            ├── model/
            │   ├── Categoria.java
            │   └── Juego.java
            ├── view/
            │   ├── PantallaBienvenida.java   ← Punto de entrada
            │   ├── VentanaCategoria.java
            │   └── VentanaJuego.java
            ├── controller/
            │   ├── CategoriaController.java
            │   └── JuegoController.java
            └── database/
                └── Conexion.java
```

---

## 🐳 Configuración de Base de Datos con Docker

### Paso 1 — Levantar contenedor MySQL

```bash
docker run -d \
  --name mysql_gamelibrary \
  -e MYSQL_ROOT_PASSWORD=root123 \
  -p 3306:3306 \
  mysql:8.0
```

### Paso 2 — Conectar desde MySQL Workbench

| Campo | Valor |
|-------|-------|
| Hostname | `localhost` |
| Port | `3306` |
| Username | `root` |
| Password | `root123` |

### Paso 3 — Ejecutar el script

Abre `script_bd.sql` en MySQL Workbench y ejecuta con **Ctrl+Shift+Enter**.

---

## ▶️ Instrucciones para Ejecutar

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/[usuario]/##_IS08_POO.git
   cd ##_IS08_POO
   ```

2. Asegurarse de que Docker esté corriendo con el contenedor `mysql_gamelibrary`

3. Verificar la conexión en `Conexion.java` (host, puerto, usuario, clave)

4. Abrir en IntelliJ IDEA → Maven → Reload

5. Ejecutar `PantallaBienvenida.java` con el botón ▶️ o:
   ```bash
   mvn clean package
   java -jar target/gamelibrary-1.0-SNAPSHOT.jar
   ```

---

## 🌿 GitFlow y Commits Convencionales

### Ramas
```
main         → versión estable final
develop      → integración continua
feature/crud-juegos       → CRUD de Juegos
feature/crud-categorias   → CRUD de Categorías
feature/pantalla-bienvenida
feature/conexion-bd
```

### Tipos de commits
```
feat:     nueva funcionalidad
fix:      corrección de errores
refactor: reestructuración de código
docs:     cambios en documentación
chore:    configuración, dependencias
```

### Ejemplos
```
feat(juegos): agregar CRUD completo con validaciones
feat(categorias): implementar listado con JTable
fix(conexion): corregir parámetros de conexión Docker
refactor(mvc): separar lógica en controller y model
docs(readme): agregar instrucciones de ejecución
```
