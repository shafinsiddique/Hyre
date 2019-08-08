package sample;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.scene.Scene;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;

public class Portal {
    private ArrayList<Applicant> jobApplicantUsers; //Applicant
    private ArrayList<Company> companies; // Applicant, Employee
    private ArrayList<Referee> refereeUsers; // Applicant, Employee

    private MongoDatabase database; // Applicant, Employee
    private MongoCollection applicantsCollection; // Applicant
    private MongoCollection companiesCollection;
    private MongoCollection refereesCollection;

    private UserCollectionHelper applicantsDatabaseHelper; // Applicant, Employee
    private CompanyCollectionHelper companyDatabaseHelper;
    private static String interviewer = "Interviewer";
    private static String coordinator = "Coordinator";
    private static String applicant = "Applicant";

    private static String userType;
    /**
     * The MainPortal is a text-based system for use by an Applicants, a Coordinator or an Interviewer
     *
     * @param db                       a MongoDB cloud database
     * @param applicantCollectionsName a String pointing to table in MongoDB database
     * @param companyCollectionsName   a String pointing to table in MongoDB database
     */

    Portal(MongoDatabase db, String applicantCollectionsName, String companyCollectionsName) throws IOException {
        this.database = db;
        this.applicantsCollection = database.getCollection(applicantCollectionsName);
        this.companiesCollection = database.getCollection(companyCollectionsName);

        applicantsDatabaseHelper = new UserCollectionHelper(this, applicantsCollection);
        companyDatabaseHelper = new CompanyCollectionHelper(this, companiesCollection);

        this.companies = companyDatabaseHelper.loadCompanies();
        this.jobApplicantUsers = applicantsDatabaseHelper.loadApplicants();
    }

    protected Applicant findApplicant(String username, String password) {
        for (Applicant jobApplicantUser : jobApplicantUsers) {
            if (jobApplicantUser.getUsername().equals(username)) {
                if (jobApplicantUser.getPassword().equals(password)) {
                    return jobApplicantUser;
                }
            }
        }
        return new Applicant();
    }

    protected Company findCompany(String companyName) {
        for (Company company : companies) {
            if (company.getName().equals(companyName)) {
                return company;
            }
        }
        return new Company();
    }


    private ArrayList<Posting>getAllPostings() {
        ArrayList<Posting> p = new ArrayList<>();

        for (Company company : this.companies) {
            p.addAll(company.getPostings());
        }

        return p;

    }


    ApplicantPortalInterface Applicantlogin(Applicant a) {
        return new ApplicantPortalInterface(a, getAllPostings(),
                applicantsCollection);
    }

    protected void registerApplicant(Applicant a) {
        jobApplicantUsers.add(a);
        Document document = new Document();
        document.append("username", a.username);
        document.append("password", a.password);
        document.append("dateCreated", a.getDateCreated());
        document.append("resume", a.getResume());
        document.append("coverLetter", a.getCoverLetter());
        document.append("appliedTo", new ArrayList<>());
        document.append("interviews", new ArrayList<>());
        applicantsDatabaseHelper.writeDocument(document);
    }

    protected CoordinatorPortalInterface registerCoordinator(Coordinator coord, Company company){
        if(!companies.contains(company)){
            companies.add(company);
            companyDatabaseHelper.addNewCompany(company);
        }
        companyDatabaseHelper.addCoordinator(coord);
        return new CoordinatorPortalInterface(coord,applicantsCollection,companiesCollection);
    }

    protected InterviewerPortalInterface registerInterviewer(Interviewer interviewer, Company company){
        companyDatabaseHelper.addInterviewer(interviewer);
        company.addInterviewer(interviewer);
        return new InterviewerPortalInterface(interviewer, applicantsCollection);
    }

    protected Coordinator findCoordinator(String username, String password) {
        for (Company company : companies) {
            if (!company.findCoordinator(username, password).isEmpty()) {
                return company.findCoordinator(username, password);
            }
        }
        return new Coordinator();
    }

    protected Interviewer findInterviewer(String username, String password) {
        for (Company company : companies) {
            if (!company.findInterviewer(username, password).isEmpty()) {
                return company.findInterviewer(username, password);
            }
        }
        return new Interviewer();
    }

    protected InterviewerPortalInterface interviewerLogin(Interviewer i){
        return new InterviewerPortalInterface(i, applicantsCollection);
    }

    protected CoordinatorPortalInterface coordinatorLogin(Coordinator c) {
        return new CoordinatorPortalInterface(c,applicantsCollection,companiesCollection);

    }
    protected static String getInterviewer(){return interviewer;}

    protected static String getCoordinator(){return coordinator;}

    protected static String getApplicant(){return applicant;}

    protected static void setUserType(String type){
        userType = type;
    }

    protected static String getUserType(){
        return userType;
    }






}