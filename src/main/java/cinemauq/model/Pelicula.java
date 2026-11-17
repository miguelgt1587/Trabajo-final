package cinemauq.model;

import Enums.Clasificacion;
import Enums.EstadoPelicula;
import Enums.Genero;

public class Pelicula{
    private int codigo;
    private String nombre;
    private Genero genero;
    private int duracionMinutos;
    private Clasificacion clasificacion;
    private EstadoPelicula estadoPelicula;

    /**
     * Metodo constructor de la clase Model.Pelicula
     * @param codigo de la pelicula
     * @param nombre de la pelicula
     * @param genero de la pelicula
     * @param duracionMinutos de la pelicula
     * @param clasificacion de la pelicula
     * @param estadoPelicula de la pelicula
     */

    public Pelicula(int codigo, String nombre, Genero genero, int duracionMinutos, Clasificacion clasificacion, EstadoPelicula estadoPelicula) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;
        this.estadoPelicula = estadoPelicula;
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

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public EstadoPelicula getEstadoPelicula() {
        return estadoPelicula;
    }

    public void setEstadoPelicula(EstadoPelicula estadoPelicula) {
        this.estadoPelicula = estadoPelicula;
    }

    @Override
    public String toString() {
        return "Model.Pelicula{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", genero='" + genero + '\'' +
                ", duracionMinutos=" + duracionMinutos +
                ", clasificacion=" + clasificacion +
                ", estadoPelicula=" + estadoPelicula +
                '}';
    }
}


