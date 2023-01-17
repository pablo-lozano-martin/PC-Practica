package mensaje;

@SuppressWarnings("serial")
public class MensajeConfirmarConexion extends Mensaje {

	public MensajeConfirmarConexion(String origen, String destino) {
		super(origen, destino);
	}

	public String getTipo() {
		return "sesion";
	}
}