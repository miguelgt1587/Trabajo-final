package cinemauq.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Boleta {

    private String codigoBoleta;
    private Venta venta;
    private Cliente cliente;
    private Funcion funcion;
    private Asiento asiento;
    private LocalDate fechaEmision;
    private LocalTime horaEmision;

    public Boleta() {
    }

    public Boleta(String codigoBoleta, Venta venta, Cliente cliente, Funcion funcion,
                  Asiento asiento, LocalDate fechaEmision, LocalTime horaEmision) {
        this.codigoBoleta = codigoBoleta;
        this.venta = venta;
        this.cliente = cliente;
        this.funcion = funcion;
        this.asiento = asiento;
        this.fechaEmision = fechaEmision;
        this.horaEmision = horaEmision;
    }

    public String getCodigoBoleta() {
        return codigoBoleta;
    }

    public void setCodigoBoleta(String codigoBoleta) {
        this.codigoBoleta = codigoBoleta;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalTime getHoraEmision() {
        return horaEmision;
    }

    public void setHoraEmision(LocalTime horaEmision) {
        this.horaEmision = horaEmision;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Boleta)) {
            return false;
        }
        Boleta boleta = (Boleta) object;
        return Objects.equals(codigoBoleta, boleta.codigoBoleta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoBoleta);
    }

    @Override
    public String toString() {
        String codigoVenta = null;
        if (venta != null) {
            codigoVenta = venta.getCodigo();
        }
        return "Model.Boleta{codigoBoleta='" + codigoBoleta + "', codigoVenta='" + codigoVenta
                + "', cliente=" + cliente + ", funcion=" + funcion + ", asiento=" + asiento
                + ", fechaEmision=" + fechaEmision + ", horaEmision=" + horaEmision + "}";
    }
}

