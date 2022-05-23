package classes;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

public class Client {
	private float pretBilet;
	private String nume;
	private String prenume;
	private String email;
	private String telefon;
	private int varsta;
	private TipPlata plata;
	private TipClasa clasa;
	 
	public Client(float pretBilet, String nume, String prenume, String email, String telefon, int varsta, TipPlata plata, TipClasa clasa) {
		this.pretBilet = pretBilet;
		this.nume = nume;
		this.prenume = prenume;
		this.email = email;
		this.telefon = telefon;
		this.varsta = varsta;
		this.plata = plata;
		this.clasa = clasa;
	}
	
	public Client(String nume, String prenume, String email, String telefon, int varsta, TipPlata plata, TipClasa clasa) {
		this.nume = nume;
		this.prenume = prenume;
		this.email = email;
		this.telefon = telefon;
		this.varsta = varsta;
		this.plata = plata;
		this.clasa = clasa;
	}

	public float getPretBilet() {
		return pretBilet;
	}
	
	public void setPretBilet(float pretBilet) {
		this.pretBilet = pretBilet;
	}
	
	public String getNume() {
		return nume;
	}
	
	public void setNume(String nume) {
		this.nume = nume;
	}
	
	public String getPrenume() {
		return prenume;
	}
	
	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefon() {
		return telefon;
	}
	
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	
	public int getVarsta() {
		return varsta;
	}
	
	public void setVarsta(int varsta) {
		this.varsta = varsta;
	}
	
	public TipPlata getPlata() {
		return plata;
	}
	
	public void setPlata(TipPlata plata) {
		this.plata = plata;
	}
	
	public TipClasa getClasa() {
		return clasa;
	}
	
	public void setClasa(TipClasa clasa) {
		this.clasa = clasa;
	}

	@Override
	public String toString() {
		return "Utilizator [pretBilet=" + pretBilet + ", nume=" + nume + ", prenume=" + prenume + ", email=" + email
				+ ", telefon=" + telefon + ", varsta=" + varsta + "]";
	}
	
	public float calculPret(Cursa cursa, Boolean dusIntors) {
		return 0;
	}	
}
