package cinemauq.util;

import cinemauq.model.*;
import java.time.LocalDate;

public class DataUtil {
    public static final String ADMINISTRADOR = "Administrador";
    public static final String ADMINISTRADOR_CONTRASENA = "123";
    public static final String OPERARIO= "Operario";
    public static final String OPERARIO_CONTRASENA = "123";

    public static Cinema iniciarDatos() {
        Cinema cinema = new Cinema();

        // Agregar películas
        cinema.agregarPelicula(new Pelicula("Inception", Genero.CIENCIA_FICCION, 148, LocalDate.of(2010, 7, 16)));
        cinema.agregarPelicula(new Pelicula("The Dark Knight", Genero.ACCION, 152, LocalDate.of(2008, 7, 18)));
        cinema.agregarPelicula(new Pelicula("Interstellar", Genero.CIENCIA_FICCION, 169, LocalDate.of(2014, 11, 7)));

        // Agregar salas
        cinema.agregarSala(new Sala("Sala 1", 100));
        cinema.agregarSala(new Sala("Sala 2", 80));
        cinema.agregarSala(new Sala("Sala 3", 120));

        return cinema;
    }
}
