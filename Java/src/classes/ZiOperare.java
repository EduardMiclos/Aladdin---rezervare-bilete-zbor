package classes;

import java.util.Calendar;
import java.util.Date;

public class ZiOperare {
	private Date data;
	private int locuriRamaseBusiness;
	private int locuriRamaseEconomy;
	
	public ZiOperare(Date data, int locuriRamaseBusiness, int locuriRamaseEconomy) {
		this.data = data;
		this.locuriRamaseBusiness = locuriRamaseBusiness;
		this.locuriRamaseEconomy = locuriRamaseEconomy;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getLocuriRamaseBusiness() {
		return locuriRamaseBusiness;
	}

	public void setLocuriRamaseBusiness(Integer locuriRamaseBusiness) {
		this.locuriRamaseBusiness = locuriRamaseBusiness;
	}

	public int getLocuriRamaseEconomy() {
		return locuriRamaseEconomy;
	}

	public void setLocuriRamaseEconomy(Integer locuriRamaseEconomy) {
		this.locuriRamaseEconomy = locuriRamaseEconomy;
	}

	@Override
	public String toString() {
		return "ZiOperare [data=" + data + ", locuriRamaseB=" + locuriRamaseBusiness + ", locuriRamaseA=" + locuriRamaseEconomy
				+ "]";
	}
	
	
}
