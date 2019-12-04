package assignment;

public class Volume {
	
	//Instance Variables
	private String issn;
	private int volume;
	private int year;
	
	//Constructor
	public Volume(String issn, int volume, int year) {
		this.issn = issn;
		this.volume = volume;
		this.year = year;
	}
	
	//Empty constructor
	public Volume() {
		
	}
	
	//Get methods
	public String getISSN() {
		return this.issn;
	}
	
	public int getVolume() {
		return this.volume;
	}
	
	public int getYear() {
		return this.year;
	}
	
	//Set methods
	public void setISSN(String issn) {
		this.issn = issn;
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
}