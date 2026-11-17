package cinemauq.model;

import Excepciones.*;
import Enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Cine {

    private String nombre;
    private int nit;
    private int telefono;
    private String direccion;
    private ArrayList<Pelicula> listPelicula;
    private ArrayList<Sala> listSala;
    private ArrayList<Funcion> listFuncion;
    private ArrayList<Cliente> listCliente;
    private ArrayList<Venta> listVenta;
    private ArrayList<Tarifa> listTarifa;
    private ArrayList<Boleta> listBoleta;


    public Cine() {
        listPelicula = new ArrayList<>();
        listSala = new ArrayList<>();
        listFuncion = new ArrayList<>();
        listCliente = new ArrayList<>();
        listVenta = new ArrayList<>();
        listTarifa = new ArrayList<>();
        listBoleta = new ArrayList<>();
        inicializarTarifas();
    }

    public Cine(String nombre, int nit, int telefono, String direccion) {
        this();
        this.nombre = nombre;
        this.nit = nit;
        this.telefono = telefono;
        this.direccion = direccion;
    }


    /** Metodo para registrar peliculas y su respectiva excepcion verificando
     * que no existan peliculas con el mismo codigo
     */
    public void registrarPelicula(Pelicula pelicula) throws PeliculaDuplicadaException {
        if (pelicula == null) {
            throw new IllegalArgumentException("La película es obligatoria");
        }
        if (buscarPelicula(pelicula.getCodigo()) != null) {
            throw new PeliculaDuplicadaException("Ya existe una película con código " + pelicula.getCodigo());
        }
        listPelicula.add(pelicula);
    }

    /**
     * Metodo para consultar las peliculas que ya fueron registradas
     * @return
     */
    public ArrayList<Pelicula> consultarPeliculas() {
        return new ArrayList<>(listPelicula);
    }


    /**
     * Metodo para modificar las peliculas que ya fueron registradas anteriormente ademas de su excepcion exclamar una pelicula que
     * no existe dentro del programa y excepcion para exclamar que la pelicula que se esta modificando no sea igual a otra ya registrada
     * @param codigo
     * @param peliculaActualizada
     * @return
     * @throws PeliculaNoEncontradaException
     * @throws PeliculaDuplicadaException
     */
    public Pelicula modificarPelicula(int codigo, Pelicula peliculaActualizada)
            throws PeliculaNoEncontradaException, PeliculaDuplicadaException {
        if (peliculaActualizada == null) {
            throw new IllegalArgumentException("La película actualizada es obligatoria");
        }
        Pelicula pelicula = buscarPelicula(codigo);
        if (pelicula == null) {
            throw new PeliculaNoEncontradaException("No existe la película " + codigo);
        }
        if (peliculaActualizada.getCodigo() != codigo
                && buscarPelicula(peliculaActualizada.getCodigo()) != null) {
            throw new PeliculaDuplicadaException(
                    "Ya existe una película con código " + peliculaActualizada.getCodigo());
        }
        pelicula.setCodigo(peliculaActualizada.getCodigo());
        pelicula.setNombre(peliculaActualizada.getNombre());
        pelicula.setGenero(peliculaActualizada.getGenero());
        pelicula.setDuracionMinutos(peliculaActualizada.getDuracionMinutos());
        pelicula.setClasificacion(peliculaActualizada.getClasificacion());
        pelicula.setEstadoPelicula(peliculaActualizada.getEstadoPelicula());
        return pelicula;
    }


    /**
     * Metodo para deshabilitar una pelicula que ya fue registrada ademas de su excepcion para exclamar una pelicula que
     * no existe dentro del programa
     * @param codigo
     * @throws PeliculaNoEncontradaException
     */
    public void deshabilitarPelicula(int codigo) throws PeliculaNoEncontradaException {
        Pelicula pelicula = buscarPelicula(codigo);
        if (pelicula == null) {
            throw new PeliculaNoEncontradaException("No existe la película " + codigo);
        }
        pelicula.setEstadoPelicula(EstadoPelicula.NO_DISPONIBLE);
    }

    /**
     * Metodo para registrar salas y su respectiva excepcion verificando
     * que no existan salas con el mismo codigo
     * @param sala
     * @throws SalaDuplicadaException
     */
    public void registrarSala(Sala sala) throws SalaDuplicadaException {
        if (sala == null) {
            throw new IllegalArgumentException("La sala es obligatoria");
        }
        if (buscarSala(sala.getCodigo()) != null) {
            throw new SalaDuplicadaException("Ya existe una sala con código " + sala.getCodigo());
        }
        listSala.add(sala);
    }

    /**
     * Metodo para consultar las salas que ya fueron registradas
     * @return
     */
    public ArrayList<Sala> consultarSalas() {
        return new ArrayList<>(listSala);
    }

    /**
     * Metodo para modificar las salas que ya fueron registradas anteriormente ademas de su excepcion exclamar una sala que
     * no existe dentro del programa y excepcion para exclamar que la sala que se esta modificando no sea igual a otra ya registrada
     * @param codigo
     * @param salaActualizada
     * @return
     * @throws SalaNoEncontradaException
     * @throws SalaDuplicadaException
     */
    public Sala modificarSala(int codigo, Sala salaActualizada)
            throws SalaNoEncontradaException, SalaDuplicadaException {
        if (salaActualizada == null) {
            throw new IllegalArgumentException("La sala actualizada es obligatoria");
        }
        Sala sala = buscarSala(codigo);
        if (sala == null) {
            throw new SalaNoEncontradaException("No existe la sala " + codigo);
        }
        if (salaActualizada.getCodigo() != codigo && buscarSala(salaActualizada.getCodigo()) != null) {
            throw new SalaDuplicadaException("Ya existe una sala con código " + salaActualizada.getCodigo());
        }
        sala.setCodigo(salaActualizada.getCodigo());
        sala.setNombre(salaActualizada.getNombre());
        sala.setCapacidadMaxima(salaActualizada.getCapacidadMaxima());
        sala.setEstadoSala(salaActualizada.getEstadoSala());
        sala.setAsientos(salaActualizada.getAsientos());
        return sala;
    }

    /**
     * Metodo para deshabilitar una sala que ya fue registrada ademas de su excepcion para exclamar una pelicula que
     * no existe dentro del programa
     * @param codigo
     * @throws SalaNoEncontradaException
     */
    public void deshabilitarSala(int codigo) throws SalaNoEncontradaException {
        Sala sala = buscarSala(codigo);
        if (sala == null) {
            throw new SalaNoEncontradaException("No existe la sala " + codigo);
        }
        sala.setEstadoSala(EstadoSala.FUERA_DE_SERVICIO);
    }

    /**
     * Metodo para programar una funcion dentro del programa, con sus respectivas excepciones para:
     * Demostrar que una funcion esta duplicada,
     * Demostrar que una sala no se encuentra reigstrada,
     * Demostrar que la pelicula no se encuentra registrada,
     * Demostrar que la Model.Sala declarada no se encuentra disponible en el momento
     * @param funcion
     * @throws FuncionDuplicadaException
     * @throws SalaNoEncontradaException
     * @throws PeliculaNoEncontradaException
     * @throws SalaNoDisponibleException
     */
    public void programarFuncion(Funcion funcion)
            throws FuncionDuplicadaException, SalaNoEncontradaException,
            PeliculaNoEncontradaException, SalaNoDisponibleException {
        if (funcion == null) {
            throw new IllegalArgumentException("La función es obligatoria");
        }
        if (buscarFuncion(funcion.getCodigo()) != null) {
            throw new FuncionDuplicadaException("Ya existe una función con código " + funcion.getCodigo());
        }
        if (funcion.getSala() == null) {
            throw new IllegalArgumentException("La sala de la función es obligatoria");
        }
        if (funcion.getPelicula() == null) {
            throw new IllegalArgumentException("La película de la función es obligatoria");
        }

        int codigoSala = funcion.getSala().getCodigo();
        int codigoPelicula = funcion.getPelicula().getCodigo();
        Sala sala = buscarSala(codigoSala);
        Pelicula pelicula = buscarPelicula(codigoPelicula);
        if (sala == null) {
            throw new SalaNoEncontradaException("No existe la sala " + codigoSala);
        }
        if (pelicula == null) {
            throw new PeliculaNoEncontradaException("No existe la película " + codigoPelicula);
        }
        if (sala.getEstadoSala() != EstadoSala.DISPONIBLE) {
            throw new SalaNoDisponibleException("La sala " + codigoSala + " no está disponible");
        }
        funcion.setSala(sala);
        funcion.setPelicula(pelicula);
        funcion.setEstadoFuncion(EstadoFuncion.PROGRAMADA);
        listFuncion.add(funcion);
    }

    /**
     * Metodo para consultar las funciones que ya fueron registradas
     * @return
     */
    public ArrayList<Funcion> consultarFunciones() {
        return new ArrayList<>(listFuncion);
    }

    /**
     * Metodo para revisar la disponibilidad de una funcion, con la excepcion de revisar si la funcion nombrada no existe
     * @param codigoFuncion
     * @return
     * @throws FuncionNoEncontradaException
     */
    public DisponibilidadFuncion consultarDisponibilidadFuncion(int codigoFuncion)
            throws FuncionNoEncontradaException {
        Funcion funcion = buscarFuncion(codigoFuncion);
        if (funcion == null) {
            throw new FuncionNoEncontradaException("No existe la función " + codigoFuncion);
        }

        ArrayList<Asiento> asientos = funcion.getSala().getAsientos();
        int disponibles = 0;
        int ocupados = 0;
        for (Asiento asiento : asientos) {
            if (asiento.getEstadoAsiento() == EstadoAsiento.DISPONIBLE) {
                disponibles++;
            } else {
                ocupados++;
            }
        }
        return new DisponibilidadFuncion(disponibles, ocupados, asientos.size());
    }

    /**
     *  Metodo que registra una venta y emite una boleta para cada asiento vendido.
     * @param codigo
     * @param cliente
     * @param codigoFuncion
     * @param codigosAsientos
     * @return
     * @throws FuncionNoEncontradaException
     * @throws AsientoOcupadoException
     * @throws SinAsientosDisponiblesException
     */


    public Venta registrarVenta(String codigo, Cliente cliente, int codigoFuncion, List<Integer> codigosAsientos)
            throws FuncionNoEncontradaException, AsientoOcupadoException, SinAsientosDisponiblesException {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código de venta es obligatorio");
        }
        for (Venta ventaExistente : listVenta) {
            if (ventaExistente.getCodigo().equals(codigo)) {
                throw new IllegalArgumentException("Ya existe una venta con código " + codigo);
            }
        }

        Funcion funcion = buscarFuncion(codigoFuncion);
        if (funcion == null) {
            throw new FuncionNoEncontradaException("No existe la función " + codigoFuncion);
        }
        if (funcion.getEstadoFuncion() != EstadoFuncion.PROGRAMADA) {
            throw new IllegalStateException("La función " + codigoFuncion + " no está programada");
        }
        if (codigosAsientos == null || codigosAsientos.isEmpty()) {
            throw new SinAsientosDisponiblesException("Debe seleccionar al menos un asiento");
        }

        ArrayList<Asiento> asientosSala = funcion.getSala().getAsientos();
        boolean hayAsientosDisponibles = false;
        for (Asiento asiento : asientosSala) {
            if (asiento.getEstadoAsiento() == EstadoAsiento.DISPONIBLE) {
                hayAsientosDisponibles = true;
                break;
            }
        }
        if (!hayAsientosDisponibles) {
            throw new SinAsientosDisponiblesException("No hay asientos disponibles para la función");
        }

        ArrayList<Asiento> asientosVenta = new ArrayList<>();
        for (int indice = 0; indice < codigosAsientos.size(); indice++) {
            Integer codigoAsiento = codigosAsientos.get(indice);
            for (int otroIndice = indice + 1; otroIndice < codigosAsientos.size(); otroIndice++) {
                if (codigosAsientos.get(otroIndice).equals(codigoAsiento)) {
                    throw new AsientoOcupadoException("No se puede seleccionar un asiento más de una vez");
                }
            }

            Asiento asientoEncontrado = null;
            for (Asiento asiento : asientosSala) {
                if (asiento.getCodigo() == codigoAsiento) {
                    asientoEncontrado = asiento;
                    break;
                }
            }
            if (asientoEncontrado == null) {
                throw new SinAsientosDisponiblesException(
                        "No existe el asiento " + codigoAsiento + " en la sala");
            }
            if (asientoEncontrado.getEstadoAsiento() != EstadoAsiento.DISPONIBLE) {
                throw new AsientoOcupadoException("El asiento " + codigoAsiento + " está ocupado");
            }
            asientosVenta.add(asientoEncontrado);
        }

        Tarifa tarifa = obtenerTarifa(cliente.getTipoCliente());
        double precioPorAsiento = redondear(funcion.getPrecioBase()
                * (1 - tarifa.getPorcentajeDescuento() / 100.0));
        double valorPagado = redondear(precioPorAsiento * asientosVenta.size());
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        Cliente clienteRegistrado = registrarOConsultarCliente(cliente);
        Venta venta = new Venta(codigo, fechaHoraActual.toLocalDate(), fechaHoraActual.toLocalTime(),
                valorPagado, EstadoVenta.ACTIVA, clienteRegistrado, funcion, asientosVenta);

        for (Asiento asiento : asientosVenta) {
            asiento.setEstadoAsiento(EstadoAsiento.OCUPADO);
        }
        listVenta.add(venta);
        generarBoleta(venta);
        return venta;
    }

    /**
     * Registra y procesa la venta de una entrada para un único asiento.
     * Este método actúa como una sobrecarga (atajo) para facilitar la labor del programador
     * desde la interfaz gráfica, evitando que tenga que instanciar manualmente una lista
     * cuando un cliente compra solo una boleta.
     */
    public Venta registrarVenta(String codigo, Cliente cliente, int codigoFuncion, int codigoAsiento)
            throws FuncionNoEncontradaException, AsientoOcupadoException, SinAsientosDisponiblesException {
        ArrayList<Integer> codigosAsientos = new ArrayList<>();
        codigosAsientos.add(codigoAsiento);
        return registrarVenta(codigo, cliente, codigoFuncion, codigosAsientos);
    }

    /**
     * Metodo para cancelar una venta con la excepcion que demuestre que la venta nombrada no fue encontrada
     * @param codigo
     * @throws VentaNoEncontradaException
     */
    public void cancelarVenta(String codigo) throws VentaNoEncontradaException {
        Venta venta = buscarVenta(codigo);
        if (venta == null) {
            throw new VentaNoEncontradaException("No existe la venta " + codigo);
        }
        if (venta.getEstadoVenta() == EstadoVenta.ACTIVA) {
            venta.setEstadoVenta(EstadoVenta.CANCELADA);
            ArrayList<Asiento> asientos = venta.getAsientos();
            for (Asiento asiento : asientos) {
                asiento.setEstadoAsiento(EstadoAsiento.DISPONIBLE);
            }
        }
    }

    /**
     * Metodo que genera el reporte de todas las entradas que se vendieron en total
     * @return
     */
    public int reporteTotalEntradasVendidas() {
        int cantidad = 0;
        for (Venta venta : listVenta) {
            if (venta.getEstadoVenta() == EstadoVenta.ACTIVA) {
                cantidad++;
            }
        }
        return cantidad;
    }


    /**
     * Metodo que genera el reporte de todo el dinero que entro con respecto a las ventas realizadas
     * @return
     */
    public double reporteValorTotalVentas() {
        double total = 0;
        for (Venta venta : listVenta) {
            if (venta.getEstadoVenta() == EstadoVenta.ACTIVA) {
                total += venta.getValorPagado();
            }
        }
        return redondear(total);
    }

    /**
     * Metodo que busca la película que aparece en más ventas activas.
     * @return
     */
    public Optional<Pelicula> reportePeliculaMasVendida() {
        if (listVenta.isEmpty()) {
            return Optional.empty();
        }

        Pelicula peliculaMasVendida = null;
        int mayorCantidad = 0;
        for (Pelicula pelicula : listPelicula) {
            int cantidadVentas = 0;
            for (Venta venta : listVenta) {
                if (venta.getEstadoVenta() == EstadoVenta.ACTIVA
                        && venta.getFuncion().getPelicula().equals(pelicula)) {
                    cantidadVentas++;
                }
            }
            if (cantidadVentas > mayorCantidad) {
                mayorCantidad = cantidadVentas;
                peliculaMasVendida = pelicula;
            }
        }
        if (peliculaMasVendida == null) {
            return Optional.empty();
        }
        return Optional.of(peliculaMasVendida);
    }

    /**
     * Calcula las estadísticas de ocupación (asientos disponibles, ocupados y el porcentaje)
     * de una función específica en el cine.
     * @param codigoFuncion
     * @return
     * @throws FuncionNoEncontradaException
     */
    public OcupacionFuncion calcularOcupacionFuncion(int codigoFuncion)
            throws FuncionNoEncontradaException {
        DisponibilidadFuncion disponibilidad = consultarDisponibilidadFuncion(codigoFuncion);
        double porcentaje = 0;
        if (disponibilidad.getTotalAsientos() > 0) {
            porcentaje = disponibilidad.getAsientosOcupados() * 100.0
                    / disponibilidad.getTotalAsientos();
        }
        return new OcupacionFuncion(disponibilidad.getAsientosDisponibles(),
                disponibilidad.getAsientosOcupados(), porcentaje);
    }

    public ArrayList<Funcion> consultarFuncionesPorFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }
        ArrayList<Funcion> funcionesFecha = new ArrayList<>();
        for (Funcion funcion : listFuncion) {
            if (fecha.equals(funcion.getFecha())) {
                funcionesFecha.add(funcion);
            }
        }
        return funcionesFecha;
    }

    public void registrarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        for (Cliente clienteRegistrado : listCliente) {
            if (Objects.equals(clienteRegistrado.getDocumento(), cliente.getDocumento())) {
                return;
            }
        }
        listCliente.add(cliente);
    }

    public ArrayList<Cliente> consultarClientes() {
        return new ArrayList<>(listCliente);
    }

    public ArrayList<Venta> consultarVentas() {
        return new ArrayList<>(listVenta);
    }

    /**
     * Metodo que permite cargar las tarifas iniciales para cada tipo de cliente.
     */
    public void inicializarTarifas() {
        listTarifa.clear();
        listTarifa.add(new Tarifa(TipoCliente.GENERAL, 0));
        listTarifa.add(new Tarifa(TipoCliente.ESTUDIANTE, 20));
        listTarifa.add(new Tarifa(TipoCliente.NINO, 30));
        listTarifa.add(new Tarifa(TipoCliente.ADULTO_MAYOR, 25));
    }


    public ArrayList<Tarifa> consultarTarifas() {
        return new ArrayList<>(listTarifa);
    }

    public void setListTarifa(ArrayList<Tarifa> listTarifa) {
        if (listTarifa == null) {
            this.listTarifa = new ArrayList<>();
        } else {
            this.listTarifa = new ArrayList<>(listTarifa);
        }
    }

    // Crea una boleta para cada asiento de la venta.
    public ArrayList<Boleta> generarBoleta(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La venta es obligatoria");
        }
        Venta ventaRegistrada = buscarVenta(venta.getCodigo());
        if (ventaRegistrada == null) {
            throw new IllegalArgumentException("La venta debe estar registrada en el cine");
        }
        if (!ventaRegistrada.getBoletas().isEmpty()) {
            return ventaRegistrada.getBoletas();
        }

        ArrayList<Boleta> boletasVenta = new ArrayList<>();
        LocalDateTime fechaHoraEmision = LocalDateTime.now();
        ArrayList<Asiento> asientos = ventaRegistrada.getAsientos();
        for (Asiento asiento : asientos) {
            String codigoBoleta = generarCodigoBoleta();
            Boleta boleta = new Boleta(codigoBoleta, ventaRegistrada,
                    ventaRegistrada.getCliente(), ventaRegistrada.getFuncion(),
                    asiento, fechaHoraEmision.toLocalDate(), fechaHoraEmision.toLocalTime());
            listBoleta.add(boleta);
            boletasVenta.add(boleta);
        }
        ventaRegistrada.setBoletas(boletasVenta);
        return new ArrayList<>(boletasVenta);
    }

    public Optional<Boleta> buscarBoletaPorCodigo(String codigoBoleta) {
        for (Boleta boleta : listBoleta) {
            if (Objects.equals(boleta.getCodigoBoleta(), codigoBoleta)) {
                return Optional.of(boleta);
            }
        }
        return Optional.empty();
    }

    public ArrayList<Boleta> consultarBoletas() {
        return new ArrayList<>(listBoleta);
    }

    public int reporteTotalBoletasEmitidas() {
        return listBoleta.size();
    }

    public ArrayList<Boleta> consultarBoletasPorPelicula(int codigoPelicula) {
        ArrayList<Boleta> resultado = new ArrayList<>();
        for (Boleta boleta : listBoleta) {
            if (boleta.getFuncion().getPelicula().getCodigo() == codigoPelicula) {
                resultado.add(boleta);
            }
        }
        return resultado;
    }

    public ArrayList<Boleta> consultarBoletasPorFecha(LocalDate fechaEmision) {
        if (fechaEmision == null) {
            throw new IllegalArgumentException("La fecha de emisión es obligatoria");
        }
        ArrayList<Boleta> resultado = new ArrayList<>();
        for (Boleta boleta : listBoleta) {
            if (fechaEmision.equals(boleta.getFechaEmision())) {
                resultado.add(boleta);
            }
        }
        return resultado;
    }

    public Map<String, Long> reporteBoletasEmitidasPorPelicula() {
        Map<String, Long> reporte = new LinkedHashMap<>();
        for (Boleta boleta : listBoleta) {
            String nombrePelicula = boleta.getFuncion().getPelicula().getNombre();
            if (reporte.containsKey(nombrePelicula)) {
                reporte.put(nombrePelicula, reporte.get(nombrePelicula) + 1);
            } else {
                reporte.put(nombrePelicula, 1L);
            }
        }
        return reporte;
    }

    private String generarCodigoBoleta() {
        int consecutivo = listBoleta.size() + 1;
        String codigo;
        do {
            codigo = String.format("BOL-%06d", consecutivo);
            consecutivo++;
        } while (buscarBoletaPorCodigo(codigo).isPresent());
        return codigo;
    }

    private Cliente registrarOConsultarCliente(Cliente cliente) {
        for (Cliente clienteRegistrado : listCliente) {
            if (Objects.equals(clienteRegistrado.getDocumento(), cliente.getDocumento())) {
                return clienteRegistrado;
            }
        }
        listCliente.add(cliente);
        return cliente;
    }

    private Pelicula buscarPelicula(int codigo) {
        for (Pelicula pelicula : listPelicula) {
            if (pelicula.getCodigo() == codigo) {
                return pelicula;
            }
        }
        return null;
    }

    private Sala buscarSala(int codigo) {
        for (Sala sala : listSala) {
            if (sala.getCodigo() == codigo) {
                return sala;
            }
        }
        return null;
    }

    private Funcion buscarFuncion(int codigo) {
        for (Funcion funcion : listFuncion) {
            if (funcion.getCodigo() == codigo) {
                return funcion;
            }
        }
        return null;
    }

    private Venta buscarVenta(String codigo) {
        for (Venta venta : listVenta) {
            if (Objects.equals(venta.getCodigo(), codigo)) {
                return venta;
            }
        }
        return null;
    }

    private Tarifa obtenerTarifa(TipoCliente tipoCliente) {
        if (tipoCliente == null) {
            throw new IllegalArgumentException("El tipo de cliente es obligatorio");
        }
        for (Tarifa tarifa : listTarifa) {
            if (tarifa.getTipoCliente() == tipoCliente) {
                return tarifa;
            }
        }
        throw new IllegalStateException("No hay una tarifa para " + tipoCliente);
    }

    private static double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Pelicula> getListPelicula() {
        return consultarPeliculas();
    }

    public ArrayList<Sala> getListSala() {
        return consultarSalas();
    }

    public ArrayList<Funcion> getListFuncion() {
        return consultarFunciones();
    }

    public ArrayList<Cliente> getListCliente() {
        return consultarClientes();
    }

    public ArrayList<Venta> getListVenta() {
        return consultarVentas();
    }

    public ArrayList<Tarifa> getListTarifa() {
        return consultarTarifas();
    }

    public ArrayList<Boleta> getListBoleta() {
        return consultarBoletas();
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (!(objeto instanceof Cine)) {
            return false;
        }
        Cine otroCine = (Cine) objeto;
        return nit == otroCine.nit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nit);
    }

    @Override
    public String toString() {
        return "Model.Cine{nombre='" + nombre + "', nit=" + nit
                + ", telefono=" + telefono + ", direccion='" + direccion
                + "', peliculas=" + listPelicula.size() + ", salas=" + listSala.size()
                + ", funciones=" + listFuncion.size() + ", clientes=" + listCliente.size()
                + ", ventas=" + listVenta.size() + ", boletas=" + listBoleta.size() + "}";
    }
}
