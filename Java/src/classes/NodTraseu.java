package classes;

import java.util.Calendar;

public class NodTraseu {
	private String numeAeroport;
	private String oraPlecare;
	private String oraSosire;
	
	public NodTraseu(String numeAeroport, String oraPlecare, String oraSosire) {
		this.numeAeroport = numeAeroport;
		this.oraPlecare = oraPlecare;
		this.oraSosire = oraSosire;
	}

	public String getNumeAeroport() {
		return numeAeroport;
	}

	public void setNumeAeroport(String numeAeroport) {
		this.numeAeroport = numeAeroport;
	}

	public String getOraPlecare() {
		return oraPlecare;
	}

	public void setOraPlecare(String oraPlecare) {
		this.oraPlecare = oraPlecare;
	}

	public String getOraSosire() {
		return oraSosire;
	}

	public void setOraSosire(String oraSosire) {
		this.oraSosire = oraSosire;
	}

	@Override
	public String toString() {
		return "NodTraseu [numeAeroport=" + numeAeroport + ", oraPlecare=" + oraPlecare + ", oraSosire=" + oraSosire
				+ "]";
	}
	
	
}
