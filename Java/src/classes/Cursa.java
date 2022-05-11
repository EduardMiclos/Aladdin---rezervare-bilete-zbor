package classes;

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

	@Override
	public String toString() {
		return "Cursa [codCursa=" + codCursa + ", tipAvion=" + tipAvion + ", locuriBusines=" + locuriBusiness
				+ ", locuriEconomy=" + locuriEconomy + ", trasee=" + trasee.toString() + ", zileOperare="
				+ zileOperare.toString() +  ", discountDusIntors=" + discountDusIntors
				+ ", discountLastMinute=" + discountLastMinute +  "]";
	}

}
