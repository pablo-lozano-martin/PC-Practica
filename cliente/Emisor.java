package cliente;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// hilo para, en el peer to peer (cliente-cliente sin pasar por servidor),
// emitir el fichero directamente al cliente que lo pidi� sin que el oyente servidor 
// tenga que dejar de escuchar
public class Emisor extends Thread{
	
	// puerto para establecer la conexi�n con el receptor
	private int puerto;
	
	// fichero que le han pedido al emisor
	private String fichero;
	
	public Emisor(int puerto, String fichero) {
		super();
		this.puerto = puerto;
		this.fichero = fichero;
	}
	
	// ejecuci�n del hilo
	public void run() {
		try {
			// el emisor espera a que el receptor se conecte al canal
			ServerSocket serversocket = new ServerSocket(puerto);
			Socket socket = serversocket.accept();
			
			// se crea el flujo para poder escribirle el fichero al receptor
			PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
			
			// se abre un flujo para poder leer del fichero
			BufferedReader leeFichero = new BufferedReader(new InputStreamReader(new FileInputStream(fichero)));
			
			// se lee el fichero l�nea a l�nea y se guarda en un string
			// (al estar enviando siempre archivos de texto, solo es necesario enviar el string)
			String line, fichero = "";
			while((line = leeFichero.readLine()) != null) {
				fichero += line + '\n';
			}
			
			// final del fichero
			fichero += '\0';
			
			// env�o del fichero al receptor
			escritor.println(fichero);
			
			serversocket.close();
			leeFichero.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}