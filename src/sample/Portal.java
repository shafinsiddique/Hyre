package sample;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class Portal {
    ArrayList<Applicant> jobApplicantUsers; //Applicant
    ArrayList<Company> companies; // Applicant, Employee
    ArrayList<Referee> refereeUsers; // Applicant, Employee

    MongoDatabase database; // Applicant, Employee
    MongoCollection applicantsCollection; // Applicant
    MongoCollection companiesCollection;
    MongoCollection refereesCollection;

    UserCollectionHelper applicantsDatabaseHelper; // Applicant, Employee
    CompanyCollectionHelper companyDatabaseHelper;

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


    ApplicantPortalInterface login(Applicant a) {
        return new ApplicantPortalInterface(a, getAllPostings(),
                applicantsCollection);
    }




}