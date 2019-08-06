package sample;

import java.io.IOException;
import java.util.Date;

class Coordinator extends User {
    private Company company;

    protected Coordinator() {

    }

    /**
     * A method for the Coordinator to reject an Applicant.
     *
     * @param i an Interview
     */
    protected void rejectApplicant(Interview i) {
        ///Reject Applicant From the Interview round.
        Applicant a = i.getApplicant();
        i.getPosting().getApplicantList().remove(a);
        i.getPosting().getInterviews().remove(i);
        a.updateStatus(i.getPosting(), "Rejected");
    }

    /**
     * A method for the Coordinator to offer a job to the last remaining Applicant.
     *
     * @param i an Interview
     */
    protected void offerJob(Interview i) {
        Applicant a = i.getApplicant();

        i.getPosting().getInterviews().remove(i);

        i.getPosting().getApplicantList().remove(a);
        a.updateStatus(i.getPosting(), "Offer");
    }

    /**
     * A User of the HRPortal, who is a Coordinator of the specified Company.
     *
     * @param username a String of the Coordinator's username for logging in the HRPortal
     * @param password a String of the Coordinator's password for logging in to HRPortal
     * @param company  a String of the Coordinator's Company
     */
    protected Coordinator(String username, String password, Company company) {
        super(username, password);
        this.company = company;
    }

    /**
     * Returns this Coordinator's Company.
     *
     * @return Company
     * @see Company
     */
    protected Company getCompany() {
        return this.company;
    }

    /**
     * Returns a Posting given an integer representing a postingIDint.
     *
     * @return Posting
     * @see Posting
     */
    protected Posting getPosting(int postingIDint) {
        for (int x = 0; x < this.company.getPostings().size(); x++) {
            if (this.company.getPostings().get(x).getPostingID() == postingIDint) {
                return this.company.getPostings().get(x);
            }
        }
        return new Posting();
    }

    /**
     * Adds a Posting to a Company.
     *
     * @param p the Posting that is to be added to the Coordinator's Company
     * @see Company
     * @see Posting
     */
    protected void addPosting(Posting p) throws IOException {
        company.addPosting(p);
    }

    /**
     * Assigns an Interviewer to a Posting.
     * Also adds the Interview to an Applicant's profile.
     *
     * @param p    the Posting for which an Interview is being scheduled
     * @param a    the Applicant who is being scheduled for the Interview
     * @param i    The Interviewer for the interview.
     * @param date a Date for which the Interview is being scheduled
     * @see Company
     * @see Applicant
     */
    protected Interview scheduleInterview(Posting p, Applicant a, Interviewer i, Date date) {
        Interview newInterview = new Interview(a, p, date, i);
        p.addInterview(newInterview);
        a.addInterview(newInterview);

        return newInterview;
    }
}



