-- ============================================================
--  SISTEMA DE BIBLIOTECA DE VIDEOJUEGOS
--  Script de Base de Datos
--  Ejecutar en MySQL Workbench conectado a Docker
-- ============================================================

-- 1. Crear y usar la base de datos
CREATE DATABASE IF NOT EXISTS db_gamelibrary
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE db_gamelibrary;

-- ============================================================
-- TABLA: categorias (CRUD 1)
-- ============================================================
CREATE TABLE IF NOT EXISTS categorias (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100)  NOT NULL,
    descripcion VARCHAR(255)  NOT NULL,
    activo      TINYINT(1)    NOT NULL DEFAULT 1,
    created_at  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================================
-- TABLA: juegos (CRUD 2)
-- ============================================================
CREATE TABLE IF NOT EXISTS juegos (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    titulo          VARCHAR(150)    NOT NULL,
    desarrollador   VARCHAR(100)    NOT NULL,
    anio_lanzamiento YEAR           NOT NULL,
    plataforma      VARCHAR(80)     NOT NULL,
    precio          DECIMAL(8,2)    NOT NULL,
    disponible      TINYINT(1)      NOT NULL DEFAULT 1,
    categoria_id    INT             NOT NULL,
    created_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_juego_categoria
        FOREIGN KEY (categoria_id) REFERENCES categorias(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- ============================================================
-- DATOS DE EJEMPLO
-- ============================================================

INSERT INTO categorias (nombre, descripcion) VALUES
('Acción',        'Juegos de ritmo rápido con combate y reflejos'),
('RPG',           'Juegos de rol con historia profunda y progresión'),
('Deportes',      'Simulaciones deportivas y competitivas'),
('Estrategia',    'Juegos de planificación y toma de decisiones'),
('Aventura',      'Exploración, puzzles y narrativa'),
('Terror',        'Juegos de suspenso y horror psicológico'),
('Pelea',         'Combate uno a uno o multijugador');

INSERT INTO juegos (titulo, desarrollador, anio_lanzamiento, plataforma, precio, categoria_id) VALUES
('God of War Ragnarök',     'Santa Monica Studio',  2022, 'PlayStation 5',  299.90, 1),
('Elden Ring',              'FromSoftware',         2022, 'PC / PS5 / Xbox', 249.90, 2),
('eFootball 2025',          'Konami',               2024, 'PC / PS4 / PS5',   0.00, 3),
('Civilization VII',        'Firaxis Games',        2025, 'PC',             259.90, 4),
('The Legend of Zelda',     'Nintendo',             2023, 'Nintendo Switch', 229.90, 5),
('Resident Evil 4 Remake',  'Capcom',               2023, 'PC / PS5 / Xbox', 199.90, 6),
('Street Fighter 6',        'Capcom',               2023, 'PC / PS5 / Xbox', 219.90, 7),
('Brawl Stars',             'Supercell',            2018, 'Móvil',            0.00, 1),
('Baldur\'s Gate 3',        'Larian Studios',       2023, 'PC / PS5',        249.90, 2),
('FIFA 24',                 'EA Sports',            2023, 'PC / PS5 / Xbox', 199.90, 3);

-- ============================================================
-- VERIFICACIÓN
-- ============================================================
SELECT 'categorias' AS tabla, COUNT(*) AS registros FROM categorias
UNION ALL
SELECT 'juegos',               COUNT(*)               FROM juegos;
