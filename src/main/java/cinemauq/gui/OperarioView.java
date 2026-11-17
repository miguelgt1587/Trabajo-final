package cinemauq.gui;

import cinemauq.CinemaApp;
import cinemauq.enums.EstadoFuncion;
import cinemauq.enums.TipoCliente;
import cinemauq.model.Asiento;
import cinemauq.model.Boleta;
import cinemauq.model.Cine;
import cinemauq.model.Cliente;
import cinemauq.model.DisponibilidadFuncion;
import cinemauq.model.Funcion;
import cinemauq.model.Venta;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Pantalla para el operario del cinema.
 * Permite vender entradas, cancelar ventas y consultar boletas, peliculas, funciones y disponibilidad.
 */
public class OperarioView extends BorderPane {

    private Cine cine;
    private static int contadorVentas = 1;

    // Componentes para la venta
    private ComboBox<String> comboFuncionesVenta;
    private TextField campoDocumentoCliente;
    private TextField campoNombreCliente;
    private ComboBox<TipoCliente> comboTipoCliente;
    private TextField campoCodigoAsiento;
    private TextArea areaTextoAsientos;

    // Componentes para cancelar venta
    private TextField campoCodigoVentaCancelar;
    private TextArea areaTextoVentas;

    // Componentes para boletas
    private TextField campoCodigoBoletaBuscar;
    private TextArea areaTextoBoletas;
    private Label etiquetaTotalBoletasEmitidas;

    // Componentes para consultas
    private TextArea areaTextoPeliculas;
    private TextArea areaTextoFunciones;
    private ComboBox<String> comboFuncionesDisponibilidad;
    private TextArea areaTextoDisponibilidad;

    public OperarioView() {
        this.cine = Datos.getCine();
        setTop(crearBarraSuperior());
        setCenter(crearPanelPestanias());
    }

    // Crea la barra de arriba con el titulo y el boton para salir
    private HBox crearBarraSuperior() {
        HBox barra = new HBox(10);
        barra.setPadding(new Insets(10));
        Label titulo = new Label("CINEMAUQ - Panel de Operario");
        Button botonCerrarSesion = new Button("Cerrar Sesión");
        botonCerrarSesion.setOnAction(e -> onCerrarSesionClick());
        barra.getChildren().addAll(titulo, botonCerrarSesion);
        return barra;
    }

    // Crea el contenedor con las 6 pestanias de trabajo
    private TabPane crearPanelPestanias() {
        TabPane pestanias = new TabPane();
        pestanias.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        pestanias.getTabs().add(new Tab("Vender Entrada", crearPestaniaVenta()));
        pestanias.getTabs().add(new Tab("Cancelar Venta", crearPestaniaCancelarVenta()));
        pestanias.getTabs().add(new Tab("Boletas", crearPestaniaBoletas()));
        pestanias.getTabs().add(new Tab("Películas", crearPestaniaPeliculas()));
        pestanias.getTabs().add(new Tab("Funciones", crearPestaniaFunciones()));
        pestanias.getTabs().add(new Tab("Disponibilidad", crearPestaniaDisponibilidad()));
        return pestanias;
    }

    // ==========================================
    // PESTAÑA 1: VENDER ENTRADA
    // ==========================================

    // Crea la pestania donde se registran las ventas de entradas
    private VBox crearPestaniaVenta() {
        VBox caja = new VBox(8);
        caja.setPadding(new Insets(10));

        comboFuncionesVenta = new ComboBox<>();
        llenarComboFuncionesVenta();

        Button botonVerAsientos = new Button("Ver Asientos de la Función");
        botonVerAsientos.setOnAction(e -> onVerAsientosClick());

        areaTextoAsientos = new TextArea();
        areaTextoAsientos.setEditable(false);
        areaTextoAsientos.setPrefHeight(120);

        caja.getChildren().addAll(new Label("Seleccione Función:"), comboFuncionesVenta, botonVerAsientos, areaTextoAsientos);
        caja.getChildren().addAll(crearCamposFormularioVenta());
        return caja;
    }

