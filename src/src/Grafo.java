package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Grafo {

	private LinkedList<NodoGrafo> nodos;
	private LinkedList<Arista> aristas;

	/**
	 * Consutruye el grafo dado los nombres de los archivos ubicados en el
	 * directorio del proyecto
	 * 
	 * @param nombreFicheroNodos
	 * @param nombreFicheroAristas
	 */
	public Grafo(String nombreFicheroNodos, String nombreFicheroAristas) {

		// variables para leer los archivos
		String directorioProyecto = System.getProperty("user.dir");
		String separador = System.getProperty("file.separator");
		String pathFicheroNodos = directorioProyecto + separador + nombreFicheroNodos;
		String pathFicheroAristas = directorioProyecto + separador + nombreFicheroAristas;

		// Inicializacion de variables
		this.nodos = new LinkedList<NodoGrafo>();
		this.aristas = new LinkedList<Arista>();

		// Lectura y construccion de los nodos con los datos de las gasolineras
		try (FileReader fr = new FileReader(pathFicheroNodos, StandardCharsets.ISO_8859_1)) {
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] datosNodo = linea.split(";");
				Gasolinera gasolinera = new Gasolinera(datosNodo);
				this.insertarNodo(gasolinera);
			}
		} catch (Exception e) {
			System.out.println("Error en la carga de nodos desde el archivo " + pathFicheroNodos);
			e.printStackTrace();
		}

		// Lectura y construccion de las aristas con los datos de las distancias
		try (FileReader fr = new FileReader(pathFicheroAristas, StandardCharsets.ISO_8859_1)) {
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] datosArista = linea.split(";");
				Arista arista = new Arista(datosArista);
				this.insertarAristaObject(arista);
			}
		} catch (Exception e) {
			System.out.println("Error en la carga de aristas desde el archivo " + pathFicheroAristas);
			e.printStackTrace();
		}
	}

	/**
	 * Inserta el nodo pasado por parámetro si no pertenece al grafo, y devuelve
	 * true. En caso contrario, no se realiza la inserción y devuelve false.
	 * 
	 * @param nodo
	 * @return boolean
	 */
	public boolean insertarNodo(NodoGrafo nodo) {
		// no existir previamente
		if (this.existeNodo(nodo.getClave())) {
			return false;
		}
		// si no existe, lo aniado
		this.nodos.add(nodo);
		return true;
	}

	/**
	 * Borra el nodo cuya clave se pasa por parámetro si existe en el grafo, y
	 * devuelve true. Si no existe, devuelve false.
	 * 
	 * @param clave
	 * @return
	 */
	public boolean borrarNodo(String clave) {
		// debe existir previamente
		if (!this.existeNodo(clave)) {
			return false;
		}
		// borramos sus aristas
		Iterator<Arista> iterator = this.aristas.iterator();
		while (iterator.hasNext()) {
			Arista arista = iterator.next();
			if (arista.contieneClave(clave)) {
				iterator.remove();
			}
		}
		// obtengo su nodo
		NodoGrafo nodoParaBorrar = this.buscaNodo(clave);
		// borramos su nodo
		if (nodoParaBorrar != null) {
			this.nodos.remove(nodoParaBorrar);
			return true;
		}
		return false;
	}

	/**
	 * Devuelve true si el nodo cuya clave se pasa por parámetro existe en el grafo,
	 * y false en caso contrario.
	 * 
	 * @param clave
	 * @return
	 */
	public boolean existeNodo(String clave) {
		// recorro la lista de nodos buscando su clave
		Iterator<NodoGrafo> iterator = this.nodos.iterator();
		while (iterator.hasNext()) {
			NodoGrafo nodo = iterator.next();
			if (nodo.getClave().equals(clave)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Busca un nodo en el grafo y lo devuelve si lo encuentra, si no lo encuentra
	 * devuelve null.
	 * 
	 * @param clave
	 * @return NodoGrafo
	 */
	public NodoGrafo buscaNodo(String clave) {
		// recorro la lista de nodos buscando su clave
		for (Iterator<NodoGrafo> iterator = this.nodos.iterator(); iterator.hasNext();) {
			NodoGrafo nodo = iterator.next();
			if (nodo.getClave().equals(clave)) {
				return nodo;
			}
		}
		return null;
	}

	/**
	 * Devuelve el número de nodos del grafo.
	 * 
	 * @return
	 */
	public int numeroNodos() {
		return this.nodos.size();
	}

	/**
	 * Si existen las dos claves pasadas por parámetro, y la arista no existe
	 * previamente en el grafo, inserta la arista entre los nodos del grafo
	 * correspondientes a ambas claves con el peso pasado por parámetro y devuelve
	 * true. En caso contrario, no se realiza la inserción y devuelve false.
	 * 
	 * @param clave1
	 * @param clave2
	 * @param peso
	 * @return boolean
	 */
	public boolean insertarArista(String clave1, String clave2, Double peso) {
		// deben existir los nodos
		if (!this.existeNodo(clave1) || !this.existeNodo(clave2)) {
			return false;
		}
		// no debe existir la arista previamente
		if (this.existeArista(clave1, clave2)) {
			return false;
		}
		// creo la nueva arista
		Arista aristaParaInsertar = new Arista(clave1, clave2, peso);
		// insertamos la arista
		this.aristas.add(aristaParaInsertar);
		return true;
	}

	/**
	 * Metodo auxiliar para mantener modelo POO a la vez que se cumple con la
	 * implementacion pedida en el enunciado
	 * 
	 * @param arista
	 * @return boolean
	 */
	public boolean insertarAristaObject(Arista arista) {
		return this.insertarArista(arista.getOrigen(), arista.getDestino(), arista.getPeso());
	}

	/**
	 * Borra la arista definida por las dos claves pasadas por parámetro si existe
	 * en el grafo y devuelve true. Si no existiera, devuelve false.
	 * 
	 * @param clave1
	 * @param clave2
	 * @return boolean
	 */
	public boolean borrarArista(String clave1, String clave2) {
		// deben existir los nodos
		if (!this.existeNodo(clave1) || !this.existeNodo(clave2)) {
			return false;
		}
		// debe existir la arista previamente
		if (!this.existeArista(clave1, clave2)) {
			return false;
		}
		// borrar la arista
		Arista aristaParaBorrar = this.buscaArista(clave1, clave2);
		if (aristaParaBorrar != null) {
			this.aristas.remove(aristaParaBorrar);
			return true;
		}
		return false;
	}

	/**
	 * Devuelve true si la arista cuyos extremos se pasan por parámetro existe en el
	 * grafo, y false en caso contrario.
	 * 
	 * @param clave1
	 * @param clave2
	 * @return boolean
	 */
	public boolean existeArista(String clave1, String clave2) {
		Arista aristaParaBuscar = new Arista(clave1, clave2);
		// recorro la lista de aristas buscando sus claves
		Iterator<Arista> iterator = this.aristas.iterator();
		while (iterator.hasNext()) {
			Arista arista = iterator.next();
			if (arista.tieneMismosVertices(aristaParaBuscar)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Busca una arista en el grafo y la devuelve si lo encuentra, si no lo
	 * encuentra devuelve null.
	 * 
	 * @param clave
	 * @return NodoGrafo
	 */
	public Arista buscaArista(String clave1, String clave2) {
		Arista aristaParaBuscar = new Arista(clave1, clave2);
		// recorro la lista de nodos buscando su clave
		for (Iterator<Arista> iterator = this.aristas.iterator(); iterator.hasNext();) {
			Arista arista = iterator.next();
			if (arista.tieneMismosVertices(aristaParaBuscar)) {
				return arista;
			}
		}
		return null;
	}

	/**
	 * Devuelve el número de aristas del grafo.
	 * 
	 * @return int
	 */
	public int numeroAristas() {
		return this.aristas.size();
	}

	/**
	 * Devuelve el listado de nodos y el listado de aristas en formato tabla
	 * 
	 * @return String
	 */
	public String toStringTable() {
		StringBuilder sb = new StringBuilder();
		sb.append("NODOS:\n");
		sb.append(
				"──────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n");
		sb.append(
				"| CLAVE                   | ROTULO               | DIRECCION                    | MUNICIPIO    | LOCALIDAD       |\n");
		sb.append(
				"|─────────────────────────├──────────────────────|──────────────────────────────|──────────────|─────────────────|\n");
		for (Iterator<NodoGrafo> iterator = this.nodos.iterator(); iterator.hasNext();) {
			sb.append(iterator.next().toString());
		}
		sb.append("\nARISTAS:\n");
		sb.append("────────────────────────────────────────────────────────────────────\n");
		sb.append("| CLAVE 1                 | CLAVE 2                 | PESO         |\n");
		sb.append("|─────────────────────────├─────────────────────────|──────────────|\n");
		for (Iterator<Arista> iterator = this.aristas.iterator(); iterator.hasNext();) {
			sb.append(iterator.next().toString());
		}
		return sb.toString();
	}

	/**
	 * Ordena el listado de las aristas del grafo de manera alfabetica y numerica
	 */
	public void ordenaAristas() {
		this.aristas.sort(new Comparator<Arista>() {
			@Override
			public int compare(Arista arista1, Arista arista2) {
				if (!arista1.getOrigen().equals(arista2.getOrigen())) {
					String[] clave1 = arista1.getOrigen().split("-");
					String[] clave2 = arista2.getOrigen().split("-");
					if (clave1[0].equals(clave2[0])) {
						return Integer.parseInt(clave1[1]) - Integer.parseInt(clave2[1]);
					} else {
						return arista1.getOrigen().compareTo(arista2.getOrigen());
					}
				} else {
					String[] clave1 = arista1.getDestino().split("-");
					String[] clave2 = arista2.getDestino().split("-");
					if (clave1[0].equals(clave2[0])) {
						return Integer.parseInt(clave1[1]) - Integer.parseInt(clave2[1]);
					} else {
						return arista1.getDestino().compareTo(arista2.getDestino());
					}
				}
			}
		});
	}

	/**
	 * Ordena los nodos del grafo de manera alfabetica y numerica
	 */
	public void ordenaNodos() {
		this.nodos.sort(new Comparator<NodoGrafo>() {
			@Override
			public int compare(NodoGrafo nodo1, NodoGrafo nodo2) {
				String[] clave1 = nodo1.getClave().split("-");
				String[] clave2 = nodo2.getClave().split("-");
				if (clave1[0].equals(clave2[0])) {
					return Integer.parseInt(clave1[1]) - Integer.parseInt(clave2[1]);
				} else {
					return nodo1.getClave().compareTo(nodo2.getClave());
				}
			}
		});

	}

	/**
	 * Devuelve la representación en String del grafo en formato de listas de
	 * adyacencias
	 * 
	 * @return String
	 */
	public String toString() {
		// ordeno las listas por si huebiera habido inserciones/borrados
		this.ordenaNodos();
		this.ordenaAristas();

		StringBuilder sb = new StringBuilder();

		// para cada uno de los nodos
		for (NodoGrafo nodo : this.nodos) {
			sb.append(this.paddingRigthWithSpaces("\n" + nodo.getClave() + ": ", 33));
			// Explorar las aristas del nodo actual (asumimos que no puede haber aristas
			// repetidas)
			for (Arista arista : this.aristas) {
				if (arista.getOrigen().equals(nodo.getClave())) {
					// si es clave origen
					sb.append(this.paddingRigthWithSpaces(arista.getDestino() + "(" + arista.getPeso() + "),", 33));
				} else if (arista.getDestino().equals(nodo.getClave())) {
					// si es clave destino
					sb.append(this.paddingRigthWithSpaces(arista.getOrigen() + "(" + arista.getPeso() + "),", 33));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * devuelve el vector de distancias mínimas del algoritmo de Dijkstra.
	 * 
	 * @param clave
	 * @return List<Double>
	 */
	public List<Double> Dijkstra(String clave) {
		NodoGrafo inicio = this.buscaNodo(clave);
		int cantidadNodos = this.nodos.size();
		List<Double> distancias = new ArrayList<>(Collections.nCopies(cantidadNodos, Double.POSITIVE_INFINITY));

		Set<NodoGrafo> visitados = new HashSet<>();
		distancias.set(this.nodos.indexOf(inicio), 0.00);

		// Cola de prioridad para obtener el nodo con la distancia mínima
		PriorityQueue<NodoGrafo> colaPrioridad = new PriorityQueue<>(
				Comparator.comparingDouble(n -> distancias.get(this.nodos.indexOf(n))));
		colaPrioridad.add(inicio);

		while (!colaPrioridad.isEmpty()) {
			// Obtener el nodo con la distancia mínima
			NodoGrafo actual = colaPrioridad.poll();
			visitados.add(actual);

			// Explorar las aristas del nodo actual
			for (Arista arista : this.aristas) {
				if ((arista.getOrigen().equals(actual.getClave()) || arista.getDestino().equals(actual.getClave()))
						&& !visitados.contains(buscaNodo(arista.getOtroExtremo(actual.getClave())))) {
					NodoGrafo nodoDestino = buscaNodo(arista.getOtroExtremo(actual.getClave()));
					// Calcular la nueva distancia desde el nodo de inicio hasta el nodo destino
					Double nuevaDistancia = distancias.get(this.nodos.indexOf(actual)) + arista.getPeso();
					int indiceDestino = this.nodos.indexOf(nodoDestino);

					// Actualizar la distancia si la nueva distancia es menor
					if (nuevaDistancia < distancias.get(indiceDestino)) {
						distancias.set(indiceDestino, nuevaDistancia);
						colaPrioridad.add(nodoDestino);
					}
				}
			}
		}

		return distancias;
	}

	/**
	 * Devuelve el camino de distancia mínima entre los nodos cuyas claves se pasan
	 * por parámetro. Si no existiera alguno de los nodos, devuelve null.
	 * 
	 * @param clave1
	 * @param clave2
	 * @return List<String>
	 */
	public List<String> Dijkstra(String clave1, String clave2) {
		NodoGrafo inicio = this.buscaNodo(clave1);
		NodoGrafo fin = this.buscaNodo(clave2);

		List<Double> distancias = this.Dijkstra(inicio.getClave());

		if (!this.nodos.contains(inicio) || !this.nodos.contains(fin)) {
			return null; // Al menos uno de los nodos no existe en el grafo
		}

		List<NodoGrafo> camino = new ArrayList<>();
		int indiceFin = this.nodos.indexOf(fin);

		if (distancias.get(indiceFin) == Double.POSITIVE_INFINITY) {
			return null; // No hay camino entre los nodos
		}

		// Reconstruir el camino desde el nodo de destino hasta el nodo de inicio
		while (!inicio.equals(fin)) {
			camino.add(fin);

			// Encontrar la arista que lleva al nodo anterior en el camino
			for (Arista arista : this.aristas) {
				String otroExtremo = arista.getOtroExtremo(fin.getClave());
				if ((arista.getDestino().equals(fin.getClave()) || arista.getOrigen().equals(fin.getClave()))
						&& distancias.get(this.nodos.indexOf(fin)) == distancias
								.get(this.nodos.indexOf(buscaNodo(otroExtremo))) + arista.getPeso()) {
					fin = buscaNodo(otroExtremo);
					break;
				}
			}
		}

		// Agregar el nodo de inicio al camino
		camino.add(inicio);
		Collections.reverse(camino);

		// para cumplir con el enunciado pasamos los nodos a String, solo con las claves
		return this.convertirNodosAClaves(camino);
	}

	/**
	 * Método para convertir una lista de nodos a una lista de claves
	 * 
	 * @param nodos
	 * @return
	 */
	public List<String> convertirNodosAClaves(List<NodoGrafo> nodos) {
		List<String> claves = new ArrayList<>();
		for (NodoGrafo nodo : nodos) {
			claves.add(nodo.getClave());
		}
		return claves;
	}

	public String paddingRigthWithSpaces(String inputString, int maxLength) {
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