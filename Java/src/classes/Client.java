package classes;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

public class Client extends Utilizator {
	private PlataOnline plata;
	
	public Client(Pair<Integer, Integer> locuriDorite, float pretBilet, String nume, String prenume, String email, String telefon, Integer varsta) {
		super(locuriDorite, pretBilet, nume, prenume, email, telefon, varsta);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float calculPret(Cursa cursa, Boolean dusIntors) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void cautaCurse() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void efectuarePlata() {
		// TODO Auto-generated method stub
		
	}

	
}
