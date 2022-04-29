package firstpackage;

public class ClasaLocuri {
	private Integer numarLocuri;
	private float pret;
	
	public ClasaLocuri(Integer numarLocuri, float pret) {
		this.numarLocuri = numarLocuri;
		this.pret = pret;
	}

	public Integer getNumarLocuri() {
		return numarLocuri;
	}

	public void setNumarLocuri(Integer numarLocuri) {
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
