package classes;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

public class PersonalAeroport{
	private PlataFizic plata;
	private String numeAeroport;
	
	public PersonalAeroport(PlataFizic plata, String numeAeroport) {
		this.plata = plata;
		this.numeAeroport = numeAeroport;
	}
}
