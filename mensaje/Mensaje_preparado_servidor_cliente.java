//package mensaje;
//
//import java.net.InetAddress;
//
//// mensaje que manda el oyente cliente al cliente indic�ndole que ya puede recibir el fichero
//@SuppressWarnings("serial")
//public class Mensaje_preparado_servidor_cliente extends Mensaje{
//	
//	// direcci�n IP del emisor
//	private InetAddress dirIP;
//	
//	// puerto en el que espera el emisor
//	private int puerto;
//	
//	public Mensaje_preparado_servidor_cliente(String origen, String destino, InetAddress dirIP, int puerto) {
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