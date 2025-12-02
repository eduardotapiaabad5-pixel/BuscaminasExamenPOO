package examenpoo.modelo;

import java.io.Serializable;
import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;

import examenpoo.excepciones.CasillaYaDescubiertaException;

public class Tablero implements Serializable {

    public static final int FILAS = 10;
    public static final int COLUMNAS = 10;
    public static final int MINAS = 10;

    private Casilla[][] casillas;
    private boolean preparado;

    public Tablero() {
        casillas = new Casilla[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                casillas[i][j] = new Casilla();
            }
        }
        preparado = false;
    }

    public Casilla getCasilla(int f, int c) {
        return casillas[f][c];
    }

    public boolean isPreparado() {
        return preparado;
    }

    public void prepararTablero(int filaInicial, int colInicial) {
        if (preparado) return;

        colocarMinas(filaInicial, colInicial);
        calcularMinasAlrededor();
        preparado = true;
    }

    private void colocarMinas(int filaInicial, int colInicial) {
        Random r = new Random();
        int contador = 0;
        while (contador < MINAS) {
            int f = r.nextInt(FILAS);
            int c = r.nextInt(COLUMNAS);
            if (f == filaInicial && c == colInicial) continue;
            if (!casillas[f][c].isTieneMina()) {
                casillas[f][c].setTieneMina(true);
                contador++;
            }
        }
    }

    private void calcularMinasAlrededor() {
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                if (!casillas[f][c].isTieneMina()) {
                    int conta = contarMinasVecinas(f, c);
                    casillas[f][c].setMinasAlrededor(conta);
                }
            }
        }
    }

    private int contarMinasVecinas(int f, int c) {
        int total = 0;
        for (int df = -1; df <= 1; df++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nf = f + df;
                int nc = c + dc;
                if (df == 0 && dc == 0) continue;
                if (estaDentro(nf, nc) && casillas[nf][nc].isTieneMina()) {
                    total++;
                }
            }
        }
        return total;
    }

    private boolean estaDentro(int f, int c) {
        return f >= 0 && f < FILAS && c >= 0 && c < COLUMNAS;
    }

    public void marcar(int f, int c) {
        if (!estaDentro(f, c)) return;
        Casilla cas = casillas[f][c];
        if (!cas.isDescubierta()) {
            cas.cambiarMarca();
        }
    }

    public void revelar(int f, int c) throws CasillaYaDescubiertaException {
        if (!estaDentro(f, c)) return;
        Casilla cas = casillas[f][c];

        if (cas.isDescubierta()) {
            throw new CasillaYaDescubiertaException("La casilla ya estaba descubierta");
        }

        if (cas.isMarcada()) {
            return;
        }

        cas.setDescubierta(true);

        if (cas.isTieneMina()) {
            return;
        }

        if (cas.getMinasAlrededor() == 0) {
            revelarVacias(f, c);
        }
    }

    private void revelarVacias(int fila, int col) {
        Queue<Coordenada> cola = new LinkedList<>();
        cola.add(new Coordenada(fila, col));

        while (!cola.isEmpty()) {
            Coordenada actual = cola.poll();
            int f = actual.getFila();
            int c = actual.getColumna();

            for (int df = -1; df <= 1; df++) {
                for (int dc = -1; dc <= 1; dc++) {
                    int nf = f + df;
                    int nc = c + dc;
                    if (!estaDentro(nf, nc)) continue;
                    Casilla vecino = casillas[nf][nc];
                    if (!vecino.isDescubierta() && !vecino.isMarcada() && !vecino.isTieneMina()) {
                        vecino.setDescubierta(true);
                        if (vecino.getMinasAlrededor() == 0) {
                            cola.add(new Coordenada(nf, nc));
                        }
                    }
                }
            }
        }
    }

    public boolean esVictoria() {
        int descubiertas = 0;
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                if (casillas[f][c].isDescubierta()) {
                    descubiertas++;
                }
            }
        }
        int totalSeguras = FILAS * COLUMNAS - MINAS;
        return descubiertas == totalSeguras;
    }

    public void revelarTodasMinas() {
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                if (casillas[f][c].isTieneMina()) {
                    casillas[f][c].setDescubierta(true);
                }
            }
        }
    }
}
