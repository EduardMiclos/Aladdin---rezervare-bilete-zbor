package classes;

import java.util.Arrays;
import java.util.Vector;

public class CompanieAeriana {
	private String numeCompanie;
	private Vector<Cursa> curse;
	
	public CompanieAeriana() {
		numeCompanie = "";
		curse = null;
	}
	
	public CompanieAeriana(String numeCompanie) {
		this.numeCompanie = numeCompanie;
	}
	
	public CompanieAeriana(Vector<Cursa> curse) {
		this.curse = curse;
	}
	
	public CompanieAeriana(String numeCompanie, Vector<Cursa> curse) {
		this.numeCompanie = numeCompanie;
		this.curse = curse;
	}

	public String getNumeCompanie() {
		return numeCompanie;
	}

	public void setNumeCompanie(String numeCompanie) {
		this.numeCompanie = numeCompanie;
	}
	
	public Vector<Cursa> getCurse() {
		return curse;
	}

	public void setCurse(Vector<Cursa> curse) {
		this.curse = curse;
	}

	@Override
	public String toString() {
		return "CompanieAeriana [curse=" + curse.toString() + "]";
	}
	
	
}
