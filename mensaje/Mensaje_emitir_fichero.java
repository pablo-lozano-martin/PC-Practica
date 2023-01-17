package mensaje;

// mensaje para que el oyente cliente pida a otro cliente que emita un fichero
@SuppressWarnings("serial")
public class Mensaje_emitir_fichero extends Mensaje {
	
	// fichero pedido
	private String fichero;
	
	// puerto que el oyente cliente ha asignado para esta conexi�n sin que se produzcan interferencias
	private int puerto;
	
	public String getFichero() {
		return fichero;
	}

	public int getPuerto() {
		return puerto;
	}

	public Mensaje_emitir_fichero(String origen, String destino, String fichero, int puerto) {
		super(origen, destino);
		this.fichero = fichero;
		this.puerto = puerto;
	}

	public String getTipo() {
		return "solicitud_fichero";
	}
	
}