package classes;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

public class Client {
	private float pretBilet;
	private String nume;
	private String prenume;
	private String email;
	private String telefon;
	private Integer varsta;
	private PlataOnline plata;
	
	public Client(Pair<Integer, Integer> locuriDorite, float pretBilet, String nume, String prenume, String email, String telefon, Integer varsta) {
		this.pretBilet = pretBilet;
		this.nume = nume;
		this.prenume = prenume;
		this.email = email;
		this.telefon = telefon;
		this.varsta = varsta;
		// TODO Auto-generated constructor stub
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
	public Integer getVarsta() {
		return varsta;
	}
	public void setVarsta(Integer varsta) {
		this.varsta = varsta;
	}

	@Override
	public String toString() {
		return "Utilizator [pretBilet=" + pretBilet + ", nume=" + nume + ", prenume=" + prenume + ", email=" + email
				+ ", telefon=" + telefon + ", varsta=" + varsta + "]";
	}
	
	public float calculPret(Cursa cursa, Boolean dusIntors) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void cautaCurse() {
		// TODO Auto-generated method stub
		
	}

	public void efectuarePlata() {
		// TODO Auto-generated method stub
		
	}

	
}
