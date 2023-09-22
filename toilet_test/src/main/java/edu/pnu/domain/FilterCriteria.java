package edu.pnu.domain;

public class FilterCriteria {
    private String city;
    private String county;
    private String disabledToilet;
    private String nappyToilet;

    // 생성자
    public FilterCriteria() {
    }

    public FilterCriteria(String city, String county, String disabledToilet, String nappyToilet) {
        this.city = city;
        this.county = county;
        this.disabledToilet = disabledToilet;
        this.nappyToilet = nappyToilet;
    }

    // Getter 및 Setter 메서드
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getDisabledToilet() {
		return disabledToilet;
	}

	public void setDisabledToilet(String disabledToilet) {
		this.disabledToilet = disabledToilet;
	}

	public String getNappyToilet() {
		return nappyToilet;
	}

	public void setNappyToilet(String nappyToilet) {
		this.nappyToilet = nappyToilet;
	}


    

}
