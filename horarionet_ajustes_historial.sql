-- ============================================================
-- HorarioNet — ajustes a la base de datos
-- Fecha: 17/08/2026
--
-- Antes de correr esto: exporta la BD desde phpMyAdmin (Exportar
-- -> Rápido -> Continuar). Es un respaldo de 10 segundos.
--
-- Correr en phpMyAdmin: pestaña SQL con la BD horarionet
-- seleccionada, pegar todo y ejecutar.
-- ============================================================

USE horarionet;


-- ------------------------------------------------------------
-- 1. La FK del historial debe apuntar a alumno, no a usuario
--
-- Motivo: la tabla historial guarda el recorrido académico de un
-- ALUMNO. Con la FK apuntando a usuario, la base de datos dejaría
-- registrar ahí a un docente o a un administrador. Apuntándola a
-- alumno, la BD misma lo impide.
-- ------------------------------------------------------------

ALTER TABLE historial
  DROP FOREIGN KEY historial_ibfk_1;

ALTER TABLE historial
  ADD CONSTRAINT historial_ibfk_1
  FOREIGN KEY (id_usuario) REFERENCES alumno (id_usuario);


-- ------------------------------------------------------------
-- 2. id_horario ahora acepta NULL
--
-- Motivo: cuando el administrador cambia a un alumno de grupo,
-- puede que todavía no exista el horario de esa combinación
-- carrera/cuatri/grupo. Con NOT NULL el registro del movimiento
-- tronaría; con NULL se guarda el cambio y el horario se asocia
-- después.
-- ------------------------------------------------------------

ALTER TABLE historial
  MODIFY id_horario int(11) NULL;


-- ------------------------------------------------------------
-- 3. Se elimina el módulo de materias
--
-- Motivo: se redujo el alcance. El docente consulta horarios; no
-- imparte materias registradas en el sistema. Las tablas materia y
-- docente_grupo_materia quedaron sin uso y sin datos.
--
-- El orden importa: primero la tabla que tiene la FK, luego la
-- referenciada.
--
-- Si prefieren conservarlas por ahora, comenten estas dos líneas
-- (poniéndoles -- al inicio) y borren también MateriaDAO.java y
-- Materia.java del proyecto hasta que decidan.
-- ------------------------------------------------------------

DROP TABLE IF EXISTS docente_grupo_materia;
DROP TABLE IF EXISTS materia;


-- ------------------------------------------------------------
-- 4. OPCIONAL — registrar también la carrera en el historial
--
-- Solo hace falta si quieren que el historial refleje un cambio de
-- carrera, no nada más de grupo/cuatrimestre. El HistorialDAO que
-- tienen ahorita NO usa esta columna, así que agregarla no rompe
-- nada pero tampoco hace nada hasta que la programen.
--
-- Descomenten las dos sentencias si la quieren.
-- ------------------------------------------------------------

-- ALTER TABLE historial
--   ADD COLUMN id_carrera int(11) NULL AFTER id_usuario;

-- ALTER TABLE historial
--   ADD CONSTRAINT historial_ibfk_5
--   FOREIGN KEY (id_carrera) REFERENCES carrera (id_carrera);


-- ============================================================
-- Verificación: corran esto después para confirmar que quedó
-- ============================================================

-- Debe mostrar id_horario con Null = YES
DESCRIBE historial;

-- Debe mostrar historial_ibfk_1 apuntando a la tabla alumno
SELECT
    CONSTRAINT_NAME,
    COLUMN_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM information_schema.KEY_COLUMN_USAGE
WHERE TABLE_SCHEMA = 'horarionet'
  AND TABLE_NAME = 'historial'
  AND REFERENCED_TABLE_NAME IS NOT NULL;
