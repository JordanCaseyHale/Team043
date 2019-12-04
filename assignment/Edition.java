package assignment;

public class Edition {
	
	//instance Variables
	private String issn;
	private int volume;
	private int edition;
	private String month;
	
	//Constructor
	public Edition(String issn, int volume, int edition, String month) {
		this.issn = issn;
		this.volume = volume;
		this.edition = edition;
		this.month = month;
	}
	
	//Empty constructor
	public Edition() {
		
	}
	
	//Get methods
	public String getISSN() {
		return this.issn;
	}
	
	public int getVolume() {
		return this.volume;
	}
	
	public int getEdition() {
		return this.edition;
	}
	
	public String getMonth() {
		return this.month;
	}
	
	//Set methods
	public void setISSN(String issn) {
		this.issn = issn;
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public void setEdition(int edition) {
		this.edition = edition;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
}