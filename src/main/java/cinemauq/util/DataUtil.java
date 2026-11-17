package cinemauq.util;

import cinemauq.enums.*;
import cinemauq.model.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class DataUtil {
    public static final String ADMINISTRADOR = "Administrador";
    public static final String ADMINISTRADOR_CONTRASENA = "123";
    public static final String OPERARIO = "Operario";
    public static final String OPERARIO_CONTRASENA = "123";

    public static Cine iniciarDatos() {
        Cine cine = new Cine("CINEMAUQ", 123, 606123456, "Armenia");

        try {
            // 3 Películas de ejemplo
            Pelicula p1 = new Pelicula(1, "Inception", Genero.CIENCIA_FICCION, 148, Clasificacion.MAYORES_12, EstadoPelicula.DISPONIBLE);
            Pelicula p2 = new Pelicula(2, "The Dark Knight", Genero.ACCION, 152, Clasificacion.MAYORES_15, EstadoPelicula.DISPONIBLE);
            Pelicula p3 = new Pelicula(3, "Interstellar", Genero.CIENCIA_FICCION, 169, Clasificacion.TODOS, EstadoPelicula.DISPONIBLE);

            cine.registrarPelicula(p1);
            cine.registrarPelicula(p2);
            cine.registrarPelicula(p3);

            // 3 Salas de ejemplo (capacidad 30 para tener 3 filas de 10)
            Sala s1 = new Sala(1, "Sala 1 (2D)", 30);
            Sala s2 = new Sala(2, "Sala 2 (3D)", 30);
            Sala s3 = new Sala(3, "Sala 3 (IMAX)", 20);

            cine.registrarSala(s1);
            cine.registrarSala(s2);
            cine.registrarSala(s3);

            // 3 Funciones de ejemplo
            LocalDate hoy = LocalDate.now();
            Funcion f1 = new Funcion(101, hoy, LocalTime.of(15, 0), 12000.0, EstadoFuncion.PROGRAMADA, p1, s1);
            Funcion f2 = new Funcion(102, hoy, LocalTime.of(18, 30), 15000.0, EstadoFuncion.PROGRAMADA, p2, s2);
            Funcion f3 = new Funcion(103, hoy.plusDays(1), LocalTime.of(20, 0), 18000.0, EstadoFuncion.PROGRAMADA, p3, s3);

            cine.programarFuncion(f1);
            cine.programarFuncion(f2);
            cine.programarFuncion(f3);

        } catch (Exception e) {
            System.err.println("Error inicializando datos de ejemplo: " + e.getMessage());
        }

        return cine;
    }
}
