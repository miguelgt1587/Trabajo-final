package cinemauq.model;

import java.util.Objects;

public class DisponibilidadFuncion {
    private int asientosDisponibles;
    private int asientosOcupados;
    private int totalAsientos;

    public DisponibilidadFuncion(int asientosDisponibles, int asientosOcupados, int totalAsientos) {
        this.asientosDisponibles = asientosDisponibles;
        this.asientosOcupados = asientosOcupados;
        this.totalAsientos = totalAsientos;
    }

    public int getAsientosDisponibles() {
        return asientosDisponibles;
    }

    public void setAsientosDisponibles(int asientosDisponibles) {
        this.asientosDisponibles = asientosDisponibles;
    }

    public int getAsientosOcupados() {
        return asientosOcupados;
    }

    public void setAsientosOcupados(int asientosOcupados) {
        this.asientosOcupados = asientosOcupados;
    }

    public int getTotalAsientos() {
        return totalAsientos;
    }

    public void setTotalAsientos(int totalAsientos) {
        this.totalAsientos = totalAsientos;
    }

    @Override
    public String toString() {
        return "DisponibilidadFuncion{asientosDisponibles=" + asientosDisponibles
                + ", asientosOcupados=" + asientosOcupados + ", totalAsientos=" + totalAsientos + "}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DisponibilidadFuncion)) {
            return false;
        }
        DisponibilidadFuncion that = (DisponibilidadFuncion) object;
        return asientosDisponibles == that.asientosDisponibles
                && asientosOcupados == that.asientosOcupados
                && totalAsientos == that.totalAsientos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(asientosDisponibles, asientosOcupados, totalAsientos);
    }
}
