package record;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RecPeriodical extends Record{
	private GregorianCalendar startRec;
	private int length;						// Length of registration expressed in seconds

	
	public RecPeriodical(GregorianCalendar startRec, int length) {	
		this.startRec = startRec;
		this.length = length;
	}
	
	public GregorianCalendar getStart() {
		return this.startRec;
	}
	
	public GregorianCalendar getStop() {
		GregorianCalendar stop = new GregorianCalendar(this.startRec.get(Calendar.YEAR),
													   this.startRec.get(Calendar.MONTH),
													   this.startRec.get(Calendar.DAY_OF_MONTH),
													   this.startRec.get(Calendar.HOUR_OF_DAY),
													   this.startRec.get(Calendar.MINUTE),
													   this.startRec.get(Calendar.SECOND));
		stop.add(Calendar.SECOND, this.length);
		
		return stop;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public String toString() {
		return this.startRec.get(Calendar.YEAR) 		+ "-" +
			   (this.startRec.get(Calendar.MONTH)+1)	+ "-" +
			   this.startRec.get(Calendar.DAY_OF_MONTH) + " " +
			   this.startRec.get(Calendar.HOUR_OF_DAY) 	+ ":" +
			   this.startRec.get(Calendar.MINUTE) 		+ ":" +
			   this.startRec.get(Calendar.SECOND)		+ " " +
			   this.length;
	}
	
	public String toHexString() {
		String ret = "";
		
		ret+=Record.swapBytes(String.format("%04X ", this.startRec.get(Calendar.DAY_OF_MONTH)).toUpperCase());        
		ret+=Record.swapBytes(String.format("%04X ", (this.startRec.get(Calendar.MONTH)+1)).toUpperCase());
		ret+=Record.swapBytes(String.format("%04X ", this.startRec.get(Calendar.YEAR)-2000).toUpperCase());
		ret+=Record.swapBytes(String.format("%04X ", this.startRec.get(Calendar.HOUR_OF_DAY)).toUpperCase());
		ret+=Record.swapBytes(String.format("%04X ", this.startRec.get(Calendar.MINUTE)).toUpperCase());
		ret+=Record.swapBytes(String.format("%04X ", this.startRec.get(Calendar.SECOND)).toUpperCase());
		
		// WARNING!!!
		//ret+=Record.swapBytes(String.format("%04X ", this.length).toUpperCase());							<<<< Emanuele Lattanzi vuole la durata in minuti invece che in secondi
		
		//ret+=Record.swapBytes(String.format("%04X ", 0).toUpperCase());        
		//ret+=Record.swapBytes(String.format("%04X ", 0).toUpperCase());
		//ret+=Record.swapBytes(String.format("%04X ", 0).toUpperCase());
		//ret+=Record.swapBytes(String.format("%04X ", 0).toUpperCase());
		//ret+=Record.swapBytes(String.format("%04X ", 0).toUpperCase());									<<<< Questi campi sono intesi come Anno Mese Giorno Ore Minuti della data di fine
		//																										 mentre i byte che dovrebbero rappresentare i secondi rappresentano i minuti di registrazione
		
		GregorianCalendar stopRec = this.getStop();
		
		ret+=Record.swapBytes(String.format("%04X ", stopRec.get(Calendar.DAY_OF_MONTH)).toUpperCase());        
		ret+=Record.swapBytes(String.format("%04X ", (stopRec.get(Calendar.MONTH)+1)).toUpperCase());
		ret+=Record.swapBytes(String.format("%04X ", stopRec.get(Calendar.YEAR)-2000).toUpperCase());
		ret+=Record.swapBytes(String.format("%04X ", stopRec.get(Calendar.HOUR_OF_DAY)).toUpperCase());
		ret+=Record.swapBytes(String.format("%04X ", stopRec.get(Calendar.MINUTE)).toUpperCase());
		ret+=Record.swapBytes(String.format("%04X ", (int)this.length/60).toUpperCase());
		
		return ret;
	}
	
}
