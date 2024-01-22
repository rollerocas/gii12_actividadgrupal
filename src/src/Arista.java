package src;

public class Arista {
	public final Integer COLUMNAS_DE_DATOS = 3;

	private String origen;
	private String destino;
	private Double peso;

	public Arista(String clave1, String clave2, Double peso) {
		this.origen = clave1;
		this.destino = clave2;
		this.peso = peso;
	}

	public Arista(String clave1, String clave2) {
		this.origen = clave1;
		this.destino = clave2;
		this.peso = null;
	}

	public Arista(String[] datos) throws Exception {
		if (datos.length != this.COLUMNAS_DE_DATOS) {
			throw new Exception("El fichero de datos de las aristas no es correcto");
		}
		this.origen = datos[0];
		this.destino = datos[1];
		this.peso = Double.parseDouble(datos[2].replace(",", "."));
	}

	public boolean contieneClave(String clave) {
		if (this.origen.equals(clave) || this.destino.equals(clave)) {
			return true;
		}
		return false;
	}

	public boolean tieneMismosVertices(Arista arista) {
		if (this.origen.equals(arista.getOrigen()) && this.destino.equals(arista.getDestino())
				|| this.destino.equals(arista.getOrigen()) && this.origen.equals(arista.getDestino())) {
			return true;
		}
		return false;
	}

	// Método para obtener el otro extremo de la arista
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
