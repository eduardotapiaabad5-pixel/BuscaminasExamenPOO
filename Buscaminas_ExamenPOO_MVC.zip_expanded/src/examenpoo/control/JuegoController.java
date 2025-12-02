package examenpoo.control;

import examenpoo.modelo.Juego;
import examenpoo.modelo.Tablero;
import examenpoo.excepciones.CasillaYaDescubiertaException;
import examenpoo.excepciones.CoordenadaInvalidaException;
import examenpoo.persistencia.ArchivoPartida;
import examenpoo.vista.ConsolaView;

public class JuegoController {

    private Juego juego;
    private ConsolaView vista;
    private boolean primerMovimiento;

    public JuegoController() {
        vista = new ConsolaView();
        juego = new Juego();
        primerMovimiento = true;
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            vista.mostrarMenuPrincipal();
            int op = vista.leerOpcionMenu();
            switch (op) {
                case 1:
                    juego = new Juego();
                    primerMovimiento = true;
                    jugarPartida();
                    break;
                case 2:
                    cargarPartida();
                    break;
                case 3:
                    salir = true;
                    vista.mostrarMensaje("Saliendo del juego...");
                    break;
                default:
                    vista.mostrarError("Opcion invalida");
            }
        }
    }

    private void cargarPartida() {
        vista.mostrarMensaje("Nombre del archivo a cargar (ej: partida.dat): ");
        String nombre = vista.leerLinea();
        try {
            juego = ArchivoPartida.cargar(nombre);
            primerMovimiento = false;
            vista.mostrarMensaje("Partida cargada.");
            jugarPartida();
        } catch (Exception e) {
            vista.mostrarError("No se pudo cargar la partida: " + e.getMessage());
        }
    }

    private void jugarPartida() {
        while (!juego.isTerminado()) {
            Tablero tablero = juego.getTablero();
            vista.mostrarTablero(tablero);
            String linea = vista.leerLinea();
            try {
                if (linea.equalsIgnoreCase("X")) {
                    juego.setTerminado(true);
                    break;
                }
                if (linea.equalsIgnoreCase("G")) {
                    vista.mostrarMensaje("Nombre del archivo para guardar (ej: partida.dat): ");
                    String nombre = vista.leerLinea();
                    ArchivoPartida.guardar(juego, nombre);
                    vista.mostrarMensaje("Partida guardada.");
                    continue;
                }

                String[] partes = linea.split(" ");
                if (partes.length < 2) {
                    throw new CoordenadaInvalidaException("Formato incorrecto");
                }
                String accion = partes[0];
                String coord = partes[1];

                int fila = obtenerFila(coord);
                int col = obtenerColumna(coord);

                if (fila < 0 || col < 0 || fila >= Tablero.FILAS || col >= Tablero.COLUMNAS) {
                    throw new CoordenadaInvalidaException("Coordenada fuera de rango");
                }

                if (accion.equalsIgnoreCase("M")) {
                    tablero.marcar(fila, col);
                } else if (accion.equalsIgnoreCase("R")) {
                    if (primerMovimiento && !tablero.isPreparado()) {
                        tablero.prepararTablero(fila, col);
                        primerMovimiento = false;
                    }
                    try {
                        tablero.revelar(fila, col);
                    } catch (CasillaYaDescubiertaException e) {
                        vista.mostrarError(e.getMessage());
                    }
                    if (tablero.getCasilla(fila, col).isTieneMina()) {
                        tablero.revelarTodasMinas();
                        vista.mostrarTablero(tablero);
                        vista.mostrarMensaje("Pisaste una mina. Has perdido.");
                        juego.setTerminado(true);
                    } else if (tablero.esVictoria()) {
                        vista.mostrarTablero(tablero);
                        vista.mostrarMensaje("¡Felicidades, ganaste!");
                        juego.setTerminado(true);
                    }
                } else {
                    vista.mostrarError("Accion desconocida");
                }
            } catch (CoordenadaInvalidaException e) {
                vista.mostrarError(e.getMessage());
            } catch (Exception e) {
                vista.mostrarError("Error: " + e.getMessage());
            }
        }
    }

    private int obtenerFila(String coord) throws CoordenadaInvalidaException {
        coord = coord.toUpperCase().trim();
        if (coord.length() < 2) {
            throw new CoordenadaInvalidaException("Coordenada muy corta");
        }
        char letra = coord.charAt(0);
        if (letra < 'A' || letra > 'J') {
            throw new CoordenadaInvalidaException("Fila invalida (A-J)");
        }
        return letra - 'A';
    }

    private int obtenerColumna(String coord) throws CoordenadaInvalidaException {
        coord = coord.toUpperCase().trim();
        String num = coord.substring(1);
        try {
            int n = Integer.parseInt(num);
            if (n < 1 || n > 10) {
                throw new CoordenadaInvalidaException("Columna invalida (1-10)");
            }
            return n - 1;
        } catch (NumberFormatException e) {
            throw new CoordenadaInvalidaException("Columna invalida");
        }
    }
}
