package assignment;
import java.util.List;

public class Article {
	
	//Instance variables
	private int articleID;
	private String name;
	private String issn;
	private int volume;
	private int edition;
	private String pageRange;
	private String abstractPara;
	private String pdfLink;
	private String respondName;
	private String respondEmail;
	private List<Author> coAuthors;
	
	//Constructor
	public Article(int articleID, String name, String issn, int volume, int edition, String pageRange, String abstractPara, String pdfLink, String respondName, String respondEmail, List<Author> coAuthors) {
		this.articleID = articleID;
		this.name = name;
		this.issn = issn;
		this.volume = volume;
		this.edition = edition;
		this.pageRange = pageRange;
		this.abstractPara = abstractPara;
		this.pdfLink = pdfLink;
		this.respondName = respondName;
		this.respondEmail = respondEmail;
		this.coAuthors = coAuthors;
	}
	
	//Empty constructor
	public Article() {
		
	}
	
	//Get Methods
	public int getArticleID() {
		return this.articleID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getISSN() {
		return this.issn;
	}
	
	public int getVolume() {
		return this.volume;
	}
	
	public int getEdition() {
		return this.edition;
	}
	
	public String getPageRange() {
		return this.pageRange;
	}
	
	public String getAbstractPara() {
		return this.abstractPara;
	}
	
	public String getPdfLink() {
		return this.pdfLink;
	}
	
	public String getRespondName() {
		return this.respondName;
	}
	
	public String getRespondEmail() {
		return this.respondEmail;
	}
	
	public List<Author> getCoAuthors() {
		return this.coAuthors;
	}
	
	//Set Methods
	public void setArticleID(int articleID) {
		this.articleID = articleID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setISSN(String issn) {
		this.issn = issn;
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public void setEdition(int edition) {
		this.edition = edition;
	}
	
	public void setPageRange(String pageRange) {
		this.pageRange = pageRange;
	}
	
	public void setAbstractPara(String abstractPara) {
		this.abstractPara = abstractPara;
	}
	
	public void setPdfLink(String pdfLink) {
		this.pdfLink = pdfLink;
	}
	
	public void setRespondName(String respondName) {
		this.respondName = respondName;
	}
	
	public void setRespondEmail(String respondEmail) {
		this.respondEmail = respondEmail;
	}
	
	public void setCoAuthors(List<Author> coAuthors) {
		this.coAuthors = coAuthors;
	}
}