package cinemauq.test;

import cinemauq.enums.*;
import cinemauq.excepciones.*;
import cinemauq.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CineTest {
    private Cine cine;
    private Pelicula pelicula;
    private Pelicula peliculaAlternativa;
    private Sala sala;

    @BeforeEach
    void setUp() throws PeliculaDuplicadaException, SalaDuplicadaException,
            SalaNoEncontradaException, PeliculaNoEncontradaException,
            FuncionDuplicadaException, SalaNoDisponibleException {
        cine = new Cine("CINEMAUQ", 123, 606123456, "Armenia");
        pelicula = new Pelicula(1, "Película A", Genero.DRAMA, 100,
                Clasificacion.TODOS, EstadoPelicula.DISPONIBLE);
        peliculaAlternativa = new Pelicula(2, "Película B", Genero.COMEDIA, 90,
                Clasificacion.MAYORES_12, EstadoPelicula.DISPONIBLE);
        sala = new Sala(1, "Sala 1", 10);
        cine.registrarPelicula(pelicula);
        cine.registrarPelicula(peliculaAlternativa);
        cine.registrarSala(sala);
    }

    @Test
    void registrarPeliculaRechazaCodigoDuplicado() throws PeliculaDuplicadaException {
        try {
            cine.registrarPelicula(new Pelicula(1, "Duplicada", Genero.DRAMA, 80,
                    Clasificacion.TODOS, EstadoPelicula.DISPONIBLE));
            fail("Se esperaba una excepción por código duplicado");
        } catch (PeliculaDuplicadaException excepcion) {
            assertNotNull(excepcion.getMessage());
        }
        assertEquals(2, cine.consultarPeliculas().size());
    }

    @Test
    void registrarSalaRechazaCodigoDuplicado() {
        try {
            cine.registrarSala(new Sala(1, "Duplicada", 5));
            fail("Se esperaba una excepción por código duplicado");
        } catch (SalaDuplicadaException excepcion) {
            assertNotNull(excepcion.getMessage());
        }
        assertEquals(1, cine.consultarSalas().size());
    }

    @Test
    void programarFuncionRequiereSalaYPeliculaRegistradasYDisponibles()
            throws SalaDuplicadaException, FuncionDuplicadaException, SalaNoEncontradaException,
            PeliculaNoEncontradaException, SalaNoDisponibleException {
        Funcion funcion = crearFuncion(1, pelicula, sala);
        cine.programarFuncion(funcion);
        assertEquals(1, cine.consultarFunciones().size());

        Sala fueraDeServicio = new Sala(2, "Sala no disponible", 5,
                EstadoSala.FUERA_DE_SERVICIO, new java.util.ArrayList<>());
        cine.registrarSala(fueraDeServicio);
        try {
            cine.programarFuncion(crearFuncion(2, pelicula, fueraDeServicio));
            fail("Se esperaba una excepción porque la sala no está disponible");
        } catch (SalaNoDisponibleException excepcion) {
            assertNotNull(excepcion.getMessage());
        }
    }

    @Test
    void registrarVentaAplicaDescuentoYOcupaAsiento()
            throws FuncionDuplicadaException, SalaNoEncontradaException,
            PeliculaNoEncontradaException, SalaNoDisponibleException,
            FuncionNoEncontradaException, AsientoOcupadoException, SinAsientosDisponiblesException {
        cine.programarFuncion(crearFuncion(1, pelicula, sala));
        Cliente estudiante = new Cliente("100", "Ana", TipoCliente.ESTUDIANTE);

        Venta venta = cine.registrarVenta("V1", estudiante, 1, 1);

        assertEquals(8000.0, venta.getValorPagado(), 0.001);
        assertEquals(EstadoVenta.ACTIVA, venta.getEstadoVenta());
        assertEquals(1, venta.getBoletas().size());
        assertEquals(1, cine.consultarBoletas().size());
        assertEquals(venta, venta.getBoletas().get(0).getVenta());
        assertEquals(venta.getCliente(), venta.getBoletas().get(0).getCliente());
        assertEquals(venta.getFuncion(), venta.getBoletas().get(0).getFuncion());
        assertEquals("BOL-000001", venta.getBoletas().get(0).getCodigoBoleta());
        assertEquals(EstadoAsiento.OCUPADO, sala.getAsientos().get(0).getEstadoAsiento());
        try {
            cine.registrarVenta("V2", estudiante, 1, 1);
            fail("Se esperaba una excepción porque el asiento está ocupado");
        } catch (AsientoOcupadoException excepcion) {
            assertNotNull(excepcion.getMessage());
        }
        assertEquals(1, cine.consultarDisponibilidadFuncion(1).getAsientosOcupados());
    }

    @Test
    void tarifasSeInicializanYBoletasPuedenConsultarsePorPeliculaYFecha()
            throws FuncionDuplicadaException, SalaNoEncontradaException,
            PeliculaNoEncontradaException, SalaNoDisponibleException,
            FuncionNoEncontradaException, AsientoOcupadoException, SinAsientosDisponiblesException {
        assertEquals(4, cine.consultarTarifas().size());
        assertEquals(0, cine.consultarTarifas().get(0).getPorcentajeDescuento(), 0.001);
        cine.programarFuncion(crearFuncion(1, pelicula, sala));
        cine.registrarVenta("V1", new Cliente("100", "Ana", TipoCliente.GENERAL), 1, 1);

        Boleta boleta = cine.consultarBoletas().get(0);
        assertTrue(cine.buscarBoletaPorCodigo(boleta.getCodigoBoleta()).isPresent());
        assertEquals(1, cine.reporteTotalBoletasEmitidas());
        assertEquals(1, cine.consultarBoletasPorPelicula(pelicula.getCodigo()).size());
        assertEquals(1, cine.consultarBoletasPorFecha(boleta.getFechaEmision()).size());
        assertEquals(1L, cine.reporteBoletasEmitidasPorPelicula().get(pelicula.getNombre()));
    }

    @Test
    void cancelarVentaLiberaAsientoYExcluyeVentaDeReportes()
            throws FuncionDuplicadaException, SalaNoEncontradaException,
            PeliculaNoEncontradaException, SalaNoDisponibleException,
            FuncionNoEncontradaException, AsientoOcupadoException,
            SinAsientosDisponiblesException, VentaNoEncontradaException {
        cine.programarFuncion(crearFuncion(1, pelicula, sala));
        cine.registrarVenta("V1", new Cliente("100", "Luis", TipoCliente.GENERAL), 1, 1);

        cine.cancelarVenta("V1");

        assertEquals(EstadoAsiento.DISPONIBLE, sala.getAsientos().get(0).getEstadoAsiento());
        assertEquals(0, cine.reporteTotalEntradasVendidas());
        try {
            cine.cancelarVenta("inexistente");
            fail("Se esperaba una excepción porque la venta no existe");
        } catch (VentaNoEncontradaException excepcion) {
            assertNotNull(excepcion.getMessage());
        }
    }

    @Test
    void reportePeliculaMasVendidaRetornaLaDeMasVentas()
            throws FuncionDuplicadaException, SalaNoEncontradaException,
            PeliculaNoEncontradaException, SalaNoDisponibleException,
            FuncionNoEncontradaException, AsientoOcupadoException,
            SinAsientosDisponiblesException {
        cine.programarFuncion(crearFuncion(1, pelicula, sala));
        cine.programarFuncion(crearFuncion(2, peliculaAlternativa, sala));
        Cliente cliente = new Cliente("100", "Luis", TipoCliente.GENERAL);
        cine.registrarVenta("V1", cliente, 1, 1);
        cine.registrarVenta("V2", cliente, 1, 2);
        cine.registrarVenta("V3", cliente, 2, 3);

        Optional<Pelicula> masVendida = cine.reportePeliculaMasVendida();

        assertTrue(masVendida.isPresent());
        assertEquals(pelicula, masVendida.get());
    }

    @Test
    void reporteValorTotalSoloSumaVentasActivas()
            throws FuncionDuplicadaException, SalaNoEncontradaException,
            PeliculaNoEncontradaException, SalaNoDisponibleException,
            FuncionNoEncontradaException, AsientoOcupadoException,
            SinAsientosDisponiblesException, VentaNoEncontradaException {
        cine.programarFuncion(crearFuncion(1, pelicula, sala));
        Cliente adulto = new Cliente("100", "Luis", TipoCliente.GENERAL);
        cine.registrarVenta("V1", adulto, 1, 1);
        cine.registrarVenta("V2", adulto, 1, 2);

        cine.cancelarVenta("V1");

        assertEquals(10_000.0, cine.reporteValorTotalVentas(), 0.001);
    }

    private Funcion crearFuncion(int codigo, Pelicula pelicula, Sala sala) {
        return new Funcion(codigo, LocalDate.now(), LocalTime.of(18, 30), 10_000,
                EstadoFuncion.PROGRAMADA, pelicula, sala);
    }
}
