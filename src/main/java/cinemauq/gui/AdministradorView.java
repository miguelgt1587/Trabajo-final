package cinemauq.gui;

import cinemauq.CinemaApp;
import cinemauq.enums.Clasificacion;
import cinemauq.enums.EstadoPelicula;
import cinemauq.enums.EstadoSala;
import cinemauq.enums.Genero;
import cinemauq.enums.TipoCliente;
import cinemauq.excepciones.SalaNoDisponibleException;
import cinemauq.model.Cine;
import cinemauq.model.Funcion;
import cinemauq.model.OcupacionFuncion;
import cinemauq.model.Pelicula;
import cinemauq.model.Sala;
import cinemauq.model.Tarifa;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Pantalla principal del Administrador.
 * Permite gestionar peliculas, salas, funciones, tarifas y consultar reportes.
 */
public class AdministradorView extends BorderPane {

    private Cine cine;

    // Campos de peliculas
    private TextField campoCodigoPelicula;
    private TextField campoNombrePelicula;
    private ComboBox<Genero> comboGeneroPelicula;
    private TextField campoDuracionPelicula;
    private ComboBox<Clasificacion> comboClasificacionPelicula;
    private ComboBox<EstadoPelicula> comboEstadoPelicula;
    private TextArea areaTextoPeliculas;

    // Campos de salas
    private TextField campoCodigoSala;
    private TextField campoNombreSala;
    private TextField campoCapacidadSala;
    private ComboBox<EstadoSala> comboEstadoSala;
    private TextArea areaTextoSalas;

    // Campos de funciones
    private TextField campoCodigoFuncion;
    private ComboBox<String> comboPeliculaFuncion;
    private ComboBox<String> comboSalaFuncion;
    private DatePicker selectorFechaFuncion;
    private TextField campoHoraFuncion;
    private TextField campoMinutoFuncion;
    private TextField campoPrecioBaseFuncion;
    private TextArea areaTextoFunciones;

    // Campos de tarifas
    private ComboBox<TipoCliente> comboTipoClienteTarifa;
    private TextField campoPorcentajeTarifa;
    private TextArea areaTextoTarifas;

    // Campos de reportes
    private ComboBox<String> comboFuncionReporteOcupacion;
    private DatePicker selectorFechaReporte;
    private TextArea areaTextoReportes;

    public AdministradorView() {
        this.cine = Datos.getCine();
        setTop(crearBarraSuperior());
        setCenter(crearPanelPestanias());
    }

    // Barra superior con titulo y salida
    private HBox crearBarraSuperior() {
        HBox barra = new HBox(10);
        barra.setPadding(new Insets(10));
        Label titulo = new Label("CINEMAUQ - Panel de Administrador");
        Button botonCerrar = new Button("Cerrar Sesión");
        botonCerrar.setOnAction(e -> onCerrarSesionClick());
        barra.getChildren().addAll(titulo, botonCerrar);
        return barra;
    }

    // Crea el TabPane con las 5 secciones de administracion
    private TabPane crearPanelPestanias() {
        TabPane panel = new TabPane();
        panel.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        panel.getTabs().add(new Tab("Películas", crearPestaniaPeliculas()));
        panel.getTabs().add(new Tab("Salas", crearPestaniaSalas()));
        panel.getTabs().add(new Tab("Funciones", crearPestaniaFunciones()));
        panel.getTabs().add(new Tab("Tarifas", crearPestaniaTarifas()));
        panel.getTabs().add(new Tab("Reportes", crearPestaniaReportes()));
        return panel;
    }

    // ==========================================
    // PESTAÑA 1: PELÍCULAS
    // ==========================================

    // Crea la pestania para administrar peliculas
    private VBox crearPestaniaPeliculas() {
        VBox caja = new VBox(6);
        caja.setPadding(new Insets(10));
        campoCodigoPelicula = new TextField();
        campoNombrePelicula = new TextField();
        comboGeneroPelicula = new ComboBox<>(FXCollections.observableArrayList(Genero.values()));
        comboGeneroPelicula.getSelectionModel().select(Genero.ACCION);
        campoDuracionPelicula = new TextField();
        comboClasificacionPelicula = new ComboBox<>(FXCollections.observableArrayList(Clasificacion.values()));
        comboClasificacionPelicula.getSelectionModel().select(Clasificacion.TODOS);
        comboEstadoPelicula = new ComboBox<>(FXCollections.observableArrayList(EstadoPelicula.values()));
        comboEstadoPelicula.getSelectionModel().select(EstadoPelicula.DISPONIBLE);

        caja.getChildren().addAll(
                new Label("Código:"), campoCodigoPelicula,
                new Label("Nombre:"), campoNombrePelicula,
                new Label("Género:"), comboGeneroPelicula,
                new Label("Duración (minutos):"), campoDuracionPelicula,
                new Label("Clasificación:"), comboClasificacionPelicula,
                new Label("Estado:"), comboEstadoPelicula
        );
        caja.getChildren().addAll(crearBotonesPeliculas());
        return caja;
    }

