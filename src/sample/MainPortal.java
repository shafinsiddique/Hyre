package sample;


import java.util.ArrayList;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class MainPortal {
    ArrayList<Applicant> jobApplicantUsers; //Applicant
    ArrayList<Company> companies; // Applicant, Employee
    ArrayList<Referee> refereeUsers; // Applicant, Employee

    MongoDatabase database; // Applicant, Employee
    MongoCollection applicantsCollection; // Applicant
    MongoCollection companiesCollection;

    UserCollectionHelper applicantsDatabaseHelper; // Applicant, Employee
    CompanyCollectionHelper companyDatabaseHelper;

    /**
     * The MainPortal is a text-based system for use by an Applicants, a Coordinator or an Interviewer
     *
     * @param db                       a MongoDB cloud database
     * @param applicantCollectionsName a String pointing to table in MongoDB database
     * @param companyCollectionsName   a String pointing to table in MongoDB database
     */

    MainPortal(MongoDatabase db, String applicantCollectionsName, String companyCollectionsName) throws IOException {
        this.database = db;
        this.applicantsCollection = database.getCollection(applicantCollectionsName);
        this.companiesCollection = database.getCollection(companyCollectionsName);

        applicantsDatabaseHelper = new UserCollectionHelper(this, applicantsCollection);
        companyDatabaseHelper = new CompanyCollectionHelper(this, companiesCollection);

        this.companies = companyDatabaseHelper.loadCompanies();
        this.jobApplicantUsers = applicantsDatabaseHelper.loadApplicants();
    }

    /**
     * Returns Company object given the the name of the company.
     *
     * @param companyName a String of the name of the company
     */
    protected Company findCompany(String companyName) {
        for (Company company : companies) {
            if (company.getName().equals(companyName)) {
                return company;
            }
        }
        return new Company();
    }

//    protected ReferenceLetter findReferenceLetter(String applicant) {
//        for (ReferenceLetter referenceLetter : referenceLetters) {
//            if (referenceLetter.getApplicant().equals(applicant)) {
//                return referenceLetter;
//            }
//        }
//        return new ReferenceLetter();
//    }

    protected Applicant findApplicant(String username) {
        for (Applicant applicant : jobApplicantUsers) {
            if (applicant.getUsername().equals(applicant)) {
                return applicant;
            }
        }
        return new Applicant();
    }

//    private void displayLoginScreen(String userType) throws IOException {
//    }

    protected ArrayList<Posting> allPostings() {
        ArrayList<Posting> p = new ArrayList<>();

        for (Company company : this.companies) {
            p.addAll(company.getPostings());
        }

        return p;

    }


}
