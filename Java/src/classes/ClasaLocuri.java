package classes;

public class ClasaLocuri {
	private int numarLocuri;
	private float pret;
	
	public ClasaLocuri(int numarLocuri, float pret) {
		this.numarLocuri = numarLocuri;
		this.pret = pret;
	}

	public int getNumarLocuri() {
		return numarLocuri;
	}

	public void setNumarLocuri(int numarLocuri) {
		this.numarLocuri = numarLocuri;
	}

	public float getPret() {
		return pret;
	}

	public void setPret(float pret) {
		this.pret = pret;
	}

	@Override
	public String toString() {
		return "ClasaLocuri [numarLocuri=" + numarLocuri + ", pret=" + pret + "]";
	}
	
	
}
