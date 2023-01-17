package mensaje;

// mensaje del cliente al oyente cliente para pedir que busque propietarios de un fichero
@SuppressWarnings("serial")
public class Mensaje_pedir_fichero extends Mensaje{
	
	// fichero pedido
	private String fichero;

	public Mensaje_pedir_fichero(String origen, String destino, String fichero) {
		super(origen, destino);
		this.fichero = fichero;
	}

	public String getFichero() {
		return fichero;
	}

	public String getTipo() {
		return "solicitud_fichero";
	}
}