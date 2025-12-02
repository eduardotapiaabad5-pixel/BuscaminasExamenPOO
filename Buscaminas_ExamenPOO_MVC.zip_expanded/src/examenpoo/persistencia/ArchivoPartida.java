package examenpoo.persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import examenpoo.modelo.Juego;

public class ArchivoPartida {

    public static void guardar(Juego juego, String nombre) throws IOException {
        FileOutputStream fos = new FileOutputStream(nombre);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(juego);
        oos.close();
        fos.close();
    }

    public static Juego cargar(String nombre) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(nombre);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Juego juego = (Juego) ois.readObject();
        ois.close();
        fis.close();
        return juego;
    }
}
