package classes;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

public class Cursa {
	private String codCursa;
	private String tipAvion;
	private ClasaLocuri locuriBusiness;
	private ClasaLocuri locuriEconomy;
	private String codTraseu;
	private Vector<NodTraseu> trasee;
	private Vector<ZiOperare> zileOperare;
	
	private String numeCompanie;
	
	private float discountDusIntors;
	private float discountLastMinute;
	
	public Cursa(String codCursa, String tipAvion, ClasaLocuri locuriBusiness, ClasaLocuri locuriEconomy, String codTraseu,
			float discountDusIntors, float discountLastMinute) {
		super();
		this.codCursa = codCursa;
		this.tipAvion = tipAvion;
		this.locuriBusiness = locuriBusiness;
		this.locuriEconomy = locuriEconomy;
		this.codTraseu = codTraseu;
		this.trasee = null;
		this.zileOperare = null;
		
		this.discountDusIntors = discountDusIntors;
		this.discountLastMinute = discountLastMinute;
	}

	public String getNumeCompanie() {
		return this.numeCompanie;
	}
	
	public void setNumeCompanie(DatabaseConnection bd) {
		try {
			bd.sendQuery("SELECT CompanieAeriana FROM CURSE where codCursa='" + codCursa + "'");
			bd.rs.first();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public String getCodCursa() {
		return codCursa;
	}

	public void setCodCursa(String codCursa) {
		this.codCursa = codCursa;
	}

	public String getTipAvion() {
		return tipAvion;
	}

	public void setTipAvion(String tipAvion) {
		this.tipAvion = tipAvion;
	}

	public ClasaLocuri getLocuriBusiness() {
		return locuriBusiness;
	}

	public void setLocuriBusiness(ClasaLocuri locuriBusines) {
		this.locuriBusiness = locuriBusines;
	}

	public ClasaLocuri getLocuriEconomy() {
		return locuriEconomy;
	}

	public void setLocuriEconomy(ClasaLocuri locuriEconomy) {
		this.locuriEconomy = locuriEconomy;
	}

	public String getCodTraseu() {
		return codTraseu;
	}

	public void setCodTraseu(String codTraseu) {
		this.codTraseu = codTraseu;
	}
	
	public Vector<NodTraseu> getTrasee() {
		return trasee;
	}

	public void setTrasee(Vector<NodTraseu> trasee) {
		this.trasee = trasee;
	}

	public Vector<ZiOperare> getZileOperare() {
		return zileOperare;
	}

	public void setZileOperare(Vector <ZiOperare> zileOperare) {
		this.zileOperare = zileOperare;
	}
	
	public float getDiscountDusIntors() {
		return discountDusIntors;
	}

	public void setDiscountDusIntors(float discountDusIntors) {
		this.discountDusIntors = discountDusIntors;
	}

	public float getDiscountLastMinute() {
		return discountLastMinute;
	}

	public void setDiscountLastMinute(float discountLastMinute) {
		this.discountLastMinute = discountLastMinute;
	}

	public String getLocatii()
	{
		String locatii = "";
		for (NodTraseu traseu : trasee)
		{
			locatii += traseu.getNumeAeroport() + ";";
		}
		locatii = locatii.substring(0, locatii.length() - 1);
		return locatii;
	}
	
	public String getOreSosire()
	{
		String oreSosire = "";
		
		for (NodTraseu traseu : trasee)
		{
			oreSosire += traseu.getOraSosire() + ";";
		}
		oreSosire = oreSosire.substring(0, oreSosire.length() - 1);
		return oreSosire;
	}
	
	public String getOrePlecare()
	{
		String orePlecare = "";
		
		for (NodTraseu traseu : trasee)
		{
			orePlecare += traseu.getOraPlecare() + ";";
 		}
		orePlecare = orePlecare.substring(0, orePlecare.length() - 1);
		return orePlecare;
	}
	
	@Override
	public String toString() {
		return "Cursa [codCursa=" + codCursa + ", tipAvion=" + tipAvion + ", locuriBusines=" + locuriBusiness
				+ ", locuriEconomy=" + locuriEconomy + ", trasee=" + trasee.toString() + ", zileOperare="
				+ zileOperare.toString() +  ", discountDusIntors=" + discountDusIntors
				+ ", discountLastMinute=" + discountLastMinute +  "]";
	}

}
