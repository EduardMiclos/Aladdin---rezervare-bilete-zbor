package firstpackage;

import java.util.Arrays;

public class Cursa {
	private String codCursa;
	private String tipAvion;
	private ClasaLocuri locuriBusines;
	private ClasaLocuri locuriEconomy;
	private NodTraseu[] trasee;
	private ZiOperare[] zileOperare;
	
	public Cursa(String codCursa, String tipAvion, ClasaLocuri locuriBusines, ClasaLocuri locuriEconomy,
			NodTraseu[] trasee, ZiOperare[] zileOperare) {
		super();
		this.codCursa = codCursa;
		this.tipAvion = tipAvion;
		this.locuriBusines = locuriBusines;
		this.locuriEconomy = locuriEconomy;
		this.trasee = trasee;
		this.zileOperare = zileOperare;
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

	public ClasaLocuri getLocuriBusines() {
		return locuriBusines;
	}

	public void setLocuriBusines(ClasaLocuri locuriBusines) {
		this.locuriBusines = locuriBusines;
	}

	public ClasaLocuri getLocuriEconomy() {
		return locuriEconomy;
	}

	public void setLocuriEconomy(ClasaLocuri locuriEconomy) {
		this.locuriEconomy = locuriEconomy;
	}

	public NodTraseu[] getTrasee() {
		return trasee;
	}

	public void setTrasee(NodTraseu[] trasee) {
		this.trasee = trasee;
	}

	public ZiOperare[] getZileOperare() {
		return zileOperare;
	}

	public void setZileOperare(ZiOperare[] zileOperare) {
		this.zileOperare = zileOperare;
	}

	@Override
	public String toString() {
		return "Cursa [codCursa=" + codCursa + ", tipAvion=" + tipAvion + ", locuriBusines=" + locuriBusines
				+ ", locuriEconomy=" + locuriEconomy + ", trasee=" + Arrays.toString(trasee) + ", zileOperare="
				+ Arrays.toString(zileOperare) + "]";
	}
	
	
}
