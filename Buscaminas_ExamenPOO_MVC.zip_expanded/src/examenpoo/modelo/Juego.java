package examenpoo.modelo;

import java.io.Serializable;

public class Juego implements Serializable {

    private Tablero tablero;
    private boolean terminado;
    private boolean victoria;

    public Juego() {
        tablero = new Tablero();
        terminado = false;
        victoria = false;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public boolean isVictoria() {
        return victoria;
    }

    public void setVictoria(boolean victoria) {
        this.victoria = victoria;
    }
}
