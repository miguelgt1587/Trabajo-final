package cinemauq;

import cinemauq.gui.AdministradorView;
import cinemauq.gui.LoginView;
import cinemauq.gui.OperarioView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Arranca la aplicacion y cambia entre las pantallas de la interfaz.
 */
public class CinemaApp extends Application {

    private static Stage ventanaPrincipal;

    @Override
    public void start(Stage stage) {
        ventanaPrincipal = stage;
        mostrarLogin();
        ventanaPrincipal.show();
    }

    // Muestra la ventana de inicio de sesion
    public static void mostrarLogin() {
        LoginView vistaLogin = new LoginView();
        Scene escena = new Scene(vistaLogin, 340, 280);
        ventanaPrincipal.setScene(escena);
        ventanaPrincipal.setTitle("CINEMAUQ - Iniciar Sesión");
        ventanaPrincipal.centerOnScreen();
    }

    // Muestra la ventana de opciones del operario
    public static void mostrarOperario() {
        OperarioView vistaOperario = new OperarioView();
        Scene escena = new Scene(vistaOperario, 650, 550);
        ventanaPrincipal.setScene(escena);
        ventanaPrincipal.setTitle("CINEMAUQ - Operario");
        ventanaPrincipal.centerOnScreen();
    }

    // Muestra la ventana de opciones del administrador
    public static void mostrarAdministrador() {
        AdministradorView vistaAdmin = new AdministradorView();
        Scene escena = new Scene(vistaAdmin, 650, 600);
        ventanaPrincipal.setScene(escena);
        ventanaPrincipal.setTitle("CINEMAUQ - Administrador");
        ventanaPrincipal.centerOnScreen();
    }
}
