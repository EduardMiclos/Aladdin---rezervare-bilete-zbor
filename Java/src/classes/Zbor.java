package classes;

import java.time.LocalDateTime;
import java.util.Date;

public class Zbor {
	String codZbor, codCursa;
	int businessRamase, economyRamase;
	String aeroportPlecare, aeroportSosire;
	LocalDateTime dataPlecare, dataSosire;
	
	public Zbor(String codZbor, String codCursa, int businessRamase, int economyRamase, String aeroportPlecare, String aeroportSosire, LocalDateTime dataPlecare, LocalDateTime dataSosire) {
		super();
		this.codZbor = codZbor;
		this.codCursa = codCursa;
		this.businessRamase = businessRamase;
		this.economyRamase = economyRamase;
		this.aeroportPlecare = aeroportPlecare;
		this.aeroportSosire = aeroportSosire;
		this.dataPlecare = dataPlecare;
		this.dataSosire = dataSosire;
	}

	public String getCodZbor() {
		return codZbor;
	}

	public void setCodZbor(String codZbor) {
		this.codZbor = codZbor;
	}

	public String getCodCursa() {
		return codCursa;
	}

	public void setCodCursa(String codCursa) {
		this.codCursa = codCursa;
	}

	public int getBusinessRamase() {
		return businessRamase;
	}

	public void setBusinessRamase(int businessRamase) {
		this.businessRamase = businessRamase;
	}

	public int getEconomyRamase() {
		return economyRamase;
	}

	public void setEconomyRamase(int economyRamase) {
		this.economyRamase = economyRamase;
	}
	

	public String getAeroportPlecare() {
		return aeroportPlecare;
	}

	public void setAeroportPlecare(String aeroportPlecare) {
		this.aeroportPlecare = aeroportPlecare;
	}

	public String getAeroportSosire() {
		return aeroportSosire;
	}

	public void setAeroportSosire(String aeroportSosire) {
		this.aeroportSosire = aeroportSosire;
	}

	public LocalDateTime getDataPlecare() {
		return dataPlecare;
	}

	public void setDataPlecare(LocalDateTime dataPlecare) {
		this.dataPlecare = dataPlecare;
	}

	public LocalDateTime getDataSosire() {
		return dataSosire;
	}

	public void setdataSosire(LocalDateTime dataSosire) {
		this.dataSosire = dataSosire;
	}
}
