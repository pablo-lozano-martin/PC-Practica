package mensaje;

import java.net.InetAddress;

@SuppressWarnings("serial")
public class MensajeListo extends Mensaje{
	
	private InetAddress _ip;
	
	private int _puerto;
	
	public MensajeListo(String origen, String destino, InetAddress ip, int puerto) {
		super(origen, destino);
		_ip = ip;
		_puerto = puerto;
	}
	
	public InetAddress getIP()
	{
		return _ip;
	}
	
	public int getPuerto()
	{
		return _puerto;
	}
	
	public String getTipo()
	{
		return "solicitud_fichero_listo";
	}
}