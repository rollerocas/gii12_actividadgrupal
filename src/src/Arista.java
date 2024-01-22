package src;

public class Arista {
	public final Integer COLUMNAS_DE_DATOS = 3;

	private String origen;
	private String destino;
	private Double peso;

	/**
	 * Crea una arista dados todos sus parametros
	 * 
	 * @param clave1
	 * @param clave2
	 * @param peso
	 */
	public Arista(String clave1, String clave2, Double peso) {
		this.origen = clave1;
		this.destino = clave2;
		this.peso = peso;
	}

	/**
	 * Crea una arista ignorando su peso
	 * 
	 * @param clave1
	 * @param clave2
	 */
	public Arista(String clave1, String clave2) {
		this.origen = clave1;
		this.destino = clave2;
		this.peso = null;
	}

	/**
	 * Crea una arista dado un array de string proveniente del CSV
	 * 
	 * @param datos
	 * @throws Exception
	 */
	public Arista(String[] datos) throws Exception {
		if (datos.length != this.COLUMNAS_DE_DATOS) {
			throw new Exception("El fichero de datos de las aristas no es correcto");
		}
		this.origen = datos[0];
		this.destino = datos[1];
		this.peso = Double.parseDouble(datos[2].replace(",", "."));
	}

	/**
	 * Comprueba si una arista tiene un nodo en uno de sus vertices
	 * 
	 * @param clave
	 * @return boolean
	 */
	public boolean contieneClave(String clave) {
		if (this.origen.equals(clave) || this.destino.equals(clave)) {
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si una arista tiene los mismos nodos que otra
	 * 
	 * @param arista
	 * @return boolean
	 */
	public boolean tieneMismosVertices(Arista arista) {
		if (this.origen.equals(arista.getOrigen()) && this.destino.equals(arista.getDestino())
				|| this.destino.equals(arista.getOrigen()) && this.origen.equals(arista.getDestino())) {
			return true;
		}
		return false;
	}

	/**
	 * Método para obtener el otro extremo de la arista, devuelve la clave del nodo
	 * en el otro extremo o null
	 * 
	 * @param actual
	 * @return String
	 */
	public String getOtroExtremo(String actual) {
		if (this.contieneClave(actual)) {
			if (origen.equals(actual)) {
				return destino;
			} else {
				return origen;
			}
		} else {
			return null;
		}
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String clave1) {
		this.origen = clave1;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String clave2) {
		this.destino = clave2;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return ("| " + Principal.paddingRigthWithSpaces(this.origen, 23) + " | "
				+ Principal.paddingRigthWithSpaces(this.destino, 23) + " | "
				+ Principal.paddingRigthWithSpaces(this.peso.toString(), 12) + " |\n");
	}
}
