package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;

public class Usuario {
	
	String _usuario;
	ObjectInputStream _reader;
	ObjectOutputStream _writer;
	
	public Usuario(String usuario, ObjectInputStream reader, ObjectOutputStream writer)
	{
		_usuario = usuario;
		_reader = reader;
		_writer = writer;
	}
	public String getUser()
	{
		return _usuario;
	}

	public ObjectInputStream getReader()
	{
		return _reader;
	}

	public ObjectOutputStream getWriter()
	{
		return _writer;
	}	
	
}
