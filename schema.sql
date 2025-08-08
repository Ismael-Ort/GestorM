-- Tabla principal de contenidos (series/animes/películas)
CREATE TABLE shows (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    title       TEXT NOT NULL,
    type        TEXT NOT NULL CHECK(type IN ('serie', 'anime', 'pelicula')),
    status      TEXT NOT NULL CHECK(status IN ('viendo', 'visto', 'favorito', 'pendiente')),
    description TEXT
);

-- Episodios para series/animes
CREATE TABLE episodes (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    show_id     INTEGER NOT NULL,
    title       TEXT NOT NULL,
    episode_no  INTEGER NOT NULL,
    duration    INTEGER,
    FOREIGN KEY (show_id) REFERENCES shows(id) ON DELETE CASCADE
);

-- Partes para películas
CREATE TABLE movie_parts (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    show_id     INTEGER NOT NULL,
    title       TEXT NOT NULL,
    part_no     INTEGER NOT NULL,
    duration    INTEGER,
    FOREIGN KEY (show_id) REFERENCES shows(id) ON DELETE CASCADE
);
