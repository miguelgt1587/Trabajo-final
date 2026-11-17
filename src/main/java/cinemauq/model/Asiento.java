package cinemauq.model;

import java.util.Objects;
import Enums.EstadoAsiento;

    public class Asiento {
        private int codigo;
        private int fila;
        private int numero;
        private EstadoAsiento estadoAsiento;

        public Asiento() {
            estadoAsiento = EstadoAsiento.DISPONIBLE;
        }

        public Asiento(int codigo, int fila, int numero) {
            this(codigo, fila, numero, EstadoAsiento.DISPONIBLE);
        }

        public Asiento(int codigo, int fila, int numero, EstadoAsiento estadoAsiento) {
            this.codigo = codigo;
            this.fila = fila;
            this.numero = numero;
            this.estadoAsiento = estadoAsiento;
        }

        public int getCodigo() {
            return codigo;
        }

        public void setCodigo(int codigo) {
            this.codigo = codigo;
        }

        public int getFila() {
            return fila;
        }

        public void setFila(int fila) {
            this.fila = fila;
        }

        public int getNumero() {
            return numero;
        }

        public void setNumero(int numero) {
            this.numero = numero;
        }

        public EstadoAsiento getEstadoAsiento() {
            return estadoAsiento;
        }

        public void setEstadoAsiento(EstadoAsiento estadoAsiento) {
            this.estadoAsiento = estadoAsiento;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Asiento)) {
                return false;
            }
            Asiento asiento = (Asiento) object;
            return codigo == asiento.codigo;
        }

        @Override
        public int hashCode() {
            return Objects.hash(codigo);
        }

        @Override
        public String toString() {
            return "Asiento{codigo=" + codigo + ", fila=" + fila + ", numero=" + numero
                    + ", estadoAsiento=" + estadoAsiento + "}";
        }
    }

}
