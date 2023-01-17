package mensaje;

import java.net.InetAddress;
import java.util.List;

@SuppressWarnings("serial")
public class MensajeConectar extends Mensaje{
	
	private List<String> _f;

	public MensajeConectar(String origen, String destino, List<String> f) {
		super(origen, destino);
		_f = f;
	}

	public String getTipo() {
		return "sesion";
	}
	
	public List<String> getFiles() {
		return _f;
	}

}