-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-08-2026 a las 11:58:03
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `horarionet`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `admin`
--

CREATE TABLE `admin` (
  `id_usuario` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Apellido` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `admin`
--

INSERT INTO `admin` (`id_usuario`, `Nombre`, `Apellido`) VALUES
(1, 'PEDRO', 'PEREZ'),
(12, 'ROSA', 'ALVIREZ');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno`
--

CREATE TABLE `alumno` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(80) NOT NULL,
  `apellido` varchar(80) NOT NULL,
  `id_carrera` int(11) DEFAULT NULL,
  `id_grupo` int(11) NOT NULL,
  `id_cuatri` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `alumno`
--

INSERT INTO `alumno` (`id_usuario`, `nombre`, `apellido`, `id_carrera`, `id_grupo`, `id_cuatri`) VALUES
(2, 'LOLA', 'PEREZ', 10, 2, 1),
(6, 'LOLA2', 'GARCIA', 1, 3, 3),
(7, 'DANY', 'NONA', 10, 1, 3),
(13, 'EJEMPLO1', 'GAUTIER', 12, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrera`
--

CREATE TABLE `carrera` (
  `id_carrera` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `carrera`
--

INSERT INTO `carrera` (`id_carrera`, `nombre`) VALUES
(1, 'CINEMATOGRAFÍA'),
(2, 'ASESOR FINANCIERO'),
(3, 'BIOTECNOLOGÍA'),
(4, 'DISEÑO Y MODA INDUSTRIAL'),
(5, 'ENFERMERÍA BILINGÜE'),
(6, 'ELECTROMOVILIDAD'),
(7, 'ECONOMÍA SOCIAL Y SOLIDARIA'),
(8, 'ENERGÍA Y DESARROLLO SOSTENIBLE'),
(9, 'MANTENIMIENTO INDUSTRIAL'),
(10, 'TICS'),
(11, 'GASTRONOMÍA'),
(12, 'MECATRÓNICA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuatri`
--

CREATE TABLE `cuatri` (
  `id_cuatri` int(11) NOT NULL,
  `num_cuatri` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cuatri`
--

INSERT INTO `cuatri` (`id_cuatri`, `num_cuatri`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `docente`
--

CREATE TABLE `docente` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(80) NOT NULL,
  `apellido` varchar(100) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `docente`
--

INSERT INTO `docente` (`id_usuario`, `nombre`, `apellido`) VALUES
(3, 'MTRA GRICELDA', 'RODRIGUEZ'),
(10, 'EZEQUIEL', 'FARIAS'),
(11, 'ROSARIO', 'HERNÁNDEZ');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo`
--

CREATE TABLE `grupo` (
  `id_grupo` int(11) NOT NULL,
  `letra` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `grupo`
--

INSERT INTO `grupo` (`id_grupo`, `letra`) VALUES
(1, 'A'),
(2, 'B'),
(3, 'C');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial`
--

CREATE TABLE `historial` (
  `id_historial` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_grupo` int(11) NOT NULL,
  `id_cuatri` int(11) NOT NULL,
  `id_horario` int(11) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Disparadores `historial`
--
DELIMITER $$
CREATE TRIGGER `tr_historial_fecha` BEFORE INSERT ON `historial` FOR EACH ROW SET NEW.fecha_registro = IFNULL(NEW.fecha_registro, NOW())
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horario`
--

CREATE TABLE `horario` (
  `id_horario` int(11) NOT NULL,
  `id_carrera` int(11) NOT NULL,
  `id_grupo` int(11) NOT NULL,
  `id_cuatri` int(11) NOT NULL,
  `imagen` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `horario`
--

INSERT INTO `horario` (`id_horario`, `id_carrera`, `id_grupo`, `id_cuatri`, `imagen`) VALUES
(1, 2, 1, 1, 'AsesorFinanciero_C1_A.pdf'),
(2, 10, 1, 3, 'TICs_C3_A.png'),
(3, 4, 1, 1, 'DisenoyModaIndustrial_C1_A.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `permiso`
--

CREATE TABLE `permiso` (
  `id_permiso` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `permiso`
--

INSERT INTO `permiso` (`id_permiso`, `nombre`, `descripcion`) VALUES
(1, 'GESTIONAR DOCENTES', 'Alta, consulta, modificación y baja de docentes'),
(2, 'GESTIONAR ALUMNOS', 'Alta, consulta, modificación y baja de alumnos'),
(3, 'GESTIONAR ADMINISTRADORES', 'Alta, consulta, modificación y baja de administradores'),
(4, 'GESTIONAR CARRERAS', 'Alta, consulta, modificación y baja del catálogo de carreras'),
(5, 'GESTIONAR HORARIOS', 'Subir, editar y eliminar horarios por carrera/cuatri/grupo'),
(6, 'CONSULTAR HORARIOS', 'Buscar y visualizar horarios filtrando por carrera, cuatrimestre y grupo'),
(7, 'VER HORARIO PROPIO', 'Consultar el horario correspondiente a la carrera/cuatri/grupo propios'),
(8, 'GESTIONAR PERMISOS', 'Asignar o quitar permisos a cada rol');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `id_rol` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `descripcion` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id_rol`, `nombre`, `descripcion`) VALUES
(1, 'ADMINISTRADOR', 'Control total del sistema'),
(2, 'DOCENTE', 'Consulta su horario y el de sus grupos'),
(3, 'ALUMNO', 'Consulta el horario de su grupo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol_permiso`
--

CREATE TABLE `rol_permiso` (
  `id_rol_permiso` int(11) NOT NULL,
  `id_rol` int(11) NOT NULL,
  `id_permiso` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rol_permiso`
--

INSERT INTO `rol_permiso` (`id_rol_permiso`, `id_rol`, `id_permiso`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 1, 6),
(7, 1, 7),
(8, 1, 8),
(9, 2, 6),
(10, 3, 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL,
  `login` varchar(50) NOT NULL,
  `contrasena` varchar(100) NOT NULL,
  `id_rol` int(11) NOT NULL,
  `ultimo_acceso` datetime DEFAULT NULL,
  `ultima_ip` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `login`, `contrasena`, `id_rol`, `ultimo_acceso`, `ultima_ip`) VALUES
(1, 'ADMIN100', 'admin123', 1, '2026-08-19 09:18:58', '192.168.1.10'),
(2, 'UTM251000PE', 'alumno123', 3, '2026-08-18 14:54:54', '192.168.1.10'),
(3, 'GRIS', 'UTM123', 2, '2026-08-19 02:11:46', '192.168.1.10'),
(6, 'UTM251001PE', 'UTMPASS251000PE', 3, '2026-08-19 02:12:24', '192.168.1.10'),
(7, 'NONAUTM1281989', '12345', 3, NULL, NULL),
(10, 'UTMEZEQUIEL123', '12345', 2, NULL, NULL),
(11, 'UTMROSARIO', 'Chayito123', 2, NULL, NULL),
(12, 'ADMINRESIDENTE100', 'super100', 1, NULL, NULL),
(13, 'UTM123456', 'Ejemplo1', 3, NULL, NULL);

--
-- Disparadores `usuario`
--
DELIMITER $$
CREATE TRIGGER `tr_actualizar_acceso` BEFORE UPDATE ON `usuario` FOR EACH ROW SET NEW.ultimo_acceso = IF(NEW.ultimo_acceso IS NOT NULL, NOW(), NEW.ultimo_acceso)
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `vista_alumnos`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `vista_alumnos` (
`id_usuario` int(11)
,`nombre` varchar(80)
,`apellido` varchar(80)
,`carrera` varchar(100)
,`grupo` varchar(5)
,`cuatrimestre` int(11)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `vista_horarios`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `vista_horarios` (
`id_horario` int(11)
,`carrera` varchar(100)
,`grupo` varchar(5)
,`cuatrimestre` int(11)
,`imagen` varchar(255)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `vista_usuarios_roles`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `vista_usuarios_roles` (
`id_usuario` int(11)
,`login` varchar(50)
,`rol` varchar(30)
,`descripcion` varchar(150)
);

-- --------------------------------------------------------

--
-- Estructura para la vista `vista_alumnos`
--
DROP TABLE IF EXISTS `vista_alumnos`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vista_alumnos`  AS SELECT `a`.`id_usuario` AS `id_usuario`, `a`.`nombre` AS `nombre`, `a`.`apellido` AS `apellido`, `c`.`nombre` AS `carrera`, `g`.`letra` AS `grupo`, `cu`.`num_cuatri` AS `cuatrimestre` FROM (((`alumno` `a` join `carrera` `c` on(`a`.`id_carrera` = `c`.`id_carrera`)) join `grupo` `g` on(`a`.`id_grupo` = `g`.`id_grupo`)) join `cuatri` `cu` on(`a`.`id_cuatri` = `cu`.`id_cuatri`)) ;

-- --------------------------------------------------------

--
-- Estructura para la vista `vista_horarios`
--
DROP TABLE IF EXISTS `vista_horarios`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vista_horarios`  AS SELECT `h`.`id_horario` AS `id_horario`, `c`.`nombre` AS `carrera`, `g`.`letra` AS `grupo`, `cu`.`num_cuatri` AS `cuatrimestre`, `h`.`imagen` AS `imagen` FROM (((`horario` `h` join `carrera` `c` on(`h`.`id_carrera` = `c`.`id_carrera`)) join `grupo` `g` on(`h`.`id_grupo` = `g`.`id_grupo`)) join `cuatri` `cu` on(`h`.`id_cuatri` = `cu`.`id_cuatri`)) ;

-- --------------------------------------------------------

--
-- Estructura para la vista `vista_usuarios_roles`
--
DROP TABLE IF EXISTS `vista_usuarios_roles`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vista_usuarios_roles`  AS SELECT `u`.`id_usuario` AS `id_usuario`, `u`.`login` AS `login`, `r`.`nombre` AS `rol`, `r`.`descripcion` AS `descripcion` FROM (`usuario` `u` join `rol` `r` on(`u`.`id_rol` = `r`.`id_rol`)) ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_usuario`);

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`id_usuario`),
  ADD KEY `id_grupo` (`id_grupo`),
  ADD KEY `id_cuatri` (`id_cuatri`),
  ADD KEY `id_carrera` (`id_carrera`);

--
-- Indices de la tabla `carrera`
--
ALTER TABLE `carrera`
  ADD PRIMARY KEY (`id_carrera`);

--
-- Indices de la tabla `cuatri`
--
ALTER TABLE `cuatri`
  ADD PRIMARY KEY (`id_cuatri`);

--
-- Indices de la tabla `docente`
--
ALTER TABLE `docente`
  ADD PRIMARY KEY (`id_usuario`);

--
-- Indices de la tabla `grupo`
--
ALTER TABLE `grupo`
  ADD PRIMARY KEY (`id_grupo`);

--
-- Indices de la tabla `historial`
--
ALTER TABLE `historial`
  ADD PRIMARY KEY (`id_historial`),
  ADD KEY `id_grupo` (`id_grupo`),
  ADD KEY `id_cuatri` (`id_cuatri`),
  ADD KEY `id_horario` (`id_horario`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `horario`
--
ALTER TABLE `horario`
  ADD PRIMARY KEY (`id_horario`),
  ADD UNIQUE KEY `uq_horario_combo` (`id_carrera`,`id_cuatri`,`id_grupo`),
  ADD KEY `id_grupo` (`id_grupo`),
  ADD KEY `id_cuatri` (`id_cuatri`),
  ADD KEY `id_carrera` (`id_carrera`);

--
-- Indices de la tabla `permiso`
--
ALTER TABLE `permiso`
  ADD PRIMARY KEY (`id_permiso`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id_rol`);

--
-- Indices de la tabla `rol_permiso`
--
ALTER TABLE `rol_permiso`
  ADD PRIMARY KEY (`id_rol_permiso`),
  ADD KEY `id_rol` (`id_rol`),
  ADD KEY `id_permiso` (`id_permiso`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `login` (`login`),
  ADD KEY `id_rol` (`id_rol`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `carrera`
--
ALTER TABLE `carrera`
  MODIFY `id_carrera` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `cuatri`
--
ALTER TABLE `cuatri`
  MODIFY `id_cuatri` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `grupo`
--
ALTER TABLE `grupo`
  MODIFY `id_grupo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `historial`
--
ALTER TABLE `historial`
  MODIFY `id_historial` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `horario`
--
ALTER TABLE `horario`
  MODIFY `id_horario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `permiso`
--
ALTER TABLE `permiso`
  MODIFY `id_permiso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `id_rol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `rol_permiso`
--
ALTER TABLE `rol_permiso`
  MODIFY `id_rol_permiso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD CONSTRAINT `alumno_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`),
  ADD CONSTRAINT `alumno_ibfk_2` FOREIGN KEY (`id_grupo`) REFERENCES `grupo` (`id_grupo`),
  ADD CONSTRAINT `alumno_ibfk_3` FOREIGN KEY (`id_cuatri`) REFERENCES `cuatri` (`id_cuatri`),
  ADD CONSTRAINT `alumno_ibfk_4` FOREIGN KEY (`id_carrera`) REFERENCES `carrera` (`id_carrera`);

--
-- Filtros para la tabla `docente`
--
ALTER TABLE `docente`
  ADD CONSTRAINT `docente_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `historial`
--
ALTER TABLE `historial`
  ADD CONSTRAINT `historial_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `alumno` (`id_usuario`),
  ADD CONSTRAINT `historial_ibfk_2` FOREIGN KEY (`id_grupo`) REFERENCES `grupo` (`id_grupo`),
  ADD CONSTRAINT `historial_ibfk_3` FOREIGN KEY (`id_cuatri`) REFERENCES `cuatri` (`id_cuatri`),
  ADD CONSTRAINT `historial_ibfk_4` FOREIGN KEY (`id_horario`) REFERENCES `horario` (`id_horario`);

--
-- Filtros para la tabla `horario`
--
ALTER TABLE `horario`
  ADD CONSTRAINT `horario_ibfk_1` FOREIGN KEY (`id_grupo`) REFERENCES `grupo` (`id_grupo`),
  ADD CONSTRAINT `horario_ibfk_2` FOREIGN KEY (`id_cuatri`) REFERENCES `cuatri` (`id_cuatri`),
  ADD CONSTRAINT `horario_ibfk_3` FOREIGN KEY (`id_carrera`) REFERENCES `carrera` (`id_carrera`);

--
-- Filtros para la tabla `rol_permiso`
--
ALTER TABLE `rol_permiso`
  ADD CONSTRAINT `rol_permiso_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id_rol`),
  ADD CONSTRAINT `rol_permiso_ibfk_2` FOREIGN KEY (`id_permiso`) REFERENCES `permiso` (`id_permiso`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id_rol`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
