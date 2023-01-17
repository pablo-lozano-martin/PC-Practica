//package mensaje;
//
//import java.net.InetAddress;
//
//// mensaje que manda el cliente que va a emitir el fichero al oyente cliente para
//// informarle de que ya ha creado su emisor y que ya est� preparado para emitir el fichero
//@SuppressWarnings("serial")
//public class Mensaje_preparado_cliente_servidor extends Mensaje{
//	
//	// direcci�n IP del emisor (la necesitamos para crear el canal con el socket)
//	private InetAddress dirIP;
//	
//	// puerto del emisor asignado para la conexi�n con el receptor
//	private int puerto;
//	
//	public Mensaje_preparado_cliente_servidor(String origen, String destino, InetAddress dirIP, int puerto) {
//		super(origen, destino);
//		this.dirIP = dirIP;
//		this.puerto = puerto;
//	}
//	public InetAddress getDirIP() {
//		return dirIP;
//	}
//	public int getPuerto() {
//		return puerto;
//	}
//	
//	public String getTipo() {
//		return "solicitud_fichero_listo";
//	}
//}