package sample;

import java.util.Date;
import java.util.HashMap;

class Interview {
    private Date date;
    private int round;
    private final Posting posting;
    private final Applicant jobApplicant;
    private Interviewer interviewer;
    private int currentRound;
    private HashMap<String, String> roundReviews;

    Interview(Applicant a, Posting p, Date date, Interviewer interviewer) {
        this.jobApplicant = a;
        this.posting = p;
        this.date = date;
        this.round = 0;
        this.interviewer = interviewer;
        this.currentRound = 0;
        roundReviews = new HashMap<>();
        for (String round : p.getRoundTypes()) {
            roundReviews.put(round, "");
        }

    }

    Interview(Applicant a, Posting p, Date date, Interviewer interviewer, HashMap<String, String> roundReviews,
              int round) {
        this(a, p, date, interviewer);
        this.currentRound = round;
        this.roundReviews = roundReviews;
    }

    protected void moveToNextRound() {
        this.currentRound += 1;
    }

    protected HashMap<String, String> getRoundReviews() {
        return roundReviews;
    }

    protected void setReviewForRound(String round, String review) {

        this.roundReviews.put(round, review);
        this.interviewer = null;
        this.date = null;
    }

    protected Boolean completedFinalRound() {
        if (round == posting.getRoundTypes().size() - 1) {
            if (!roundReviews.get(posting.getRoundTypes().get(posting.getRoundTypes().size() - 1)).equals("")) {
                return true;
            }
        }
        return false;

    }

    /**
     * Returns the current round type of an Interview.
     *
     * @return currentRound
     */
    protected String getCurrentRoundType() {
        return posting.getRoundTypes().get(currentRound);
    }

    /**
     * Returns this Interview's Interviewer.
     *
     * @return Interviewer
     */
    protected Interviewer getInterviewer() {
        return this.interviewer;
    }

    /**
     * Returns this Interview's Date.
     *
     * @return Date
     */
    protected Date getDate() {
        return this.date;
    }


    /**
     * Moves interview to next round.
     *
     * @param newDate
     * @param newInterviewer
     */
    protected void moveToNextRound(Date newDate, Interviewer newInterviewer) {
        date = newDate;
        interviewer = newInterviewer;
        jobApplicant.addMessage("You have successfully qualified for the next round of interviews for "
                + posting.getPosition() + " job at " + posting.getCompanyName() + ".");
        moveToNextRound();
    }

    /**
     * Returns the round of the interview.
     *
     * @return round
     */
    protected int getRound() {
        return this.round;
    }

    /**
     * Returns this Applicant.
     *
     * @return jobApplicant
     */
    protected Applicant getApplicant() {
        return this.jobApplicant;
    }

    /**
     * Returns this Posting.
     *
     * @return posting
     */
    protected Posting getPosting() {
        return this.posting;
    }

    /**
     * Returns all info about a Posting for on-screen display.
     *
     * @return a concatenated String
     */
    protected String getAllInfo() {
        String PostingID = "Posting ID: " + this.posting.getPostingID() + "\n";
        String applicant = "Applicant: " + this.jobApplicant.getUsername() + "\n";
        String reviews = "Round Reviews: \n";

        for (String round : posting.getRoundTypes()) {
            reviews += round + ": " + roundReviews.get(round) + "\n";
        }

        if (interviewer == null) {
            return PostingID + applicant + reviews;
        } else {
            String date = "Date: " + this.date + "\n";
            String interviewer = "Interviewer: " + this.interviewer.getUsername() + "\n";

            return PostingID + applicant + date + interviewer + reviews;
        }


    }


}


