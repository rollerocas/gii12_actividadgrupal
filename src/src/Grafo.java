package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
		for (Iterator<Arista> iterator = this.aristas.iterator(); iterator.hasNext();) {
			Arista arista = iterator.next();
			if (arista.contieneClave(clave)) {
				this.aristas.remove(arista);
			}
		}
		// obtengo su nodo
		NodoGrafo nodoParaBorrar = this.buscaNodo(clave);
		// borramos su nodo
		if (nodoParaBorrar != null) {
			this.nodos.remove(nodoParaBorrar);
		}
		return true;
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
		for (Iterator<NodoGrafo> iterator = this.nodos.iterator(); iterator.hasNext();) {
			if (iterator.next().getClave().equals(clave)) {
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
			if (nodo.getClave() == clave) {
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

	// si existen las dos claves pasadas por parámetro, y la arista no existe
	// previamente en el grafo, inserta la arista entre los nodos del grafo
	// correspondientes a ambas claves con el peso pasado por parámetro y devuelve
	// true. En caso contrario, no se realiza la inserción y devuelve false.
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
		// ordenar la lista de aristas
		// TODO
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
		return this.insertarArista(arista.getClave1(), arista.getClave2(), arista.getPeso());
	}

	// borra la arista definida por las dos claves pasadas por parámetro si existe
	// en el grafo y devuelve true. Si no existiera, devuelve false.
	public boolean borrarArista(String clave1, String clave2) {
		// TODO
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
		for (Iterator<Arista> iterator = this.aristas.iterator(); iterator.hasNext();) {
			if (iterator.next().tieneMismosVertices(aristaParaBuscar)) {
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
	 * @return
	 */
	public int numeroAristas() {
		return this.aristas.size();
	}

	// devuelve la representación en String del grafo en formato de listas de
	// adyacencias. El siguiente ejemplo muestra la cadena correspondiente al grafo
	// de la figura:
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("NODOS:\n");
		sb.append("──────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n");
		sb.append("| CLAVE                   | ROTULO               | DIRECCION                    | MUNICIPIO    | LOCALIDAD       |\n");
		sb.append("|─────────────────────────├──────────────────────|──────────────────────────────|──────────────|─────────────────|\n");
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

	// devuelve el vector de distancias mínimas del algoritmo de Dijkstra.
	public List<Double> Dijkstra(String clave) {
		return null;
	}

	// devuelve el camino de distancia mínima entre los nodos cuyas claves se pasan
	// por parámetro.
	// Si no existiera alguno de los nodos, devuelve null.
	public List<String> Dijkstra(String clave1, String clave2) {
		return null;
	}
}
