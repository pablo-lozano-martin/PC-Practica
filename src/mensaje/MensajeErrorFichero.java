package mensaje;

@SuppressWarnings("serial")
public class MensajeErrorFichero extends Mensaje{

	public MensajeErrorFichero(String origen, String destino) {
		super(origen, destino);
	}

	public String getTipo() {
		return "error";
	}
}