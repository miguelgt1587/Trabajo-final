package cinemauq.gui;

import cinemauq.model.Cine;
import cinemauq.util.DataUtil;

/**
 * Guarda una sola copia del cine para toda la interfaz grafica.
 */
public class Datos {

    private static Cine cine;

    // Crea el cine si no existe y lo devuelve
    public static Cine getCine() {
        if (cine == null) {
            cine = DataUtil.iniciarDatos();
        }
        return cine;
    }
}
