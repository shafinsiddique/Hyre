package sample;

import java.time.ZoneId;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

public class Applicant extends User {
    private LocalDate dateCreated;
    private final HashMap<Posting, String> appliedTo = new HashMap<>();
    private final HashMap<Posting, Interview> interviews = new HashMap<>();
    private String resume;
    private String coverLetter;
    private ReferenceLetter referenceLetter;
    private ArrayList<String> messages;
    private LocalDate lastApplicationDate;

    protected Applicant() {

    }

    protected Applicant(String username, String password, String resume, String coverLetter) {
        super(username, password);
        this.resume = resume;
        this.coverLetter = coverLetter;
        this.dateCreated = Main.date;
        this.messages = new ArrayList<>();
        this.lastApplicationDate = dateCreated;
        this.messages = new ArrayList<>();
    }


    protected Applicant(String username, String password, String resume, String coverLetter,
                        Date date) {
        this(username, password, resume, coverLetter);
        this.dateCreated = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    }

    protected void addMessage(String message) {
        this.messages.add(message);
    }

    protected ArrayList<String> getMessages() {
        return this.messages;
    }

    /**
     * Returns this Applicant's resume.
     *
     * @return resume
     */
    String getResume() {
        return this.resume;
    }

    /**
     * Returns this Applicant's cover letter.
     *
     * @return coverLetter
     */
    protected String getCoverLetter() {
        return this.coverLetter;
    }

    /**
     * Returns the date this Applicant last applied.
     *
     * @return lastApplicationDate
     */
    protected LocalDate getLastDate() {
        return this.lastApplicationDate;
    }


    protected HashMap<Posting, Interview> getInterviews() {
        return this.interviews;
    }

    /**
     * A method to add an Interview.
     *
     * @param i an Interview
     */
    protected void addInterview(Interview i) {

        this.interviews.put(i.getPosting(), i);
    }


    protected LocalDate getDateCreated() {
        return this.dateCreated;
    }

    /**
     * A method to display Applicant's username, resume and cover letter
     *
     * @return String (concatenated) of Applicant info
     */
    protected String getAllInfo() {
        return "Applicant Name: " + this.getUsername() + "\n" +
                "Resume: " + resume + "\n" +
                "Cover Letter: " + coverLetter + "\n";
    }


    protected HashMap<Posting, String> getAppliedTo() {
        return this.appliedTo;
    }


    protected void addPostingToAppliedList(Posting p, String status) {
        appliedTo.put(p, status);
    }

    /**
     * A method to for an Applicant to an application by postingID
     *
     * @param postingID
     * @return Posting
     */
    protected Posting findApplication(int postingID) {
        for (Posting p : this.appliedTo.keySet()) {
            if (p.getPostingID() == postingID) {
                return p;
            }
        }
        return new Posting();
    }

    /**
     * A method to determine whether an Applicant has applied to a posting
     *
     * @param p
     * @return boolean
     */
    protected boolean alreadyAppliedFor(Posting p) {
        for (Posting post : this.appliedTo.keySet()) {
            if (post.getPostingID() == p.getPostingID()) {
                return true;
            }
        }
        return false;
    }

    protected void apply(Posting p) {
        this.appliedTo.put(p, "Pending");
        p.addApplicant(this);

    }

    protected boolean hasInterviewFor(Posting P) {
        for (Posting post : this.getInterviews().keySet()) {
            if (post.getPostingID() == P.getPostingID() && this.getInterviews().get(post).getInterviewer() != null) {
                return true;
            }
        }
        return false;
    }

    protected void removeApplication(Posting p) {
        p.removeApplicant(this);
        this.appliedTo.put(p, "Abandoned");
        this.interviews.remove(p);
        if (this.interviews.isEmpty()) {
            //TODO: When we decide how to change time, change this appropriately.
            this.lastApplicationDate = Main.date;
        }
    }

    protected void updateStatus(Posting p, String status) {
        appliedTo.put(p, status);
    }

    protected void viewOffers() {
        for (Posting p : this.appliedTo.keySet()) {
            if (this.appliedTo.get(p).equals("Offer")) {
                this.hire(p);
            }
        }
    }

    private void hire(Posting p) {


    }

    protected void withdraw(Posting p) {
        this.appliedTo.remove(p);
        this.interviews.remove(p);
    }

    protected void setResume(String resume) {
        this.resume = resume;
    }

    protected void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    protected void setReferenceLetter(ReferenceLetter referenceLetter) {
        this.referenceLetter = referenceLetter;
    }
}
