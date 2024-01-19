package src;

import java.util.List;

public class Principal {

	public static void main(String args[]) {
		// Creamos un nuevo grafo que usa los ficheros en el directorio del proyecto
		Grafo grafo = new Grafo("gasolineras.csv", "distancias-gasolineras.csv");
		System.out.println(grafo.toString());
		
		List<String> caminoMinimo = grafo.Dijkstra("GIJÓN-2", "AVILÉS-17");
		
		System.out.println("CAMINO: -----------------------------");
		System.out.println(caminoMinimo.toString());
		System.out.println("-----------------------------");
		
	}
}
