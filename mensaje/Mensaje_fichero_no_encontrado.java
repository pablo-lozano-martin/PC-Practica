package mensaje;

// mensaje de fichero no encontrado
@SuppressWarnings("serial")
public class Mensaje_fichero_no_encontrado extends Mensaje{

	public Mensaje_fichero_no_encontrado(String origen, String destino) {
		super(origen, destino);
	}

	public String getTipo() {
		return "error";
	}
}