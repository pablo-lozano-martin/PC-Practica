package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

// hilo para, en el peer to peer (cliente-cliente sin pasar por servidor),
// recibir el fichero directamente del cliente que lo envi� sin que el oyente servidor 
// tenga que dejar de escuchar
public class Receptor extends Thread{
	
	// IP del emisor
	private InetAddress dirIP;
	
	// puerto para la conexi�n con el emisor
	private int puerto;
	
	public Receptor(InetAddress dirIP, int puerto) {
		super();
		this.dirIP = dirIP;
		this.puerto = puerto;
	}
	
	// ejecuci�n del hilo
	public void run() {
		try {
			// se crea el canal para la comunicaci�n con el emisor
			Socket socket = new Socket(dirIP, puerto);
			
			// se crea el flujo para leer del emisor
			BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			// se lee l�nea a l�nea el fichero que hemos pedido y se escribe por consola
			String line, fichero = "";
			while(true) {
				line = lector.readLine();
				if(line.equals("\0")) {break;}
				fichero += line + '\n';
			}
			
			System.out.println("El fichero recibido es:\n" + fichero);
			
			socket.close();
			lector.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}