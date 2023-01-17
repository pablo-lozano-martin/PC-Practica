package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Receptor extends Thread {
	
	private int _puerto;
	private InetAddress _ip;
	
	public Receptor(InetAddress ip, int puerto) {
		super();
		_ip = ip;
		_puerto = puerto;
	}
	
	public void run() {
		try {
			Socket socket = new Socket(_ip, _puerto);
			
			BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			int i = 0;
			String l = "";
			String f = "";
			
			while(true)		//Espera hasta que le llega un fichero a descargar
			{
				l = lector.readLine();
				
				if (!l.equals("\0"))
				{
					f = f + l + '\n';
					i++;
					
					if (i%1000 == 0)	//Escribe cuánto lleva leido en caso de que sea un fichero grande
					{
						System.out.println(i + " lineas leidas");
					}
				}
				else
				{
					break;
				}
			}
			
			socket.close();
			lector.close();
			
			System.out.println("El receptor ha descargado: " + f);
			System.out.println("Que consta de " + i + " líneas");
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}