package mensaje;

@SuppressWarnings("serial")
public class MensajeSolicitud extends Mensaje{
	
	// fichero pedido
	private String _f;

	public MensajeSolicitud(String origen, String destino, String f) {
		super(origen, destino);
		_f = f;
		
	}
	
	public String getTipo()
	{
		return "solicitud_fichero";
	}

	public String getFichero()
	{
		return _f;
	}


}