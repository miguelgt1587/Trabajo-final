package cinemauq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CinemaApp extends Application {
    public static Stage mainStage;
    public static Scene sceneInicio;
    public static Scene sceneOperario;
    public static Scene sceneAdministrador;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;

        ModelFactory.getInstance();

        var inicioUrl= CinemaApp.class.getResource("inicio.fxml");
        var operarioUrl = CinemaApp.class.getResource("operario.fxml");
        var administradorUrl = CinemaApp.class.getResource("administrador.fxml");


        sceneInicio = new Scene(FXMLLoader.load(inicioUrl));
        sceneOperario = new Scene(FXMLLoader.load(operarioUrl));
        sceneAdministrador = new Scene(FXMLLoader.load(administradorUrl));

        goToInicio();
        mainStage.show();
    }

    public static void goToInicio() {
        mainStage.setScene(sceneInicio);
        mainStage.setTitle("Iniciar sesión");
    }

    public static void goToOperario() {
        mainStage.setScene(sceneOperario);
        mainStage.setTitle("Panel de Operario");
    }

    public static void goToAdministrador() {
        mainStage.setScene(sceneAdministrador);
        mainStage.setTitle("Panel de Administrador");
    }
}
