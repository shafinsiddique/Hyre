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
    protected ArrayList<String>getInProcessInterviews(){
        ArrayList<String>interviews = new ArrayList<>();

        for (Interview i: company.getAllInProcessInterviews()){
            interviews.add(i.getAllInfo());
        }

        return interviews;
    }

    protected Interview findInProcessInterview(int i){
        return company.getAllInProcessInterviews().get(i);
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

    ArrayList<String>viewAllApplicants(Posting P) {
        ArrayList<String>allApplicants = new ArrayList<>();
        for (Applicant a : P.getApplicantList()) {
            allApplicants.add(a.getAllInfo());
        }

        return allApplicants;

    }

    Applicant findApplicant(Posting P, int i){
        return P.getApplicantList().get(i);
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
    ArrayList<String> allPostingsString() {
        ArrayList<String> postings = new ArrayList<String>();
        for (Posting p : company.getPostings()) {
            postings.add(p.getAllInfo());
        }
        return postings;
    }

    Posting findPosting(int index) {
        return company.getPostings().get(index);
    }
    private void sendMessageToApplicant(Applicant a, String m) {
        a.addMessage(m);
        UserCollectionHelper statusUpdater = new UserCollectionHelper(a, usersCollection);
        statusUpdater.addMessage(m);
    }

    protected Interview scheduleInterview(Posting p, Applicant a, Interviewer i, Date d) {
        Interview newInterview = coordinator.scheduleInterview(p, a, i, d);
        addInterviewToDatabase(newInterview);
        sendMessageToApplicant(a, "You have been scheduled an interview with " +
                newInterview.getPosting().getCompanyName()
                + " for the position of " + newInterview.getPosting().getPosition());

        return newInterview;
    }

    protected ArrayList<String> viewAllInterviewers(){
        ArrayList<String> interviewers = new ArrayList<String>();
        for (Interviewer i : company.getInterviewers()) {
            interviewers.add(i.getUsername());
        }
        return interviewers;
    }

    protected Interviewer findInterviewer(int index){
        return company.getInterviewers().get(index);
    }

    protected void hire(Interview i) {
        UserCollectionHelper statusUpdater = new UserCollectionHelper(i.getApplicant(), usersCollection);
        coordinator.offerJob(i);
        statusUpdater.updateApplicationStatus(i.getPosting(), "Offer");
        sendMessageToApplicant(i.getApplicant(), "Congratulations! You have been offered the position of " +
                i.getPosting().getPosition() + " at " + i.getPosting().getCompanyName());
    }

    protected void reject(Interview i) {coordinator.rejectApplicant(i);
        UserCollectionHelper statusUpdater = new UserCollectionHelper(i.getApplicant(), usersCollection);
        statusUpdater.updateApplicationStatus(i.getPosting(), "Rejected");
        sendMessageToApplicant(i.getApplicant(), "You have been rejected for the position of " +
                i.getPosting().getPosition() +
                " at " + i.getPosting().getCompanyName());

    }

    protected void moveUpCandidate(Interview i, Date newDate, Interviewer newInterviewer) {
        i.moveToNextRound(newDate, newInterviewer);
        UserCollectionHelper uch = new UserCollectionHelper(i.getApplicant(), usersCollection);
        uch.updateInterviewRoundReview(i);
        sendMessageToApplicant(i.getApplicant(), "You have successfully moved on to the next round of " +
                "interviews for " + i.getPosting().getPosition()
                + " at " + i.getPosting().getCompanyName());
        uch.updateApplicationStatus(i.getPosting(), i.getCurrentRoundType());


    }
}


