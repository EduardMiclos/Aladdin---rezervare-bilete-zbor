package classes;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

public class PersonalAeroport{
	private TipPlata plata;
	private String numeAeroport;
	
	public PersonalAeroport(TipPlata plata, String numeAeroport) {
		this.plata = plata;
		this.numeAeroport = numeAeroport;
	}
}
