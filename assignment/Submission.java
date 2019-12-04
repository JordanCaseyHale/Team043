package assignment;
import java.util.List;

public class Submission {

	//Instances Variables
	private String[] reviews;
	private String[] verdicts;
	private String[] responses;
	private String journal;
	private String name;
	private String abstractPara;
	private String pdfLink;
	private String respondname;
	private String respondEmail;
	private List<List<String>> coAuthors;
	
	//Constructor
	public Submission(String name, String abstractPara, String pdfLink, String respondName, String respondEmail,
			List<List<String>> coAuthors, String[] reviews, String[] verdicts, String[] responses, String journal) {
		this.setName(name);
		this.abstractPara = abstractPara;
		this.pdfLink = pdfLink;
		this.respondname = respondName;
		this.respondEmail = respondEmail;
		this.coAuthors = coAuthors;
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

	public String getName() {
		return this.name;
	}	
	
	public String getAbstractPara() {
		return abstractPara;
	}

	public String getPdfLink() {
		return pdfLink;
	}

	public String getRespondname() {
		return respondname;
	}

	public String getRespondEmail() {
		return respondEmail;
	}

	public List<List<String>> getCoAuthors() {
		return coAuthors;
	}

	//Set Methods
	
	public void setReviews(String[] reviews) {
		this.reviews = reviews;
	}
	public void setCoAuthors(List<List<String>> coAuthors) {
		this.coAuthors = coAuthors;
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

	public void setName(String name) {
		this.name = name;
	}	
	
	public void setAbstractPara(String abstractPara) {
		this.abstractPara = abstractPara;
	}

	public void setPdfLink(String pdfLink) {
		this.pdfLink = pdfLink;
	}
	
	public void setRespondname(String respondname) {
		this.respondname = respondname;
	}
	
	public void setRespondEmail(String respondEmail) {
		this.respondEmail = respondEmail;
	}
	




}