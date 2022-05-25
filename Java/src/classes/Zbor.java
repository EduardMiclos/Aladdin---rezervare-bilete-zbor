package classes;

import java.time.LocalDateTime;
import java.util.Date;

public class Zbor {
	String codZbor, codCursa, companieAeriana;
	int businessRamase, economyRamase;
	float pretEconomy, pretBusiness;
	float discountDusIntors, discountLastMinute;
	String aeroportPlecare, aeroportSosire;
	LocalDateTime dataPlecare, dataSosire;
	
	public Zbor(String codZbor, String codCursa, String companieAeriana, int businessRamase, int economyRamase, float pretBusiness, float pretEconomy, float discountDusIntors, float discountLastMinute, String aeroportPlecare, String aeroportSosire, LocalDateTime dataPlecare, LocalDateTime dataSosire) {
		super();
		this.codZbor = codZbor;
		this.codCursa = codCursa;
		this.companieAeriana = companieAeriana;
		this.businessRamase = businessRamase;
		this.economyRamase = economyRamase;
		this.pretBusiness = pretBusiness;
		this.pretEconomy = pretEconomy;
		this.discountDusIntors = discountDusIntors;
		this.discountLastMinute = discountLastMinute;
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

	public String getCompanieAeriana() {
		return companieAeriana;
	}

	public void setCompanieAeriana(String companieAeriana) {
		this.companieAeriana = companieAeriana;
	}

	public float getPretEconomy() {
		return pretEconomy;
	}

	public void setPretEconomy(float pretEconomy) {
		this.pretEconomy = pretEconomy;
	}

	public float getPretBusiness() {
		return pretBusiness;
	}

	public void setPretBusiness(float pretBusiness) {
		this.pretBusiness = pretBusiness;
	}

	public void setDataSosire(LocalDateTime dataSosire) {
		this.dataSosire = dataSosire;
	}

	public float getDiscountDusIntors() {
		return discountDusIntors;
	}
	
	public String getOrasPlecare() {
		return this.aeroportPlecare.split(",")[0];
	}
	
	public String getOrasSosire() {
		return this.aeroportSosire.split(",")[0];
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
	
}
