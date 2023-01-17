package mensaje;

@SuppressWarnings("serial")
public class MensajeEmision extends Mensaje {
	
	private String _f;
	private int _puerto;

	public MensajeEmision(String origen, String destino, String f, int puerto)
	{
		super(origen, destino);
		_f = f;
		_puerto = puerto;
	}

	public String getTipo() {
		return "solicitud_fichero";
	}
	
	public String getFichero() {	//Envia el fichero
		return _f;
	}

	public int getPuerto() {
		return _puerto;
	}
	
}