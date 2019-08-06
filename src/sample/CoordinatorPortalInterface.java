package sample;

import com.mongodb.client.MongoCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class CoordinatorPortalInterface {
    private Coordinator coordinator;
    private Company company;
    private MongoCollection usersCollection;
    private MongoCollection companiesCollection;

    CoordinatorPortalInterface(Coordinator c, MongoCollection userCollection, MongoCollection companyCollection) {
        this.coordinator = c;
        this.company = c.getCompany();
        this.usersCollection = userCollection;
        this.companiesCollection = companyCollection;
    }

    private boolean checkValidPostingsID(int id) {
        for (Posting p : company.getPostings()) {
            if (p.getPostingID() == id) {
                return false;
            }
        }
        return true;
    }

    /**
     * A method to add an Interview to the database.
     *
     * @param i an Interview
     */
    private void addInterviewToDatabase(Interview i) {
        UserCollectionHelper writer = new UserCollectionHelper(i.getApplicant(), usersCollection);
        writer.addInterview(i);
        writer.updateApplicationStatus(i.getPosting(), i.getCurrentRoundType());
    }

    private void addPostingToDatabase(Posting p) {
        CompanyCollectionHelper helper = new CompanyCollectionHelper(company, companiesCollection);
        helper.addPosting(p);
    }

    protected void createPosting(int postingID, String position, String description, String requirements,
                                      Date expiryDate, String items, String location) throws IOException {

        ArrayList<String> rounds = new ArrayList<>(Arrays.asList(items.split(", ")));

        Posting p = new Posting(postingID, position, description, requirements, company, expiryDate, rounds, location);
        p.addLocation(location);
        p.setOpeningDate(new Date());
        coordinator.addPosting(p);
        addPostingToDatabase(p);

    }
    private ArrayList<String> viewAllPostings() {
        ArrayList<String> postings = new ArrayList<String>();
        for (Posting p : company.getPostings()) {
            postings.add(p.getAllInfo());
        }
        return postings;
    }

    private void sendMessageToApplicant(Applicant a, String m) {
        a.addMessage(m);
        UserCollectionHelper statusUpdater = new UserCollectionHelper(a, usersCollection);
        statusUpdater.addMessage(m);
    }

    private void scheduleInterviewScreen(Posting p, Applicant a, Interviewer i, Date d) {
        viewAllPostings();
        Interview newInterview = coordinator.scheduleInterview(p, a, i, d);
        addInterviewToDatabase(newInterview);
        sendMessageToApplicant(a, "You have been scheduled an interview with " +
                newInterview.getPosting().getCompanyName()
                + " for the position of " + newInterview.getPosting().getPosition());
    }

    private ArrayList<String> viewAllInterviewers(){
        ArrayList<String> interviewers = new ArrayList<String>();
        for (Interviewer i : company.getInterviewers()) {
            interviewers.add(i.getUsername());
        }
        return interviewers;
    }
}
