package sample;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Portal {
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

    Portal(MongoDatabase db, String applicantCollectionsName, String companyCollectionsName) {
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
    Company findCompany(String companyName) {
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

    Applicant findApplicant(String username) {
        for (Applicant applicant : jobApplicantUsers) {
            if (applicant.getUsername().equals(username)) {
                return applicant;
            }
        }
        return new Applicant();
    }

    protected ArrayList<Posting> allPostings() {
        ArrayList<Posting> p = new ArrayList<>();

        for (Company company : this.companies) {
            p.addAll(company.getPostings());
        }

        return p;

    }

    void activityTypePage(String usertype) {
        Button login = new Button();
        login.setText("Login");
        login.setOnAction(e -> {displayLoginScreen(usertype);});

        Button register = new Button();
        register.setText("Register");
        register.setOnAction( e -> {displayRegisterScreen(usertype);});

        Button mainMenu = new Button();
        mainMenu.setText("Main Menu");
        mainMenu.setOnAction(e -> {Main.showHomePage();});

        GridPane pageLayout = new GridPane();
        pageLayout.setAlignment(Pos.CENTER);
        pageLayout.setHgap(5);
        pageLayout.add(login, 0,0);
        pageLayout.add(register, 1, 0);
        pageLayout.add(mainMenu, 2, 0);

        Scene pageScene = new Scene(pageLayout, 400, 150);
        Stage window = new Stage();
        window.setScene(pageScene);
    }

    void displayLoginScreen(String userType) {}

    void displayRegisterScreen(String userType) {}
}