package assignment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Submission {
	/**
	 * Checks if Submission still requires reviews
	 */
	public static int ReviewsLeft (Submission s) {
		int[] reviewsDone = s.getReviews();
		int reviewCounter = 0;
		for(int i = 0;i<3;i++) {
			if(reviewsDone[i] != 0) {
				reviewCounter++;
			}
		}
		return 3-reviewCounter;
	}
	/**
	 * Gets a Submission object based on a subID
	 */
	public static Submission getSubmissionByID(int subID) {
		Submission returnedSub = new Submission();
		try(Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team043","team043","38796815")){

			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM submission WHERE SubID = ?");
			pstmt.setInt(1, subID);
			ResultSet result = pstmt.executeQuery();
			String respondEmail = null;
			String subTitle = null;
			String subAbstract = null;
			String subLink = null;
			String subISSN = null;
			if (result.first()) {
				subTitle = result.getString(2);
				subAbstract = result.getString(3);
				subLink = result.getString(4);
				subISSN = result.getString(5);
				respondEmail = result.getString(6);
			}				
			PreparedStatement getSubAuthstmt = con.prepareStatement("SELECT account.Email,account.Title,account.Forename,account.Surname,account.Affiliation,account.Password FROM account"
					+ " INNER JOIN submissionAuthors USING(Email) WHERE submissionAuthors.SubID = ?");
			getSubAuthstmt.setInt(1,subID);
			ResultSet subAuthResult = getSubAuthstmt.executeQuery();
			
			ArrayList<Author> coauths = new ArrayList<Author>();
			while (subAuthResult.next()){
				Author a = new Author(subAuthResult.getString(2),subAuthResult.getString(3),subAuthResult.getString(4),subAuthResult.getString(1),subAuthResult.getString(5),subAuthResult.getString(6));
				coauths.add(a);
			}
			
			PreparedStatement revIDpstmt = con.prepareStatement("SELECT RevID,Initial_verdict,Verdict,Response FROM reviews WHERE SubID = ?");
			revIDpstmt.setInt(1, subID);
			int[] revIDs = new int[3];
			String[] initVers  = new String[3];
			String[] vers = new String[3];
			String[] resps = new String[3];
			int count = 0;
			ResultSet revIDresult = revIDpstmt.executeQuery();
			while (revIDresult.next()){
				if(count<3) {
					revIDs[count] = revIDresult.getInt(1);
					initVers[count] = revIDresult.getString(2);
					vers[count] = revIDresult.getString(3);
					resps[count] = revIDresult.getString(4);
					count++;
				}
			}			

			PreparedStatement getRespondAuthorstmt = con.prepareStatement("SELECT Title,Forename,Surname FROM account WHERE Email = ?");
			getRespondAuthorstmt.setString(1,respondEmail);
			ResultSet authResult = getRespondAuthorstmt.executeQuery();
			
			if (authResult.first()) {
				returnedSub = new Submission(subTitle,subAbstract,subLink,authResult.getString(1),authResult.getString(2),authResult.getString(3),respondEmail,coauths,subISSN,subID);
				returnedSub.setReviews(revIDs);
				returnedSub.setInitVerdicts(initVers);
				returnedSub.setVerdicts(vers);
				returnedSub.setResponses(resps);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return returnedSub;
	}
	
	//Instances Variables
	private int[] reviewIDs;
	private String[] verdicts;
	private String[] initVerdicts;
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
	private Boolean complete;
	
	//Default Constructor
	public Submission() {
		this.reviewIDs = new int[3];
		this.initVerdicts = new String[3];
		this.verdicts = new String[3];
		this.responses = new String[3];		
	}
	
	//Constructor
	public Submission(String name, String abstractPara, String pdfLink, String respondTitle, String respondForename, String respondSurname, String respondEmail,
			List<Author> coAuthors, int[] reviewIDs, String[] initverdicts, String[] verdicts,String[] responses, String ISSN, int subID, Boolean complete) {
		this.setName(name);
		this.abstractPara = abstractPara;
		this.pdfLink = pdfLink;
		this.respondTitle = respondTitle;
		this.respondForename = respondForename;
		this.respondSurname = respondSurname;
		this.respondEmail = respondEmail;
		this.coAuthors = coAuthors;
		this.reviewIDs = reviewIDs;
		this.initVerdicts = initverdicts;
		this.verdicts = verdicts;
		this.responses = responses;
		this.journal = ISSN;
		this.subID = subID;
		this.complete = complete;
	}
	//Partial Constructor 
	
	public Submission(String name, String abstractPara, String pdfLink, String respondTitle, String respondForename, String respondSurname, String respondEmail,
			List<Author> coAuthors, String ISSN,int subID) {
		this.setName(name);
		this.abstractPara = abstractPara;
		this.pdfLink = pdfLink;
		this.respondTitle = respondTitle;
		this.respondForename = respondForename;
		this.respondSurname = respondSurname;
		this.respondEmail = respondEmail;
		this.coAuthors = coAuthors;
		this.journal = ISSN;
		this.reviewIDs = new int[3];
		this.initVerdicts = new String[3];
		this.verdicts = new String[3];
		this.responses = new String[3];
		this.subID = subID;
		this.complete = false;
	}
	
	
	//Get Methods
	public int[] getReviews() {
		return this.reviewIDs;
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
	public String[] getInitVerdicts() {
		return initVerdicts;
	}

	public Boolean getComplete() {
		return complete;
	}
	
	//Set Methods
	
	public void setReviews(int[] reviews) {
		this.reviewIDs = reviews;
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
	
	public void setJournal(String ISSN) {
		this.journal = ISSN;
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

	public void setInitVerdicts(String[] initVerdicts) {
		this.initVerdicts = initVerdicts;
	}

	public void setComplete(Boolean complete) {
		this.complete = complete;
	}
	
	
}