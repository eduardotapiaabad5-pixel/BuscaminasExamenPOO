package examenpoo.vista;

import java.util.Scanner;

import examenpoo.modelo.Casilla;
import examenpoo.modelo.Tablero;

public class ConsolaView {

    private Scanner scanner;

    public ConsolaView() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenuPrincipal() {
        System.out.println("===== BUSCAMINAS EXAMEN POO =====");
        System.out.println("1. Nueva partida");
        System.out.println("2. Cargar partida");
        System.out.println("3. Salir");
        System.out.print("Elija una opcion: ");
    }

    public int leerOpcionMenu() {
        String linea = scanner.nextLine();
        try {
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void mostrarTablero(Tablero tablero) {
        System.out.print("   ");
        for (int c = 0; c < Tablero.COLUMNAS; c++) {
            System.out.print((c + 1) + " ");
        }
        System.out.println();
        for (int f = 0; f < Tablero.FILAS; f++) {
            char letra = (char) ('A' + f);
            System.out.print(letra + "  ");
            for (int c = 0; c < Tablero.COLUMNAS; c++) {
                Casilla cas = tablero.getCasilla(f, c);
                char simbolo;
                if (!cas.isDescubierta()) {
                    if (cas.isMarcada()) {
                        simbolo = 'F';
                    } else {
                        simbolo = '#';
                    }
                } else {
                    if (cas.isTieneMina()) {
                        simbolo = 'X';
                    } else {
                        int n = cas.getMinasAlrededor();
                        if (n == 0) {
                            simbolo = ' ';
                        } else {
                            simbolo = (char) ('0' + n);
                        }
                    }
                }
                System.out.print(simbolo + " ");
            }
            System.out.println();
        }
    }

    public String leerLinea() {
        System.out.print("Comando (R A1, M B3, G para guardar, X para salir): ");
        return scanner.nextLine();
    }

    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }

    public void mostrarError(String msg) {
        System.out.println("[ERROR] " + msg);
    }
}
