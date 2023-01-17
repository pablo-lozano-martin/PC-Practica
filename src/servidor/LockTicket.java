package servidor;

import java.util.concurrent.atomic.AtomicInteger;

public class LockTicket {
	
	private volatile int _nowTicket;
	private volatile AtomicInteger  _nextTicket;
	
	public LockTicket()
	{
		_nowTicket = 0;
		_nextTicket = new AtomicInteger(0);
	}

	void takeLock(int id)	//El proceso se hace con el ticket
	{
		int myTicket = _nextTicket.getAndAdd(1);
		while(_nowTicket != myTicket);
	}

	void releaseLock(int id)	//El proceso devuelve el ticket dejando pasar al siguiente
	{
		_nowTicket++;
	}

}