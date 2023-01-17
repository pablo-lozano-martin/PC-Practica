package mensaje;

// mensaje del cliente para pedir al oyente cliente la lista de usuarios conectados
@SuppressWarnings("serial")
public class Mensaje_lista_usuarios extends Mensaje{

	public Mensaje_lista_usuarios(String origen, String destino) {
		super(origen, destino);
	}

	public String getTipo() {
		return "solicitud_lista";
	}
}