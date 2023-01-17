package cliente;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Emisor extends Thread{
	
	private int _puerto;
	private String _file;
	
	public Emisor(int puerto, String file)
	{
		super();
		_file = file;
		_puerto = puerto;
	}
	
	public void run() {
		try	{	//Esperar a que el receptor se una al canal
			ServerSocket sSocket = new ServerSocket(_puerto);
			Socket socket = sSocket.accept();
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			
			BufferedReader f = new BufferedReader(new InputStreamReader(new FileInputStream(_file)));
			
			String l, file = "";
			while((l = f.readLine()) != null)
			{
				file += l + '\n';
			}
			
			file = file + '\0';
			
			writer.println(file);	//Lee el fichero y se lo pasa al receptor por el socket que tienen en comun
			
			f.close();
			sSocket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}