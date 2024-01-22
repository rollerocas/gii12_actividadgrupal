package src;

public class Gasolinera implements NodoGrafo {
	public final Integer COLUMNAS_DE_DATOS = 5;

	private String clave;
	private String rotulo;
	private String direccion;
	private String municipio;
	private String localidad;

	public Gasolinera(String clave, String rotulo, String direccion, String municipio, String localidad) {
		this.clave = clave;
		this.rotulo = rotulo;
		this.direccion = direccion;
		this.municipio = municipio;
		this.localidad = localidad;
	}

	public Gasolinera(String[] datos) throws Exception {
		if (datos.length != this.COLUMNAS_DE_DATOS) {
			throw new Exception("El fichero de datos de las gasolineras no es correcto");
		}
		this.municipio = datos[0];
		this.localidad = datos[1];
		this.clave = datos[2];
		this.direccion = datos[3];
		this.rotulo = datos[4];
	}

	@Override
	public String getClave() {
		return this.clave;
	}

	@Override
	public String toString() {
		return ("| " 
				+ Principal.paddingRigthWithSpaces(this.clave, 23) + " | " 
				+ Principal.paddingRigthWithSpaces(this.rotulo, 20) + " | " 
				+ Principal.paddingRigthWithSpaces(this.direccion, 28) + " | "
				+ Principal.paddingRigthWithSpaces(this.municipio, 12) + " | "
				+ Principal.paddingRigthWithSpaces(this.localidad, 15) + " |\n");
	}	
}