    // Crea los campos de texto para cliente y asiento
    private VBox crearCamposFormularioVenta() {
        VBox formulario = new VBox(6);
        campoDocumentoCliente = new TextField();
        campoNombreCliente = new TextField();
        comboTipoCliente = new ComboBox<>(FXCollections.observableArrayList(TipoCliente.values()));
        comboTipoCliente.getSelectionModel().select(TipoCliente.GENERAL);
        campoCodigoAsiento = new TextField();

        Button botonVender = new Button("Realizar Venta");
        botonVender.setOnAction(e -> onVenderClick());

        formulario.getChildren().addAll(
                new Label("Documento Cliente:"), campoDocumentoCliente,
                new Label("Nombre Cliente:"), campoNombreCliente,
                new Label("Tipo Cliente:"), comboTipoCliente,
                new Label("Código Numérico del Asiento:"), campoCodigoAsiento,
                botonVender
        );
        return formulario;
    }

    // Llena el ComboBox de ventas con las funciones en estado PROGRAMADA
    private void llenarComboFuncionesVenta() {
        ArrayList<String> elementos = new ArrayList<>();
        for (Funcion funcion : cine.consultarFunciones()) {
            if (funcion.getEstadoFuncion() == EstadoFuncion.PROGRAMADA) {
                elementos.add(funcion.getCodigo() + " - " + funcion.getPelicula().getNombre() + " (" + funcion.getSala().getNombre() + ")");
            }
        }
        comboFuncionesVenta.setItems(FXCollections.observableArrayList(elementos));
        if (!elementos.isEmpty()) {
            comboFuncionesVenta.getSelectionModel().selectFirst();
        }
    }

    // Muestra en el TextArea el estado de cada asiento de la funcion
    private void onVerAsientosClick() {
        Funcion funcion = buscarFuncionSeleccionada(comboFuncionesVenta.getValue());
        if (funcion == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Debe seleccionar una función.");
            return;
        }

        StringBuilder texto = new StringBuilder();
        for (Asiento asiento : funcion.getSala().getAsientos()) {
            String letra = convertirFilaALetra(asiento.getFila());
            texto.append(letra).append(asiento.getNumero())
                    .append(" (Código ").append(asiento.getCodigo()).append(") - ")
                    .append(asiento.getEstadoAsiento()).append("\n");
        }
        areaTextoAsientos.setText(texto.toString());
    }

    // Convierte el numero de fila en letra: 1 es A, 2 es B, etc.
    private String convertirFilaALetra(int fila) {
        char letra = (char) ('A' + fila - 1);
        return String.valueOf(letra);
    }

    // Busca un objeto Funcion en el cine a partir del texto del ComboBox
    private Funcion buscarFuncionSeleccionada(String textoCombo) {
        if (textoCombo == null) {
            return null;
        }
        for (Funcion funcion : cine.consultarFunciones()) {
            String etiqueta = funcion.getCodigo() + " - " + funcion.getPelicula().getNombre() + " (" + funcion.getSala().getNombre() + ")";
            if (etiqueta.equals(textoCombo)) {
                return funcion;
            }
        }
        return null;
    }

    // Valida los datos y registra la venta llamando a la clase Cine
    private void onVenderClick() {
        Funcion funcion = buscarFuncionSeleccionada(comboFuncionesVenta.getValue());
        String documento = campoDocumentoCliente.getText().trim();
        String nombre = campoNombreCliente.getText().trim();
        String textoCodigoAsiento = campoCodigoAsiento.getText().trim();

        if (funcion == null || documento.isEmpty() || nombre.isEmpty() || textoCodigoAsiento.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Llene todos los datos de la venta.");
            return;
        }

        procesarVenta(funcion, documento, nombre, textoCodigoAsiento);
    }