    // Agrega los botones y el area de texto de peliculas
    private VBox crearBotonesPeliculas() {
        VBox caja = new VBox(6);
        Button botonRegistrar = new Button("Registrar Película");
        botonRegistrar.setOnAction(e -> onRegistrarPeliculaClick());
        Button botonModificar = new Button("Modificar Película");
        botonModificar.setOnAction(e -> onModificarPeliculaClick());
        Button botonDeshabilitar = new Button("Deshabilitar Película");
        botonDeshabilitar.setOnAction(e -> onDeshabilitarPeliculaClick());
        Button botonListar = new Button("Listar Películas");
        botonListar.setOnAction(e -> onListarPeliculasClick());

        areaTextoPeliculas = new TextArea();
        areaTextoPeliculas.setEditable(false);
        areaTextoPeliculas.setPrefHeight(150);

        caja.getChildren().addAll(botonRegistrar, botonModificar, botonDeshabilitar, botonListar, areaTextoPeliculas);
        return caja;
    }

    // Registra una pelicula en el cine
    private void onRegistrarPeliculaClick() {
        try {
            int codigo = Integer.parseInt(campoCodigoPelicula.getText().trim());
            String nombre = campoNombrePelicula.getText().trim();
            int duracion = Integer.parseInt(campoDuracionPelicula.getText().trim());
            if (nombre.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Ingrese el nombre de la película.");
                return;
            }
            Pelicula nueva = new Pelicula(codigo, nombre, comboGeneroPelicula.getValue(), duracion,
                    comboClasificacionPelicula.getValue(), comboEstadoPelicula.getValue());
            cine.registrarPelicula(nueva);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Película registrada.");
            onListarPeliculasClick();
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Código y duración deben ser enteros.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    // Modifica una pelicula existente en el cine
    private void onModificarPeliculaClick() {
        try {
            int codigo = Integer.parseInt(campoCodigoPelicula.getText().trim());
            String nombre = campoNombrePelicula.getText().trim();
            int duracion = Integer.parseInt(campoDuracionPelicula.getText().trim());
            Pelicula datos = new Pelicula(codigo, nombre, comboGeneroPelicula.getValue(), duracion,
                    comboClasificacionPelicula.getValue(), comboEstadoPelicula.getValue());
            cine.modificarPelicula(codigo, datos);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Película modificada.");
            onListarPeliculasClick();
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Código y duración deben ser números.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    // Deshabilita una pelicula marcandola como NO_DISPONIBLE
    private void onDeshabilitarPeliculaClick() {
        try {
            int codigo = Integer.parseInt(campoCodigoPelicula.getText().trim());
            cine.deshabilitarPelicula(codigo);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Película deshabilitada.");
            onListarPeliculasClick();
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ingrese el código numérico de la película.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    // Muestra todas las peliculas en el TextArea
    private void onListarPeliculasClick() {
        StringBuilder texto = new StringBuilder();
        for (Pelicula pelicula : cine.consultarPeliculas()) {
            texto.append("Código: ").append(pelicula.getCodigo())
                    .append(" | Nombre: ").append(pelicula.getNombre())
                    .append(" | Género: ").append(pelicula.getGenero())
                    .append(" | Duración: ").append(pelicula.getDuracionMinutos()).append(" min")
                    .append(" | Clasificación: ").append(pelicula.getClasificacion())
                    .append(" | Estado: ").append(pelicula.getEstadoPelicula()).append("\n");
        }
        areaTextoPeliculas.setText(texto.toString());
    }

    // ==========================================
    // PESTAÑA 2: SALAS
    // ==========================================

    // Crea la pestania para administrar salas
    private VBox crearPestaniaSalas() {
        VBox caja = new VBox(6);
        caja.setPadding(new Insets(10));
        campoCodigoSala = new TextField();
        campoNombreSala = new TextField();
        campoCapacidadSala = new TextField();
        comboEstadoSala = new ComboBox<>(FXCollections.observableArrayList(EstadoSala.values()));
        comboEstadoSala.getSelectionModel().select(EstadoSala.DISPONIBLE);

        caja.getChildren().addAll(
                new Label("Código:"), campoCodigoSala,
                new Label("Nombre:"), campoNombreSala,
                new Label("Capacidad:"), campoCapacidadSala,
                new Label("Estado:"), comboEstadoSala
        );
        caja.getChildren().addAll(crearBotonesSalas());
        return caja;
    }

    // Agrega los botones de accion para salas
    private VBox crearBotonesSalas() {
        VBox caja = new VBox(6);
        Button botonRegistrar = new Button("Registrar Sala");
        botonRegistrar.setOnAction(e -> onRegistrarSalaClick());
        Button botonModificar = new Button("Modificar Sala");
        botonModificar.setOnAction(e -> onModificarSalaClick());
        Button botonDeshabilitar = new Button("Deshabilitar Sala");
        botonDeshabilitar.setOnAction(e -> onDeshabilitarSalaClick());
        Button botonListar = new Button("Listar Salas");
        botonListar.setOnAction(e -> onListarSalasClick());

        areaTextoSalas = new TextArea();
        areaTextoSalas.setEditable(false);
        areaTextoSalas.setPrefHeight(150);

        caja.getChildren().addAll(botonRegistrar, botonModificar, botonDeshabilitar, botonListar, areaTextoSalas);
        return caja;
    }

    // Registra una nueva sala con sus asientos
    private void onRegistrarSalaClick() {
        try {
            int codigo = Integer.parseInt(campoCodigoSala.getText().trim());
            String nombre = campoNombreSala.getText().trim();
            int capacidad = Integer.parseInt(campoCapacidadSala.getText().trim());
            if (nombre.isEmpty() || capacidad <= 0) {
                mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Datos de sala incompletos o capacidad inválida.");
                return;
            }
            Sala nueva = new Sala(codigo, nombre, capacidad);
            nueva.setEstadoSala(comboEstadoSala.getValue());
            cine.registrarSala(nueva);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Sala registrada correctamente.");
            onListarSalasClick();
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Código y capacidad deben ser números.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    // Modifica una sala existente
    private void onModificarSalaClick() {
        try {
            int codigo = Integer.parseInt(campoCodigoSala.getText().trim());
            String nombre = campoNombreSala.getText().trim();
            int capacidad = Integer.parseInt(campoCapacidadSala.getText().trim());
            Sala datos = new Sala(codigo, nombre, capacidad);
            datos.setEstadoSala(comboEstadoSala.getValue());
            cine.modificarSala(codigo, datos);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Sala modificada.");
            onListarSalasClick();
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Código y capacidad deben ser números.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    // Deshabilita la sala cambiandola a FUERA_DE_SERVICIO
    private void onDeshabilitarSalaClick() {
        try {
            int codigo = Integer.parseInt(campoCodigoSala.getText().trim());
            cine.deshabilitarSala(codigo);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Sala deshabilitada (FUERA_DE_SERVICIO).");
            onListarSalasClick();
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ingrese el código numérico de la sala.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    // Lista las salas en el TextArea
    private void onListarSalasClick() {
        StringBuilder texto = new StringBuilder();
        for (Sala sala : cine.consultarSalas()) {
            texto.append("Código: ").append(sala.getCodigo())
                    .append(" | Nombre: ").append(sala.getNombre())
                    .append(" | Capacidad: ").append(sala.getCapacidadMaxima())
                    .append(" | Estado: ").append(sala.getEstadoSala()).append("\n");
        }
        areaTextoSalas.setText(texto.toString());
    }

    // ==========================================
    // PESTAÑA 3: FUNCIONES
    // ==========================================

    // Crea la pestania para programar funciones
    private VBox crearPestaniaFunciones() {
        VBox caja = new VBox(6);
        caja.setPadding(new Insets(10));
        campoCodigoFuncion = new TextField();
        comboPeliculaFuncion = new ComboBox<>();
        comboSalaFuncion = new ComboBox<>();
        selectorFechaFuncion = new DatePicker(LocalDate.now());
        campoHoraFuncion = new TextField("18");
        campoMinutoFuncion = new TextField("30");
        campoPrecioBaseFuncion = new TextField("15000");

        caja.getChildren().addAll(
                new Label("Código Función:"), campoCodigoFuncion,
                new Label("Película:"), comboPeliculaFuncion,
                new Label("Sala:"), comboSalaFuncion,
                new Label("Fecha:"), selectorFechaFuncion,
                new Label("Hora (0-23):"), campoHoraFuncion,
                new Label("Minuto (0-59):"), campoMinutoFuncion,
                new Label("Precio Base ($):"), campoPrecioBaseFuncion
        );
        caja.getChildren().addAll(crearBotonesFunciones());
        refrescarCombosFuncion();
        return caja;
    }

    // Agrega los botones y el TextArea de funciones
    private VBox crearBotonesFunciones() {
        VBox caja = new VBox(6);
        Button botonProgramar = new Button("Programar Función");
        botonProgramar.setOnAction(e -> onProgramarFuncionClick());
        Button botonListar = new Button("Listar Funciones");
        botonListar.setOnAction(e -> onListarFuncionesClick());
        Button botonRefrescarListas = new Button("Actualizar Películas y Salas");
        botonRefrescarListas.setOnAction(e -> refrescarCombosFuncion());

        areaTextoFunciones = new TextArea();
        areaTextoFunciones.setEditable(false);
        areaTextoFunciones.setPrefHeight(150);

        caja.getChildren().addAll(botonProgramar, botonListar, botonRefrescarListas, areaTextoFunciones);
        return caja;
    }

    // Actualiza los ComboBox de peliculas y salas
    private void refrescarCombosFuncion() {
        ArrayList<String> listaPeliculas = new ArrayList<>();
        for (Pelicula p : cine.consultarPeliculas()) {
            listaPeliculas.add(p.getCodigo() + " - " + p.getNombre());
        }
        comboPeliculaFuncion.setItems(FXCollections.observableArrayList(listaPeliculas));
        if (!listaPeliculas.isEmpty()) comboPeliculaFuncion.getSelectionModel().selectFirst();

        ArrayList<String> listaSalas = new ArrayList<>();
        for (Sala s : cine.consultarSalas()) {
            listaSalas.add(s.getCodigo() + " - " + s.getNombre() + " (" + s.getEstadoSala() + ")");
        }
        comboSalaFuncion.setItems(FXCollections.observableArrayList(listaSalas));
        if (!listaSalas.isEmpty()) comboSalaFuncion.getSelectionModel().selectFirst();
    }

    // Programa una funcion validando que la sala este disponible
    private void onProgramarFuncionClick() {
        try {
            int codigo = Integer.parseInt(campoCodigoFuncion.getText().trim());
            Pelicula pelicula = buscarPeliculaPorTexto(comboPeliculaFuncion.getValue());
            Sala sala = buscarSalaPorTexto(comboSalaFuncion.getValue());
            LocalDate fecha = selectorFechaFuncion.getValue();
            int hora = Integer.parseInt(campoHoraFuncion.getText().trim());
            int minuto = Integer.parseInt(campoMinutoFuncion.getText().trim());
            double precio = Double.parseDouble(campoPrecioBaseFuncion.getText().trim());

            Funcion nueva = new Funcion(codigo, fecha, LocalTime.of(hora, minuto), precio, null, pelicula, sala);
            cine.programarFuncion(nueva);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Función programada exitosamente.");
            onListarFuncionesClick();
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Código, hora, minuto y precio deben ser números válidos.");
        } catch (SalaNoDisponibleException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Sala No Disponible", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    // Busca un objeto Pelicula en el cine usando el texto del combo
    private Pelicula buscarPeliculaPorTexto(String texto) {
        if (texto == null) return null;
        for (Pelicula p : cine.consultarPeliculas()) {
            if (texto.startsWith(p.getCodigo() + " - ")) return p;
        }
        return null;
    }

    // Busca un objeto Sala en el cine usando el texto del combo
    private Sala buscarSalaPorTexto(String texto) {
        if (texto == null) return null;
        for (Sala s : cine.consultarSalas()) {
            if (texto.startsWith(s.getCodigo() + " - ")) return s;
        }
        return null;
    }

    // Lista las funciones registradas en el TextArea
    private void onListarFuncionesClick() {
        StringBuilder texto = new StringBuilder();
        for (Funcion funcion : cine.consultarFunciones()) {
            texto.append("Código: ").append(funcion.getCodigo())
                    .append(" | Película: ").append(funcion.getPelicula().getNombre())
                    .append(" | Sala: ").append(funcion.getSala().getNombre())
                    .append(" | Fecha: ").append(funcion.getFecha())
                    .append(" | Hora: ").append(funcion.getHora())
                    .append(" | Precio: $").append(funcion.getPrecioBase())
                    .append(" | Estado: ").append(funcion.getEstadoFuncion()).append("\n");
        }
        areaTextoFunciones.setText(texto.toString());
    }

    // ==========================================
    // PESTAÑA 4: TARIFAS
    // ==========================================

    // Crea la pestania para gestionar las tarifas
    private VBox crearPestaniaTarifas() {
        VBox caja = new VBox(6);
        caja.setPadding(new Insets(10));
        comboTipoClienteTarifa = new ComboBox<>(FXCollections.observableArrayList(TipoCliente.values()));
        comboTipoClienteTarifa.getSelectionModel().select(TipoCliente.GENERAL);
        campoPorcentajeTarifa = new TextField("0");

        Button botonGuardar = new Button("Guardar Porcentaje");
        botonGuardar.setOnAction(e -> onGuardarTarifaClick());
        Button botonListar = new Button("Ver Tarifas Actuales");
        botonListar.setOnAction(e -> onListarTarifasClick());

        areaTextoTarifas = new TextArea();
        areaTextoTarifas.setEditable(false);
        areaTextoTarifas.setPrefHeight(150);

        caja.getChildren().addAll(
                new Label("Tipo de Cliente:"), comboTipoClienteTarifa,
                new Label("Porcentaje de Descuento (0 - 100):"), campoPorcentajeTarifa,
                botonGuardar, botonListar, areaTextoTarifas
        );
        return caja;
    }

    // Actualiza la tarifa seleccionada en el modelo
    private void onGuardarTarifaClick() {
        try {
            double porcentaje = Double.parseDouble(campoPorcentajeTarifa.getText().trim());
            TipoCliente tipo = comboTipoClienteTarifa.getValue();
            ArrayList<Tarifa> lista = cine.consultarTarifas();
            for (Tarifa tarifa : lista) {
                if (tarifa.getTipoCliente() == tipo) {
                    tarifa.setPorcentajeDescuento(porcentaje);
                }
            }
            cine.setListTarifa(lista);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Tarifa actualizada.");
            onListarTarifasClick();
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "El porcentaje debe ser un número.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    // Muestra las tarifas en el TextArea
    private void onListarTarifasClick() {
        StringBuilder texto = new StringBuilder();
        for (Tarifa tarifa : cine.consultarTarifas()) {
            texto.append("Tipo: ").append(tarifa.getTipoCliente())
                    .append(" | Descuento: ").append(tarifa.getPorcentajeDescuento()).append("%\n");
        }
        areaTextoTarifas.setText(texto.toString());
    }

    // ==========================================
    // PESTAÑA 5: REPORTES
    // ==========================================

    // Crea la pestania para los reportes del sistema
    private VBox crearPestaniaReportes() {
        VBox caja = new VBox(6);
        caja.setPadding(new Insets(10));

        Button botonEntradas = new Button("1. Total Entradas Vendidas");
        botonEntradas.setOnAction(e -> onReporteEntradasClick());
        Button botonIngresos = new Button("2. Total de Ingresos");
        botonIngresos.setOnAction(e -> onReporteIngresosClick());
        Button botonPeliculaMasVendida = new Button("3. Película Más Vendida");
        botonPeliculaMasVendida.setOnAction(e -> onReportePeliculaMasVendidaClick());
        Button botonBoletasEmitidas = new Button("4. Total de Boletas Emitidas");
        botonBoletasEmitidas.setOnAction(e -> onReporteBoletasEmitidasClick());

        comboFuncionReporteOcupacion = new ComboBox<>();
        llenarComboReporteOcupacion();
        Button botonOcupacion = new Button("5. Ocupación de la Función");
        botonOcupacion.setOnAction(e -> onReporteOcupacionClick());

        selectorFechaReporte = new DatePicker(LocalDate.now());
        Button botonFecha = new Button("6. Funciones por Fecha");
        botonFecha.setOnAction(e -> onReporteFechaClick());

        areaTextoReportes = new TextArea();
        areaTextoReportes.setEditable(false);
        areaTextoReportes.setPrefHeight(180);

        caja.getChildren().addAll(
                botonEntradas, botonIngresos, botonPeliculaMasVendida, botonBoletasEmitidas,
                new Label("Función para Ocupación:"), comboFuncionReporteOcupacion, botonOcupacion,
                new Label("Fecha para Funciones:"), selectorFechaReporte, botonFecha,
                new Label("Resultado del Reporte:"), areaTextoReportes
        );
        return caja;
    }

    // Llena el ComboBox de funciones para reporte de ocupacion
    private void llenarComboReporteOcupacion() {
        ArrayList<String> lista = new ArrayList<>();
        for (Funcion f : cine.consultarFunciones()) {
            lista.add(f.getCodigo() + " - " + f.getPelicula().getNombre());
        }
        comboFuncionReporteOcupacion.setItems(FXCollections.observableArrayList(lista));
        if (!lista.isEmpty()) comboFuncionReporteOcupacion.getSelectionModel().selectFirst();
    }

    // Muestra la cantidad total de entradas vendidas
    private void onReporteEntradasClick() {
        int total = cine.reporteTotalEntradasVendidas();
        areaTextoReportes.setText("Reporte 1: Total de entradas vendidas activas = " + total);
    }

    // Muestra el dinero total recaudado por ventas activas
    private void onReporteIngresosClick() {
        double ingresos = cine.reporteValorTotalVentas();
        areaTextoReportes.setText("Reporte 2: Total de ingresos recaudados = $" + ingresos);
    }

    // Muestra la pelicula con mayor cantidad de entradas vendidas usando isPresent()
    private void onReportePeliculaMasVendidaClick() {
        Optional<Pelicula> resultado = cine.reportePeliculaMasVendida();
        if (resultado.isPresent()) {
            Pelicula pelicula = resultado.get();
            areaTextoReportes.setText("Reporte 3: Película más vendida = " + pelicula.getNombre() + " (Código " + pelicula.getCodigo() + ")");
        } else {
            areaTextoReportes.setText("Reporte 3: No hay ventas registradas todavía.");
        }
    }

    // Muestra el total de boletas emitidas por el cinema
    private void onReporteBoletasEmitidasClick() {
        int total = cine.reporteTotalBoletasEmitidas();
        areaTextoReportes.setText("Reporte 4: Total de boletas emitidas = " + total);
    }

    // Muestra disponibles, ocupados y porcentaje de ocupacion de una funcion
    private void onReporteOcupacionClick() {
        String seleccion = comboFuncionReporteOcupacion.getValue();
        if (seleccion == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione una función.");
            return;
        }
        try {
            int codigo = Integer.parseInt(seleccion.split(" - ")[0]);
            OcupacionFuncion ocupacion = cine.calcularOcupacionFuncion(codigo);
            areaTextoReportes.setText("Reporte 5: Ocupación de la función " + codigo + ":\n"
                    + "- Asientos Disponibles: " + ocupacion.getCantidadDisponible() + "\n"
                    + "- Asientos Ocupados: " + ocupacion.getCantidadOcupada() + "\n"
                    + "- Porcentaje de Ocupación: " + ocupacion.getPorcentajeOcupacion() + "%");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    // Muestra las funciones programadas en la fecha elegida
    private void onReporteFechaClick() {
        LocalDate fecha = selectorFechaReporte.getValue();
        if (fecha == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione una fecha.");
            return;
        }
        StringBuilder texto = new StringBuilder();
        texto.append("Reporte 6: Funciones para la fecha ").append(fecha).append(":\n");
        for (Funcion f : cine.consultarFuncionesPorFecha(fecha)) {
            texto.append("- Código: ").append(f.getCodigo())
                    .append(" | Película: ").append(f.getPelicula().getNombre())
                    .append(" | Sala: ").append(f.getSala().getNombre())
                    .append(" | Hora: ").append(f.getHora())
                    .append(" | Precio: $").append(f.getPrecioBase()).append("\n");
        }
        areaTextoReportes.setText(texto.toString());
    }

    // Regresa a la pantalla de login al cerrar sesion
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
