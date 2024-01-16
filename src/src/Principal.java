package src;

public class Principal {

	public static void main(String args[]) {
		// Creamos un nuevo grafo que usa los ficheros en el directorio del proyecto
		Grafo grafo = new Grafo("gasolineras.csv", "distancias-gasolineras.csv");
		System.out.println(grafo.toString());
	}
}
