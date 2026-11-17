package cinemauq.model;


import Enums.EstadoVenta;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class Venta {
    private String codigo;
    private LocalDate fecha;
    private LocalTime hora;
    private double valorPagado;
    private EstadoVenta estadoVenta;
    private Cliente cliente;
    private Funcion funcion;
    private ArrayList<Asiento> asientos;
    private ArrayList<Boleta> boletas;

    public Venta() {
        asientos = new ArrayList<>();
        boletas = new ArrayList<>();
    }

    public Venta(String codigo, LocalDate fecha, LocalTime hora, double valorPagado,
                 EstadoVenta estadoVenta, Cliente cliente, Funcion funcion,
                 ArrayList<Asiento> asientos) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.hora = hora;
        this.valorPagado = valorPagado;
        this.estadoVenta = estadoVenta;
        this.cliente = cliente;
        this.funcion = funcion;
        this.asientos = asientos == null ? new ArrayList<>() : new ArrayList<>(asientos);
        this.boletas = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public EstadoVenta getEstadoVenta() {
        return estadoVenta;
    }

    public void setEstadoVenta(EstadoVenta estadoVenta) {
        this.estadoVenta = estadoVenta;
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

    public ArrayList<Asiento> getAsientos() {
        return new ArrayList<>(asientos);
    }

    public void setAsientos(ArrayList<Asiento> asientos) {
        this.asientos = asientos == null ? new ArrayList<>() : new ArrayList<>(asientos);
    }

    public ArrayList<Boleta> getBoletas() {
        return new ArrayList<>(boletas);
    }

    public void setBoletas(ArrayList<Boleta> boletas) {
        this.boletas = boletas == null ? new ArrayList<>() : new ArrayList<>(boletas);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta venta = (Venta) object;
        return Objects.equals(codigo, venta.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Venta{codigo='" + codigo + "', fecha=" + fecha + ", hora=" + hora
                + ", valorPagado=" + valorPagado + ", estadoVenta=" + estadoVenta
                + ", cliente=" + cliente + ", funcion=" + funcion + ", asientos=" + asientos
                + ", boletas=" + boletas.size() + "}";
    }
}
