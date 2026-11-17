package cinemauq.model;

import java.util.Objects;

public class OcupacionFuncion {
    private int cantidadDisponible;
    private int cantidadOcupada;
    private double porcentajeOcupacion;

    public OcupacionFuncion(int cantidadDisponible, int cantidadOcupada, double porcentajeOcupacion) {
        this.cantidadDisponible = cantidadDisponible;
        this.cantidadOcupada = cantidadOcupada;
        this.porcentajeOcupacion = porcentajeOcupacion;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public int getCantidadOcupada() {
        return cantidadOcupada;
    }

    public void setCantidadOcupada(int cantidadOcupada) {
        this.cantidadOcupada = cantidadOcupada;
    }

    public double getPorcentajeOcupacion() {
        return porcentajeOcupacion;
    }

    public void setPorcentajeOcupacion(double porcentajeOcupacion) {
        this.porcentajeOcupacion = porcentajeOcupacion;
    }

    @Override
    public String toString() {
        return "OcupacionFuncion{cantidadDisponible=" + cantidadDisponible
                + ", cantidadOcupada=" + cantidadOcupada
                + ", porcentajeOcupacion=" + porcentajeOcupacion + "%}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof OcupacionFuncion)) {
            return false;
        }
        OcupacionFuncion that = (OcupacionFuncion) object;
        return cantidadDisponible == that.cantidadDisponible
                && cantidadOcupada == that.cantidadOcupada
                && Double.compare(porcentajeOcupacion, that.porcentajeOcupacion) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cantidadDisponible, cantidadOcupada, porcentajeOcupacion);
    }
}
