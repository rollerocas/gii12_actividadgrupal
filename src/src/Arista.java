package src;

public class Arista {
	public final Integer COLUMNAS_DE_DATOS = 3;
	
	private String clave1;
	private String clave2;
	private Double peso;
	
	public Arista(String clave1, String clave2, Double peso) {
		this.clave1 = clave1;
		this.clave2 = clave2;
		this.peso = peso;
	}
	
	public Arista(String clave1, String clave2) {
		this.clave1 = clave1;
		this.clave2 = clave2;
		this.peso = null;
	}
	
	public Arista(String[] datos) throws Exception {
		if (datos.length != this.COLUMNAS_DE_DATOS) {
			throw new Exception("El fichero de datos de las aristas no es correcto");
		}
		this.clave1 = datos[0];
		this.clave2 = datos[1];
		this.peso = Double.parseDouble(datos[2].replace(",","."));
	}
	
	public boolean contieneClave(String clave) {
		if (this.clave1 == clave || this.clave2 == clave) {
			return true;
		}
		return false;
	}
	
	public boolean tieneMismosVertices(Arista arista) {
		if (this.clave1 == arista.getClave1() && this.clave2 == arista.getClave2()) {
			return true;
		}
		return false;
	}
	
	public String getClave1() {
		return clave1;
	}

	public void setClave1(String clave1) {
		this.clave1 = clave1;
	}

	public String getClave2() {
		return clave2;
	}

	public void setClave2(String clave2) {
		this.clave2 = clave2;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}
	
	@Override
	public String toString() {
		return ("| "+this.paddingRigthWithSpaces(this.clave1, 23)+" | "+this.paddingRigthWithSpaces(this.clave2, 23)+" | "+this.paddingRigthWithSpaces(this.peso.toString(), 12)+" |\n");
	}
	
	public String paddingRigthWithSpaces(String inputString, int maxLength) {
		int totalSpacesToAdd = maxLength - inputString.length();
		StringBuilder sb = new StringBuilder();
		if (totalSpacesToAdd < 0) {
			sb.append(inputString.substring(0,maxLength-3));
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
