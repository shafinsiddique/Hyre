package sample;

import java.io.*;
import java.util.ArrayList;

class Company {
    private String name;
    private ArrayList<Posting> postings;
    private ArrayList<Interviewer> interviewers;
    private ArrayList<Coordinator> coordinators;

    protected Company(String name) {

        this.name = name;
        this.postings = new ArrayList<>();
        this.interviewers = new ArrayList<>();
        this.coordinators = new ArrayList<>();
    }

    protected Company() {
        this.postings = new ArrayList<>();
        this.interviewers = new ArrayList<>();
        this.coordinators = new ArrayList<>();
    }

    protected ArrayList<Coordinator> getCoordinators() {
        return this.coordinators;
    }

    protected void setPostings(ArrayList<Posting> postings) {
        this.postings = postings;
    }

    protected void setInterviewers(ArrayList<Interviewer> interviewers) {
        this.interviewers = interviewers;
    }

    protected void setCoordinators(ArrayList<Coordinator> coordinators) {

        this.coordinators = coordinators;

    }

    protected ArrayList<Interview> getAllInProcessInterviews() {
        ArrayList<Interview> interview = new ArrayList<>();

        for (Posting p : postings) {
            interview.addAll(p.getInProcessInterviews());
        }

        return interview;
    }

    /**
     * A method to find an Interviewer in the system.
     *
     * @param username a String of the Interviewer's username
     * @return Interviewer
     */
    protected Interviewer findInterviewer(String username) {
        for (int x = 0; x < getInterviewers().size(); x++) {
            if (getInterviewers().get(x).getUsername().equals(username)) {
                return getInterviewers().get(x);
            }
        }
        System.out.println("No such interviewer exists.");
        return new Interviewer();
    }


    /**
     * A method to find a Posting in the system.
     *
     * @param postingID an Integer of the postingID
     * @return Posting
     */
    protected Posting findPostingWithID(int postingID) {
        for (Posting p : postings) {
            if (p.getPostingID() == postingID) {
                return p;
            }
        }
        return new Posting();
    }



    protected ArrayList<Interviewer> getInterviewers() {
        return this.interviewers;
    }

    protected ArrayList<Posting> getPostings() {
        return this.postings;
    }

    protected Boolean isEmpty() {
        return this.name == null;
    }

    protected String getName() {
        return this.name;
    }

    protected Coordinator findCoordinator(String username, String password) {
        for (Coordinator coordinator : this.coordinators) {
            if (coordinator.getUsername().equals(username)) {
                if (coordinator.getPassword().equals(password)) {
                    return coordinator;
                }
            }
        }
        return new Coordinator();
    }

    /**
     * A method to find an Interviewer in the system.
     *
     * @param username a String of the Interviewer's username
     * @param password a String of the Interviewer's password
     * @return Interviewer
     */
    protected Interviewer findInterviewer(String username, String password) {
        for (Interviewer interviewer : this.interviewers) {
            if (interviewer.getUsername().equals(username)) {
                if (interviewer.getPassword().equals(password)) {
                    return interviewer;
                }
            }
        }
        return new Interviewer();
    }

    /**
     * A method to add a Coordinator.
     *
     * @param c an Coordinator
     */
    protected void addCoordinator(Coordinator c) {
        this.coordinators.add(c);
        String line = c.getUsername() + ", " + c.getPassword() + "\n";
    }

    /**
     * A method to add an Interviewer.
     *
     * @param i an Interviewer
     */
    protected void addInterviewer(Interviewer i) {
        this.interviewers.add(i);
        String line = i.getUsername() + ", " + i.getPassword() + "\n";
    }


    /**
     * A method to add a Posting.
     *
     * @param posting a Posting
     */
    protected void addPosting(Posting posting) throws IOException {
        postings.add(posting);

    }

}
