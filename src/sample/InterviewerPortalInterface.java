package sample;

import com.mongodb.client.MongoCollection;

import java.util.ArrayList;

public class InterviewerPortalInterface {
    private final Interviewer interviewer;
    private final MongoCollection users;

    InterviewerPortalInterface(Interviewer i, MongoCollection usersCollection) {
        this.interviewer = i;
        this.users = usersCollection;
    }

    private ArrayList<String> viewInterviewsForToday() {
        int counter = 1;
        ArrayList<String> interviews = new ArrayList<String>();
        for (Interview i : interviewer.getTodayInterviews()) {
            interviews.add(counter + ". " + i.getAllInfo());
            counter += 1;
        }
        return interviews;
    }

    /**
     * Updates the InterviewDatabase with a Review.
     *
     * @param i an Interview
     */
    private void updateInterviewDatabaseWithReview(Interview i) {
        UserCollectionHelper newHelper = new UserCollectionHelper(i.getApplicant(), users);

        newHelper.updateInterviewRoundReview(i);
    }

    void addReview(int selectInterview, String review){
        Interview i = interviewer.getTodayInterviews().get(selectInterview - 1);
        interviewer.enterReview(i, review);
        updateInterviewDatabaseWithReview(i);
    }

}