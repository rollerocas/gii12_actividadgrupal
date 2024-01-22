package src;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Principal {
	// Nombres de ficheros a cargar ubicados en el directorio raiz del proyecto
	public final static String FICHERO_NODOS = "gasolineras.csv";
	public final static String FICHERO_ARISTAS = "distancias-gasolineras.csv";

	public static void main(String args[]) {

		// Creamos un nuevo grafo que usa los ficheros en el directorio del proyecto
		Grafo grafo = crearGrafo();

		// Las visualizamos en modo tabla
		visualizarTablas(grafo);

		// Visualizamos en modo listas de adyacencias
		visualizarListasAdyacencia(grafo);

		// Calculamos el vector de distancias
		vectorDistanciasMinimas(grafo, "AVILÉS-7");

		// Calculamos el camino minimo
		calcularCaminoMinimo(grafo, "AVILÉS-7", "CORVERA DE ASTURIAS-28");

		// Borramos un nodo del camino
		borrarNodo(grafo, "AVILÉS-10");
		// Y Calculamos de nuevo el camino minimo para ver que cambia
		calcularCaminoMinimo(grafo, "AVILÉS-7", "CORVERA DE ASTURIAS-28");

		// Borramos una arista
		borrarArista(grafo, "AVILÉS-19", "CORVERA DE ASTURIAS-28");
		// Y Calculamos de nuevo el camino minimo para ver que cambia
		calcularCaminoMinimo(grafo, "AVILÉS-7", "CORVERA DE ASTURIAS-28");
	}

	/**
	 * Crea un nuevo grafo a la vez que muestra mensajes en consola
	 * 
	 * @return Grafo
	 */
	public static Grafo crearGrafo() {
		System.out.println(
				"\n──────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.println("\nCREANDO GRAFO:");
		try {
			Grafo grafo = new Grafo(FICHERO_NODOS, FICHERO_ARISTAS);
			// Ordenamos las listas
			grafo.ordenaNodos();
			grafo.ordenaAristas();
			System.out.println("\nGrafo creado correctamente");
			return grafo;
		} catch (Exception e) {
			System.out.println("\nNo se ha podido crear el grafo dados los ficheros.");
		}
		return null;
	}

	/**
	 * Muestra el grafo como su deficinicion formal de lista de nodos y lista
	 * aristas ordenada
	 * 
	 * @param grafo
	 */
	public static void visualizarTablas(Grafo grafo) {
		System.out.println(
				"\n──────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.println("\nVISTA COMO TABLAS:");
		System.out.println(grafo.toStringTable());
	}

	/**
	 * Muestra el grafo como listas de adyacencias para cada uno de sus nodos
	 * 
	 * @param grafo
	 */
	public static void visualizarListasAdyacencia(Grafo grafo) {
		System.out.println(
				"\n──────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.println("\nLISTAS DE ADYACENCIAS:");
		System.out.println(grafo.toString());
	}

	/**
	 * Calcula el camino minimo a la vez que muestra mensajes sobre su resultado
	 * 
	 * @param grafo
	 * @param origen
	 * @param destino
	 */
	public static void calcularCaminoMinimo(Grafo grafo, String origen, String destino) {
		System.out.println(
				"\n──────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		List<String> caminoMinimo = grafo.Dijkstra(origen, destino);
		if (caminoMinimo == null) {
			System.out.println("\nCAMINO: No se ha encontrado un camino entre ambas gasolineras.");
		} else {
			System.out.println("\nCAMINO:");
			System.out.println(caminoMinimo.toString());
		}
	}

	/**
	 * Calcula y muestra por consola el vector de distancias minimas partiendo de
	 * una clave
	 * 
	 * @param grafo
	 * @param clave
	 */
	public static void vectorDistanciasMinimas(Grafo grafo, String clave) {
		System.out.println(
				"\n──────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		List<Double> vectorDistanciasMinimas = grafo.Dijkstra(clave);
		System.out.println("\nVECTOR:");
		// paso las distancias a BigDecimal para no perder precision
		List<BigDecimal> vectorDistanciasMinimasDecimal = new ArrayList<>();
		for (Double double1 : vectorDistanciasMinimas) {
			vectorDistanciasMinimasDecimal.add(new BigDecimal(double1, MathContext.DECIMAL64)
					.setScale(2, RoundingMode.HALF_EVEN).stripTrailingZeros());
		}
		System.out.println(vectorDistanciasMinimasDecimal.toString());
	}

	/**
	 * Borra un nodo del grafo a la vez que muestra su resultado por consola
	 * 
	 * @param grafo
	 * @param clave
	 */
	public static void borrarNodo(Grafo grafo, String clave) {
		System.out.println(
				"\n──────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.println("\nBORRANDO NODO:");
		boolean resultado = grafo.borrarNodo("AVILÉS-10");
		if (resultado) {
			System.out.println("Nodo (" + clave + ") borrado correctamente");
		} else {
			System.out.println("No se ha podido borrar el nodo (" + clave + ")");
		}
	}

	/**
	 * Borra una arista del grafo a la vez que muestra su resultado por consola
	 * 
	 * @param grafo
	 * @param origen
	 * @param destino
	 */
	public static void borrarArista(Grafo grafo, String origen, String destino) {
		System.out.println(
				"\n──────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.println("\nBORRANDO ARISTA:");
		boolean resultado = grafo.borrarArista("AVILÉS-19", "CORVERA DE ASTURIAS-28");
		if (resultado) {
			System.out.println("Arista (" + origen + " - " + destino + ") borrado correctamente");
		} else {
			System.out.println("No se ha podido borrar la arista (" + origen + " - " + destino + ")");
		}
	}

	/**
	 * Metodo auxiliar usado en las clases para formatear en columnas las salidas de
	 * consola (toString) a un determenido ancho, dado una cadena y una longitud
	 * maxima devuelve el string rellenado con espacios o acortado con puntos
	 * suspensivos
	 * 
	 * @param inputString
	 * @param maxLength
	 * @return String
	 */
	public static String paddingRigthWithSpaces(String inputString, int maxLength) {
		int totalSpacesToAdd = maxLength - inputString.length();
		StringBuilder sb = new StringBuilder();
		if (totalSpacesToAdd < 0) {
			sb.append(inputString.substring(0, maxLength - 3));
			sb.append("...");
		} else {
			sb.append(inputString);
			for (int i = 0; i < totalSpacesToAdd; i++) {
				sb.append(' ');
			}
		}
		return sb.toString();
	}
}
