package firstpackage;

import java.util.Calendar;

public class ZiOperare {
	private Calendar data;
	private Integer locuriRamaseB;
	private Integer locuriRamaseA;
	
	public ZiOperare(Calendar data, Integer locuriRamaseB, Integer locuriRamaseA) {
		this.data = data;
		this.locuriRamaseB = locuriRamaseB;
		this.locuriRamaseA = locuriRamaseA;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Integer getLocuriRamaseB() {
		return locuriRamaseB;
	}

	public void setLocuriRamaseB(Integer locuriRamaseB) {
		this.locuriRamaseB = locuriRamaseB;
	}

	public Integer getLocuriRamaseA() {
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
