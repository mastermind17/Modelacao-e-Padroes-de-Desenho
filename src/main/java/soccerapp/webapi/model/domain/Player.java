package soccerapp.webapi.model.domain;

public class Player {

	private String name;
	private String position;
	private int jerseyNumber;
	private String dateOfBirth;
	private String nationality;
	private String contractUntil;
	private String marketValue;

	public Player(String name, String position, int jerseyNumber, String dateOfBirth, String nationality,
				  String contractUntil, String marketValue) {
		this.name = name;
		this.position = position;
		this.jerseyNumber = jerseyNumber;
		this.dateOfBirth = dateOfBirth;
		this.nationality = nationality;
		this.contractUntil = contractUntil;
		this.marketValue = marketValue;
	}

	public String getName() {
		return name;
	}

	public String getPosition() {
		return position;
	}

	public int getJerseyNumber() {
		return jerseyNumber;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getNationality() {
		return nationality;
	}

	public String getContractUntil() {
		return contractUntil;
	}

	public String getMarketValue() {
		return marketValue;
	}
}
