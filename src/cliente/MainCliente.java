package cliente;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class MainCliente {

public static void main(String[] args) throws IOException {
		
		if(args.length < 1) {
			System.err.println("<Puerto no detectado>");
			System.exit(1);
		}
		
		int puerto = Integer.parseInt(args[0]);
		
		System.out.println("Escriba el nombre del usuario:");
		
		try (Scanner s = new Scanner(System.in))
		{
			String name = s.nextLine();
			
			Cliente c = new Cliente(puerto, name, InetAddress.getLocalHost());	//Crea un nuevo cliente con sus datos
			
			c.init();
		}
		catch(Exception e)
		{
			System.out.println("ERROR - Nombre de usuario invalido");
		}
	}
}