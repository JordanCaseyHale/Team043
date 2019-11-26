package assignment;
import java.util.List;

public class Submission extends Article {

	//Instances Variables
	private String[] reviews;
	private String[] verdicts;
	private String[] responses;
	private String journal;
	
	//Constructor
	public Submission(String name, int issn, int volume, int edition, String pageRange, String abstractPara, String pdfLink, String respondName, String respondEmail,
			List<List<String>> coAuthors, String[] reviews, String[] verdicts, String[] responses, String journal) {
		super(name, issn, volume, edition, pageRange, abstractPara, pdfLink, respondName, respondEmail, coAuthors);
		this.reviews = reviews;
		this.verdicts = verdicts;
		this.responses = responses;
		this.journal = journal;
	}
	
	//Get Methods
	public String[] getReviews() {
		return this.reviews;
	}
	
	public String[] getVerdicts() {
		return this.verdicts;
	}
	
	public String[] getResponses() {
		return this.responses;
	}
	
	public String getJournal() {
		return this.journal;
	}
	
	//Set Methods
	
	public void setReviews(String[] reviews) {
		this.reviews = reviews;
	}
	
	public void setVerdicts(String[] verdicts) {
		this.verdicts = verdicts;
	}
	
	public void setResponses(String[] responses) {
		this.responses = responses;
	}
	
	public void setJournal(String journal) {
		this.journal = journal;
	}
}