    // Convierte el codigo del asiento y llama al modelo dentro de un try/catch
    private void procesarVenta(Funcion funcion, String doc, String nombre, String textoAsiento) {
        try {
            int codigoAsiento = Integer.parseInt(textoAsiento);
            String codigoVenta = "V" + contadorVentas;
            Cliente cliente = new Cliente(doc, nombre, comboTipoCliente.getValue());
            cine.registrarCliente(cliente);

            Venta venta = cine.registrarVenta(codigoVenta, cliente, funcion.getCodigo(), codigoAsiento);
            contadorVentas++;

            mostrarAlertaBoletasDeVenta(venta);
            campoCodigoAsiento.clear();
            onVerAsientosClick();
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "El código del asiento debe ser un número.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error en Venta", e.getMessage());
        }
    }

    // Muestra en un Alert informativo las boletas emitidas con los getters
    private void mostrarAlertaBoletasDeVenta(Venta venta) {
        StringBuilder texto = new StringBuilder();
        for (Boleta boleta : venta.getBoletas()) {
            texto.append(textoDeLaBoleta(boleta)).append("\n\n");
        }
        mostrarAlerta(Alert.AlertType.INFORMATION, "Entrada Emitida con Éxito", texto.toString());
    }

    // Devuelve los datos de una boleta organizados linea por linea con los getters
    private String textoDeLaBoleta(Boleta boleta) {
        String asientoTexto = convertirFilaALetra(boleta.getAsiento().getFila()) + boleta.getAsiento().getNumero();
        return "Código de boleta: " + boleta.getCodigoBoleta() + "\n"
                + "Código de venta: " + boleta.getVenta().getCodigo() + "\n"
                + "Cliente: " + boleta.getCliente().getNombre() + " (" + boleta.getCliente().getDocumento() + ")\n"
                + "Película: " + boleta.getFuncion().getPelicula().getNombre() + "\n"
                + "Sala: " + boleta.getFuncion().getSala().getNombre() + "\n"
                + "Fecha y hora de la función: " + boleta.getFuncion().getFecha() + " " + boleta.getFuncion().getHora() + "\n"
                + "Asiento: " + asientoTexto + "\n"
                + "Valor pagado: $" + boleta.getVenta().getValorPagado() + "\n"
                + "Fecha y hora de emisión: " + boleta.getFechaEmision() + " " + boleta.getHoraEmision();
    }

    // ==========================================
    // PESTAÑA 2: CANCELAR VENTA
    // ==========================================

    // Crea la pestania para cancelar una venta y ver la lista
    private VBox crearPestaniaCancelarVenta() {
        VBox caja = new VBox(8);
        caja.setPadding(new Insets(10));

        campoCodigoVentaCancelar = new TextField();
        Button botonCancelar = new Button("Cancelar Venta");
        botonCancelar.setOnAction(e -> onCancelarVentaClick());

        Button botonVerVentas = new Button("Ver Todas las Ventas");
        botonVerVentas.setOnAction(e -> onVerVentasClick());

        areaTextoVentas = new TextArea();
        areaTextoVentas.setEditable(false);
        areaTextoVentas.setPrefHeight(200);

        caja.getChildren().addAll(
                new Label("Código de la Venta a Cancelar:"), campoCodigoVentaCancelar,
                botonCancelar, botonVerVentas, new Label("Listado de Ventas:"), areaTextoVentas
        );
        return caja;
    }

    // Cancela la venta en el cine usando try/catch y aclarando que queda CANCELADA y asiento libre
    private void onCancelarVentaClick() {
        String codigo = campoCodigoVentaCancelar.getText().trim();
        if (codigo.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Ingrese el código de la venta.");
            return;
        }
        try {
            cine.cancelarVenta(codigo);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                    "La venta " + codigo + " quedó CANCELADA y el asiento fue liberado.");
            campoCodigoVentaCancelar.clear();
            onVerVentasClick();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    // Muestra todas las ventas registradas con un ciclo for en el TextArea
    private void onVerVentasClick() {
        StringBuilder texto = new StringBuilder();
        for (Venta venta : cine.consultarVentas()) {
            texto.append("Código: ").append(venta.getCodigo())
                    .append(" | Cliente: ").append(venta.getCliente().getNombre())
                    .append(" | Función: ").append(venta.getFuncion().getCodigo())
                    .append(" | Pagado: $").append(venta.getValorPagado())
                    .append(" | Estado: ").append(venta.getEstadoVenta()).append("\n");
        }
        areaTextoVentas.setText(texto.toString());
    }

    // ==========================================
    // PESTAÑA 3: BOLETAS
    // ==========================================

    // Crea la pestania para consultar y buscar boletas emitidas
    private VBox crearPestaniaBoletas() {
        VBox caja = new VBox(8);
        caja.setPadding(new Insets(10));

        Button botonVerTodas = new Button("Ver todas las boletas");
        botonVerTodas.setOnAction(e -> onVerTodasLasBoletasClick());

        HBox barraBusqueda = crearBarraBusquedaBoleta();
        etiquetaTotalBoletasEmitidas = new Label("Total boletas emitidas: 0");

        areaTextoBoletas = new TextArea();
        areaTextoBoletas.setEditable(false);
        areaTextoBoletas.setPrefHeight(200);

        caja.getChildren().addAll(botonVerTodas, barraBusqueda, etiquetaTotalBoletasEmitidas, areaTextoBoletas);
        return caja;
    }

    // Crea la barra de busqueda por codigo de boleta
    private HBox crearBarraBusquedaBoleta() {
        HBox barra = new HBox(8);
        campoCodigoBoletaBuscar = new TextField();
        campoCodigoBoletaBuscar.setPromptText("Ej: BOL-000001");
        Button botonBuscar = new Button("Buscar boleta");
        botonBuscar.setOnAction(e -> onBuscarBoletaClick());
        barra.getChildren().addAll(new Label("Código Boleta:"), campoCodigoBoletaBuscar, botonBuscar);
        return barra;
    }

    // Muestra todas las boletas emitidas en el TextArea
    private void onVerTodasLasBoletasClick() {
        StringBuilder texto = new StringBuilder();
        for (Boleta boleta : cine.consultarBoletas()) {
            texto.append(textoDeLaBoleta(boleta)).append("\n----------------------------------------\n");
        }
        areaTextoBoletas.setText(texto.toString());
        int total = cine.reporteTotalBoletasEmitidas();
        etiquetaTotalBoletasEmitidas.setText("Total boletas emitidas: " + total);
    }

    // Busca una boleta por codigo usando Optional con isPresent()
    private void onBuscarBoletaClick() {
        String codigo = campoCodigoBoletaBuscar.getText().trim();
        if (codigo.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Ingrese el código de la boleta.");
            return;
        }
        Optional<Boleta> opcion = cine.buscarBoletaPorCodigo(codigo);
        if (opcion.isPresent()) {
            Boleta boleta = opcion.get();
            areaTextoBoletas.setText(textoDeLaBoleta(boleta));
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No existe esa boleta.");
        }
    }

    // ==========================================
    // PESTAÑAS 4, 5 Y 6: CONSULTAS
    // ==========================================

    // Crea la pestania para listar peliculas en un TextArea
    private VBox crearPestaniaPeliculas() {
        VBox caja = new VBox(8);
        caja.setPadding(new Insets(10));
        Button botonListar = new Button("Listar Películas");
        botonListar.setOnAction(e -> onListarPeliculasClick());
        areaTextoPeliculas = new TextArea();
        areaTextoPeliculas.setEditable(false);
        areaTextoPeliculas.setPrefHeight(250);
        caja.getChildren().addAll(botonListar, areaTextoPeliculas);
        return caja;
    }

    // Llena el TextArea de peliculas con un ciclo for
    private void onListarPeliculasClick() {
        StringBuilder texto = new StringBuilder();
        for (cinemauq.model.Pelicula pelicula : cine.consultarPeliculas()) {
            texto.append("Código: ").append(pelicula.getCodigo())
                    .append(" | Nombre: ").append(pelicula.getNombre())
                    .append(" | Género: ").append(pelicula.getGenero())
                    .append(" | Duración: ").append(pelicula.getDuracionMinutos()).append(" min")
                    .append(" | Clasificación: ").append(pelicula.getClasificacion()).append("\n");
        }
        areaTextoPeliculas.setText(texto.toString());
    }

    // Crea la pestania para consultar funciones
    private VBox crearPestaniaFunciones() {
        VBox caja = new VBox(8);
        caja.setPadding(new Insets(10));
        Button botonListar = new Button("Listar Funciones");
        botonListar.setOnAction(e -> onListarFuncionesClick());
        areaTextoFunciones = new TextArea();
        areaTextoFunciones.setEditable(false);
        areaTextoFunciones.setPrefHeight(250);
        caja.getChildren().addAll(botonListar, areaTextoFunciones);
        return caja;
    }

    // Llena el TextArea con las funciones y los asientos disponibles
    private void onListarFuncionesClick() {
        StringBuilder texto = new StringBuilder();
        for (Funcion funcion : cine.consultarFunciones()) {
            int disponibles = contarAsientosDisponibles(funcion.getCodigo());
            texto.append("Código: ").append(funcion.getCodigo())
                    .append(" | Película: ").append(funcion.getPelicula().getNombre())
                    .append(" | Sala: ").append(funcion.getSala().getNombre())
                    .append(" | Fecha: ").append(funcion.getFecha())
                    .append(" | Hora: ").append(funcion.getHora())
                    .append(" | Valor: $").append(funcion.getPrecioBase())
                    .append(" | Asientos Libres: ").append(disponibles).append("\n");
        }
        areaTextoFunciones.setText(texto.toString());
    }

    // Consulta los asientos disponibles de una funcion
    private int contarAsientosDisponibles(int codigoFuncion) {
        try {
            return cine.consultarDisponibilidadFuncion(codigoFuncion).getAsientosDisponibles();
        } catch (Exception e) {
            return 0;
        }
    }

    // Crea la pestania para consultar disponibilidad de una funcion
    private VBox crearPestaniaDisponibilidad() {
        VBox caja = new VBox(8);
        caja.setPadding(new Insets(10));
        comboFuncionesDisponibilidad = new ComboBox<>();
        llenarComboDisponibilidad();
        Button botonConsultar = new Button("Consultar Disponibilidad");
        botonConsultar.setOnAction(e -> onConsultarDisponibilidadClick());
        areaTextoDisponibilidad = new TextArea();
        areaTextoDisponibilidad.setEditable(false);
        areaTextoDisponibilidad.setPrefHeight(220);
        caja.getChildren().addAll(new Label("Función:"), comboFuncionesDisponibilidad, botonConsultar, areaTextoDisponibilidad);
        return caja;
    }

    // Llena el combo de funciones para la consulta de disponibilidad
    private void llenarComboDisponibilidad() {
        ArrayList<String> elementos = new ArrayList<>();
        for (Funcion funcion : cine.consultarFunciones()) {
            elementos.add(funcion.getCodigo() + " - " + funcion.getPelicula().getNombre() + " (" + funcion.getSala().getNombre() + ")");
        }
        comboFuncionesDisponibilidad.setItems(FXCollections.observableArrayList(elementos));
        if (!elementos.isEmpty()) {
            comboFuncionesDisponibilidad.getSelectionModel().selectFirst();
        }
    }

    // Muestra el total, disponibles, ocupados y el listado de asientos en el TextArea
    private void onConsultarDisponibilidadClick() {
        Funcion funcion = buscarFuncionSeleccionada(comboFuncionesDisponibilidad.getValue());
        if (funcion == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione una función.");
            return;
        }
        try {
            DisponibilidadFuncion datos = cine.consultarDisponibilidadFuncion(funcion.getCodigo());
            StringBuilder texto = new StringBuilder();
            texto.append("Total Asientos: ").append(datos.getTotalAsientos()).append("\n");
            texto.append("Disponibles: ").append(datos.getAsientosDisponibles()).append("\n");
            texto.append("Ocupados: ").append(datos.getAsientosOcupados()).append("\n\n");
            texto.append("--- Asientos ---\n");
            for (Asiento asiento : funcion.getSala().getAsientos()) {
                String letra = convertirFilaALetra(asiento.getFila());
                texto.append(letra).append(asiento.getNumero()).append(" - ").append(asiento.getEstadoAsiento()).append("\n");
            }
            areaTextoDisponibilidad.setText(texto.toString());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    // Vuelve a la pantalla de login al cerrar sesion
    private void onCerrarSesionClick() {
        CinemaApp.mostrarLogin();
    }

    // Muestra un mensaje emergente
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
