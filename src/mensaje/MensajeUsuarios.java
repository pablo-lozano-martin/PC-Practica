package mensaje;

@SuppressWarnings("serial")
public class MensajeUsuarios extends Mensaje{

	public MensajeUsuarios(String origen, String destino) {
		super(origen, destino);
	}

	public String getTipo() {
		return "solicitud_lista";
	}
}