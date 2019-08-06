package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Posting {
    private ArrayList<Applicant> jobApplicantList = new ArrayList<>();
    private ArrayList<Interview> interviewList = new ArrayList<>();
    private String position;
    private String description;
    private String requirements;
    private Company company;
    private int postingID;
    private Date closingDate;
    private Date openingDate;
    private ArrayList<String> roundTypes = new ArrayList<>();
    private final ArrayList<String> tags = new ArrayList<>();
    private boolean closed;

    protected Posting() {

    }

    protected Posting(int postingID, String position, String description, String requirements, Company company,
                      Date expiryDate, ArrayList<String> roundTypes) {
        this.position = position;
        this.description = description;
        this.requirements = requirements;
        this.company = company;
        this.postingID = postingID;
        this.closingDate = expiryDate;
        this.roundTypes = roundTypes;
        this.closed = false;

        createTags();

    }

    protected boolean isEmpty() {

        return this.position == null;

    }

    /**
     * Sets openingDate tags for a Posting.
     *
     * @param d a Date representing the openingDate for this Posting
     */
    protected void setOpeningDate(Date d) {
        this.openingDate = d;
    }

    protected ArrayList<Interview> getInProcessInterviews() {
        ArrayList<Interview> ipInterviews = new ArrayList<>();
        for (Interview i : interviewList) {
            if (i.getRound() < this.roundTypes.size()) {
                if (!i.getRoundReviews().get(this.roundTypes.get(i.getRound())).equals("")) {
                    ipInterviews.add(i);
                }
            }
        }
        return ipInterviews;
    }

    /**
     * Adds tags to a Posting.
     */
    private void createTags() {
        this.tags.add(company.getName().toLowerCase());
        this.tags.addAll(new ArrayList<>(Arrays.asList(position.split(" "))));
        this.tags.addAll(new ArrayList<>(Arrays.asList(description.split(" "))));
        this.tags.addAll(new ArrayList<>(Arrays.asList(requirements.split(" "))));
        this.tags.add(Integer.toString(postingID));

        for (int x=0; x<tags.size(); x++) {
            tags.set(x, tags.get(x).toLowerCase());
        }

    }

    /**
     * Returns all the info about a Posting.
     *
     * @return rounds
     */
    protected String getAllInfo() {
        String postingID = "Posting ID: " + this.postingID + "\n";
        String company = "Company: " + this.company.getName() + "\n";
        String positionName = "Position: " + this.position + "\n";
        String positionDesc = "Description: " + this.description + "\n";
        String requirements = "Requirements: " + this.requirements + "\n";
        String openingDate = "This posting opened: " + this.openingDate + "\n";
        String closingDate = "This posting closes: " + this.closingDate + "\n";
        String tags = "Tags: " + this.tags + "\n";
        String rounds = "Rounds: " + this.roundTypes + "\n";

        return postingID + company + positionName + positionDesc + requirements + openingDate + closingDate + tags
                + rounds;
    }

    protected Date getClosingDate() {
        return this.closingDate;
    }

    protected ArrayList<String> getTags() {
        return this.tags;
    }

    protected void addLocation(String location) {
        this.tags.add(location);
    }

    protected int getPostingID() {
        return this.postingID;
    }

    /**
     * Returns a Posting's job description.
     *
     * @return description
     */
    protected String getDescription() {
        return this.description;
    }

    /**
     * Returns a Posting's job requirements.
     *
     * @return requirements
     */
    protected String getRequirements() {
        return this.requirements;
    }

    /**
     * Returns a Posting's list of Interviews.
     *
     * @return interviewList
     */
    protected ArrayList<Interview> getInterviews() {
        return this.interviewList;
    }

    /**
     * Returns the Company which has created this Posting.
     *
     * @return company
     */
    protected Company getCompany() {
        return this.company;
    }

    /**
     * Returns a list of Applicants who have applied to this Posting.
     *
     * @return jobApplicantList
     */
    protected ArrayList<Applicant> getApplicantList() {
        return this.jobApplicantList;
    }

    protected boolean isClosed() {
        return (new Date().after(this.closingDate) || this.closed);
    }

    /**
     * Returns the position for this Posting.
     *
     * @return position
     */
    protected String getPosition() {
        return this.position;
    }

    protected String getCompanyName() {
        return this.company.getName();
    }

    protected ArrayList<String> getRoundTypes() {
        return this.roundTypes;
    }

    protected void close() {
        this.closed = true;
        this.jobApplicantList = new ArrayList<>();
        this.interviewList = new ArrayList<>();
    }

    /**
     * Adds an Applicant to the application list for this Posting.
     *
     * @param jobApplicant
     */
    protected void addApplicant(Applicant jobApplicant) {
        this.jobApplicantList.add(jobApplicant);
    }

    protected Applicant findApplicant(String userName) {
        for (Applicant jobApplicant : jobApplicantList) {
            if (jobApplicant.getUsername().equals(userName)) {
                return jobApplicant;
            }
        }
        System.out.println("No such applicant exists.");
        return new Applicant();
    }

    /**
     * Adds an Interview for this Posting.
     *
     * @param i
     */
    protected void addInterview(Interview i) {
        this.interviewList.add(i);
    }

    protected void removeApplicant(Applicant jobApplicant) {
        this.jobApplicantList.remove(jobApplicant);
        // Remove the candidate if they have an interview scheduled.
        for (Interview i : this.interviewList) {
            if (i.getApplicant() == jobApplicant) {
                this.interviewList.remove(i);
                break;
            }

        }

    }

}
