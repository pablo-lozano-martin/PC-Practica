package mensaje;

import java.util.List;

// mensaje para iniciar conexi�n
@SuppressWarnings("serial")
public class Mensaje_conexion extends Mensaje{
	
	// lista de los ficheros que tiene el cliente que inicia la conexi�n
	private List<String> ficherosCliente;

	public List<String> getFicherosCliente() {
		return ficherosCliente;
	}

	public Mensaje_conexion(String origen, String destino, List<String> ficherosCliente) {
		super(origen, destino);
		this.ficherosCliente = ficherosCliente;
	}

	public String getTipo() {
		return "sesion";
	}
}