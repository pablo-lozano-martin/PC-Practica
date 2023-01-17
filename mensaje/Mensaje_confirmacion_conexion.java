package mensaje;

// mensaje de confirmaci�n de conexi�n correcta
@SuppressWarnings("serial")
public class Mensaje_confirmacion_conexion extends Mensaje {

	public Mensaje_confirmacion_conexion(String origen, String destino) {
		super(origen, destino);
	}

	public String getTipo() {
		return "sesion";
	}
}