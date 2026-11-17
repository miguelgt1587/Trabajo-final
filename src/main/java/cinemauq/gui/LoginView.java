package cinemauq.gui;

import cinemauq.CinemaApp;
import cinemauq.util.DataUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Pantalla basica para iniciar sesion en el sistema de cine.
 */
public class LoginView extends VBox {

    private TextField campoUsuario;
    private PasswordField campoContrasenia;
    private Button botonIngresar;

    public LoginView() {
        setSpacing(10);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);
        crearComponentes();
        armarDisenio();
    }

    // Inicializa las cajas de texto y el boton de acceso
    private void crearComponentes() {
        campoUsuario = new TextField();
        campoUsuario.setPromptText("Usuario");
        campoUsuario.setMaxWidth(200);

        campoContrasenia = new PasswordField();
        campoContrasenia.setPromptText("Contraseña");
        campoContrasenia.setMaxWidth(200);

        botonIngresar = new Button("Ingresar");
        botonIngresar.setOnAction(e -> onIngresarClick());
    }

    // Agrega los textos y campos a la ventana
    private void armarDisenio() {
        Label etiquetaTitulo = new Label("CINEMAUQ");
        Label etiquetaUsuario = new Label("Usuario:");
        Label etiquetaContrasenia = new Label("Contraseña:");
        Label etiquetaPista = new Label("Admin: Administrador / 123\nOperario: Operario / 123");

        getChildren().add(etiquetaTitulo);
        getChildren().add(etiquetaUsuario);
        getChildren().add(campoUsuario);
        getChildren().add(etiquetaContrasenia);
        getChildren().add(campoContrasenia);
        getChildren().add(botonIngresar);
        getChildren().add(etiquetaPista);
    }

    // Valida que los campos no esten vacios y revisa las credenciales
    private void onIngresarClick() {
        String usuario = campoUsuario.getText().trim();
        String contrasenia = campoContrasenia.getText().trim();

        if (validarCampos(usuario, contrasenia)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Debe llenar ambos campos.");
            return;
        }

        verificarCredenciales(usuario, contrasenia);
    }

    // Comprueba si los campos estan vacios
    private boolean validarCampos(String usuario, String contrasenia) {
        return usuario.isEmpty() || contrasenia.isEmpty();
    }

    // Compara el usuario y clave con if/else para abrir la ventana correcta
    private void verificarCredenciales(String usuario, String contrasenia) {
        if (DataUtil.ADMINISTRADOR.equalsIgnoreCase(usuario) && DataUtil.ADMINISTRADOR_CONTRASENA.equals(contrasenia)) {
            CinemaApp.mostrarAdministrador();
        } else if (DataUtil.OPERARIO.equalsIgnoreCase(usuario) && DataUtil.OPERARIO_CONTRASENA.equals(contrasenia)) {
            CinemaApp.mostrarOperario();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Usuario o contraseña incorrectos.");
        }
    }

    // Muestra un mensaje emergente de alerta
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
