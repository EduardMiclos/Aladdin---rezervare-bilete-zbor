package firstpackage;

import java.util.Calendar;

public class NodTraseu {
	private String numeAeroport;
	private Calendar oraPlecare;
	private Calendar oraSosire;
	
	public NodTraseu(String numeAeroport, Calendar oraPlecare, Calendar oraSosire) {
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

	public Calendar getOraPlecare() {
		return oraPlecare;
	}

	public void setOraPlecare(Calendar oraPlecare) {
		this.oraPlecare = oraPlecare;
	}

	public Calendar getOraSosire() {
		return oraSosire;
	}

	public void setOraSosire(Calendar oraSosire) {
		this.oraSosire = oraSosire;
	}

	@Override
	public String toString() {
		return "NodTraseu [numeAeroport=" + numeAeroport + ", oraPlecare=" + oraPlecare + ", oraSosire=" + oraSosire
				+ "]";
	}
	
	
}
