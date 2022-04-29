package firstpackage;

import java.util.Arrays;

public class CompanieAeriana {
	private Cursa[] curse;
	private float discountDusIntors;
	private float discountLastMinute;
	
	public CompanieAeriana(Cursa[] curse, float discountDusIntors, float discountLastMinute) {
		this.curse = curse;
		this.discountDusIntors = discountDusIntors;
		this.discountLastMinute = discountLastMinute;
	}

	public Cursa[] getCurse() {
		return curse;
	}

	public void setCurse(Cursa[] curse) {
		this.curse = curse;
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
		return "CompanieAeriana [curse=" + Arrays.toString(curse) + ", discountDusIntors=" + discountDusIntors
				+ ", discountLastMinute=" + discountLastMinute + "]";
	}
	
	
}
