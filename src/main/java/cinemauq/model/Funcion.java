package cinemauq.model;

import cinemauq.enums.EstadoFuncion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Funcion {
    private int codigo;
    private LocalDate fecha;
    private LocalTime hora;
    private double precioBase;
    private EstadoFuncion estadoFuncion;
    private Pelicula pelicula;
    private Sala sala;

    public Funcion() {
    }

    public Funcion(int codigo, LocalDate fecha, LocalTime hora, double precioBase,
                   EstadoFuncion estadoFuncion, Pelicula pelicula, Sala sala) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.hora = hora;
        this.precioBase = precioBase;
        this.estadoFuncion = estadoFuncion;
        this.pelicula = pelicula;
        this.sala = sala;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
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

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public EstadoFuncion getEstadoFuncion() {
        return estadoFuncion;
    }

    public void setEstadoFuncion(EstadoFuncion estadoFuncion) {
        this.estadoFuncion = estadoFuncion;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Funcion)) {
            return false;
        }
        Funcion funcion = (Funcion) object;
        return codigo == funcion.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Funcion{codigo=" + codigo + ", fecha=" + fecha + ", hora=" + hora
                + ", precioBase=" + precioBase + ", estadoFuncion=" + estadoFuncion
                + ", pelicula=" + pelicula + ", sala=" + sala + "}";
    }
}
