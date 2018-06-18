import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
/**
 * @author Jesus Chilca 
 *
 */
import java.util.Scanner;

import javax.crypto.spec.PSource;

public class Fichero {
	 
	public void DataSensor(String rutas, ArrayList<String> sensor1, ArrayList<String> sensor2) {
		/// fichero codificado en UTF-8
		Writer out = null;// "D:/java/Ficheros/DatosDelSensor.txt"
		String ruta = rutas;

		try { // ,true para que pueda solo añadir en la siguiente linea
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ruta), "UTF-8"));
			// Escribimos linea a linea en el fichero
			try {
				// out.write(sensor1 + "," + sensor2 + "\n");
				for (int i = 0; i < sensor1.size(); i++) {
					out.write(sensor1.get(i) + "," + sensor2.get(i) + "\n");
				}
			} catch (IOException ex) {
				System.out.println("Mensaje excepcion escritura: " + ex.getMessage());
			}
		} catch (UnsupportedEncodingException | FileNotFoundException ex2) {
			System.out.println("Mensaje error 2: " + ex2.getMessage());
		} finally {
			try {
				out.close();
			} catch (IOException ex3) {
				System.out.println("Mensaje error cierre fichero: " + ex3.getMessage());
			}
		}
	}

	public ArrayList<String> LecturaData() {
		String ruta = "D:/java/Ficheros/DatosDelSensor.txt";
		File fichero = new File(ruta);
		ArrayList<String> d1 = new ArrayList<>();
		ArrayList<String> d2 = new ArrayList<>();
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		Scanner s = null;
		// if (archivo.exists()) {
		try {
			// Leemos el contenido del fichero
			System.out.println("... Leemos el contenido del fichero ...");
			s = new Scanner(fichero);

			// Leemos linea a linea el fichero
			while (s.hasNextLine()) {
				String linea = s.nextLine(); // Guardamos la linea en un String
				// System.out.println(linea); // Imprimimos la linea
				String[] parts = linea.split(",");
				String part1 = parts[0]; // 123
				String part2 = parts[1]; // 654321
				d1.add(part1);
				d2.add(part2);
				System.out.println(part1 + "-->parte 1");
				System.out.println(part2 + "-->parte 2");
			}
			System.out.println(d1.size() + " ---> tama");
		} catch (Exception ex) {
			System.out.println("Mensaje: " + ex.getMessage());
		} finally {
			// Cerramos el fichero tanto si la lectura ha sido correcta o no
			try {
				if (s != null)
					s.close();
			} catch (Exception ex2) {
				System.out.println("Mensaje 2: " + ex2.getMessage());
			}
			return d1;
		}
		// } else {
		// System.out.println("El archivo no existe");
		// }
		// return null;

	}

	public static void main(String[] args) {
		Fichero n = new Fichero();
		n.LecturaData();
	}

}
