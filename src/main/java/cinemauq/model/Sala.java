package cinemauq.model;

import cinemauq.enums.EstadoSala;

import java.util.ArrayList;
import java.util.Objects;

public class  Sala {
    private int codigo;
    private String nombre;
    private int capacidadMaxima;
    private EstadoSala estadoSala;
    private ArrayList<Asiento> asientos;

    public Sala() {
        estadoSala = EstadoSala.DISPONIBLE;
        asientos = new ArrayList<>();
    }

    public Sala(int codigo, String nombre, int capacidadMaxima) {
        this(codigo, nombre, capacidadMaxima, EstadoSala.DISPONIBLE, crearAsientos(capacidadMaxima));
    }

    public Sala(int codigo, String nombre, int capacidadMaxima, EstadoSala estadoSala,
                ArrayList<Asiento> asientos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.estadoSala = estadoSala;
        this.asientos = asientos == null ? new ArrayList<>() : new ArrayList<>(asientos);
    }

    private static ArrayList<Asiento> crearAsientos(int capacidad) {
        if (capacidad < 0) {
            throw new IllegalArgumentException("La capacidad no puede ser negativa");
        }
        ArrayList<Asiento> asientos = new ArrayList<>();
        for (int indice = 1; indice <= capacidad; indice++) {
            asientos.add(new Asiento(indice, ((indice - 1) / 10) + 1, ((indice - 1) % 10) + 1));
        }
        return asientos;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        if (capacidadMaxima < 0) {
            throw new IllegalArgumentException("La capacidad no puede ser negativa");
        }
        this.capacidadMaxima = capacidadMaxima;
    }

    public EstadoSala getEstadoSala() {
        return estadoSala;
    }

    public void setEstadoSala(EstadoSala estadoSala) {
        this.estadoSala = estadoSala;
    }

    public ArrayList<Asiento> getAsientos() {
        return new ArrayList<>(asientos);
    }

    public void setAsientos(ArrayList<Asiento> asientos) {
        this.asientos = asientos == null ? new ArrayList<>() : new ArrayList<>(asientos);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Sala)) {
            return false;
        }
        Sala sala = (Sala) object;
        return codigo == sala.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Sala{codigo=" + codigo + ", nombre='" + nombre + "', capacidad=" + capacidadMaxima
                + ", estadoSala=" + estadoSala + ", asientos=" + asientos.size() + "}";
    }
}
