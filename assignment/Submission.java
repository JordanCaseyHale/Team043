package assignment;
import java.util.List;

public class Submission {

	//Instances Variables
	private String[] reviews;
	private String[] verdicts;
	private String[] responses;
	private int subID;
	private String journal;
	private String name;
	private String abstractPara;
	private String pdfLink;
	private String respondTitle;
	private String respondForename;
	private String respondSurname;
	private String respondEmail;
	private List<Author> coAuthors;
	
	//Default Constructor
	public Submission() {
		
	}
	
	//Constructor
	public Submission(String name, String abstractPara, String pdfLink, String respondTitle, String respondForename, String respondSurname, String respondEmail,
			List<Author> coAuthors, String[] reviews, String[] verdicts, String[] responses, String journal, int subID) {
		this.setName(name);
		this.abstractPara = abstractPara;
		this.pdfLink = pdfLink;
		this.respondTitle = respondTitle;
		this.respondForename = respondForename;
		this.respondSurname = respondSurname;
		this.respondEmail = respondEmail;
		this.coAuthors = coAuthors;
		this.reviews = reviews;
		this.verdicts = verdicts;
		this.responses = responses;
		this.journal = journal;
		this.subID = subID;
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

	public String getRespondTitle() {
		return respondTitle;
	}
	
	public String getRespondForename() {
		return respondForename;
	}
	
	public String getRespondSurname() {
		return respondSurname;
	}

	public String getRespondEmail() {
		return respondEmail;
	}

	public List<Author> getCoAuthors() {
		return coAuthors;
	}
	
	public int getSubID() {
		return subID;
	}

	//Set Methods
	
	public void setReviews(String[] reviews) {
		this.reviews = reviews;
	}
	public void setCoAuthors(List<Author> coAuthors) {
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
	
	public void setRespondTitle(String respondTitle) {
		this.respondTitle = respondTitle;
	}
	
	public void setRespondForename(String respondForename) {
		this.respondForename = respondForename;
	}
	
	public void setRespondSurname(String respondSurname) {
		this.respondSurname = respondSurname;
	}
	
	public void setRespondEmail(String respondEmail) {
		this.respondEmail = respondEmail;
	}
	
	public void setSubID(int subID) {
		this.subID = subID;
	}
}