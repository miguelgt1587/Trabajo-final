package cinemauq.model;

import Enums.TipoCliente;

public class Tarifa {

    private TipoCliente tipoCliente;
    private double porcentajeDescuento;

    public Tarifa() {
    }

    public Tarifa(TipoCliente tipoCliente, double porcentajeDescuento) {
        this.tipoCliente = tipoCliente;
        setPorcentajeDescuento(porcentajeDescuento);
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        if (!Double.isFinite(porcentajeDescuento) || porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100 por ciento");
        }
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public String toString() {
        return "Tarifa{tipoCliente=" + tipoCliente
                + ", porcentajeDescuento=" + porcentajeDescuento + "%}";
    }
}
