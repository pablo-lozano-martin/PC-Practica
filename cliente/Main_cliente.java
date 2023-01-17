package cliente;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

// main separado de la clase cliente para poder tener atributos propios para
// cada cliente, de lo contrario tendr�an que ser est�ticos (y valer lo mismo para
// todas las instancias de cliente)
public class Main_cliente {

// representa una de las m�quinas cliente que se van a utilizar
public static void main(String[] args) throws IOException {
		
		// si no hay puerto error
		if(args.length < 1) {
			System.err.println("No hay puerto");
			System.exit(1);
		}
		
		// conversi�n del string a entero
		int puerto = Integer.parseInt(args[0]);
		
		System.out.println("Introduzca el nombre de usuario");
		
		@SuppressWarnings("resource")
		// flujo de entrada por consola
		Scanner scanner = new Scanner(System.in);
		String nombreUser = scanner.nextLine();
		
		// se crea el cliente
		Cliente cliente = new Cliente(puerto, nombreUser, InetAddress.getLocalHost());
		
		// se inicia la ejecuci�n del cliente
		cliente.iniciaCliente();
	}
}