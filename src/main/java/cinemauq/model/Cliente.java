package cinemauq.model;

import Enums.TipoCliente;

public class Cliente {
    private String documento;
    private String nombre;
    private TipoCliente tipoCliente;

    /**
     * Metodo constructor de la clase Model.Cliente
     * @param documento del cliente
     * @param nombre del cliente
     * @param tipoCliente del cliente
     */
    public Cliente(String documento, String nombre, TipoCliente tipoCliente) {
        this.documento = documento;
        this.nombre = nombre;
        this.tipoCliente = tipoCliente;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    @Override
    public String toString() {
        return "Model.Cliente{" +
                "documento='" + documento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tipoCliente=" + tipoCliente +
                '}';
    }
}
