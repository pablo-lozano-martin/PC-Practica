package mensaje;

@SuppressWarnings("serial")
public class MensajeDesconectar extends Mensaje{

	public MensajeDesconectar(String origen, String destino) {
		super(origen, destino);
	}

	public String getTipo() {
		return "fin_sesion";
	}
}