package classes;

import java.util.Calendar;
import java.util.Date;

public class ZiOperare {
	private Date data;
	private int locuriRamaseB;
	private int locuriRamaseA;
	
	public ZiOperare(Date data, int locuriRamaseB, int locuriRamaseA) {
		this.data = data;
		this.locuriRamaseB = locuriRamaseB;
		this.locuriRamaseA = locuriRamaseA;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getLocuriRamaseB() {
		return locuriRamaseB;
	}

	public void setLocuriRamaseB(Integer locuriRamaseB) {
		this.locuriRamaseB = locuriRamaseB;
	}

	public int getLocuriRamaseA() {
		return locuriRamaseA;
	}

	public void setLocuriRamaseA(Integer locuriRamaseA) {
		this.locuriRamaseA = locuriRamaseA;
	}

	@Override
	public String toString() {
		return "ZiOperare [data=" + data + ", locuriRamaseB=" + locuriRamaseB + ", locuriRamaseA=" + locuriRamaseA
				+ "]";
	}
	
	
}
