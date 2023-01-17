package mensaje;

// mensaje para cerrar conexi�n
@SuppressWarnings("serial")
public class Mensaje_cerrar_conexion extends Mensaje{

	public Mensaje_cerrar_conexion(String origen, String destino) {
		super(origen, destino);
	}

	public String getTipo() {
		return "fin_sesion";
	}
}