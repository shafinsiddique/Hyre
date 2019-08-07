package sample;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.mongodb.client.model.Updates;

class UserCollectionHelper {
    private final MongoCollection users;
    private Portal portal;
    private Applicant jobApplicant;

    public UserCollectionHelper(Applicant a, MongoCollection users) {
        this.users = users;
        this.jobApplicant = a;

    }

    public UserCollectionHelper(Portal portal, MongoCollection users) {
        this.users = users;
        this.portal = portal;
    }

    /**
     * Loads Applicants.
     *
     * @return ArrayList<Applicant>
     */
    public ArrayList<Applicant> loadApplicants() {
        FindIterable<Document> documents = this.users.find();

        MongoCursor<Document> cursor = documents.iterator();

        ArrayList<Applicant> jobApplicants = new ArrayList<>();
        while (cursor.hasNext()) {
            Document d = cursor.next();

            Applicant newApplicant = new Applicant(d.getString("username"), d.getString("password"),
                    d.getString("resume"), d.getString("coverLetter"), d.getDate("dateCreated"));
            jobApplicants.add(newApplicant);
            try {
                loadAppliedTo(newApplicant, (List<Document>) d.get("appliedTo"));
                loadInterviews(newApplicant, (List<Document>) d.get("interviews"));
                loadMessages(newApplicant, (List<String>) d.get("messages"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jobApplicants;

    }

    private void loadMessages(Applicant a, List<String> messages) {
        if (!(messages == null)) {
            for (String message : messages) {
                a.addMessage(message);
            }
        }
    }

    private void loadInterviews(Applicant a, List<Document> interviews) {
        for (Document d : interviews) {
            Company c = this.portal.findCompany(d.getString("company"));
            Posting p = c.findPostingWithID(d.getInteger("postingID"));
            Date date = d.getDate("date");

            Document rounds = (Document) d.get("roundReviews");
            HashMap<String, String> roundReviews = new HashMap<>();

            for (String r : p.getRoundTypes()) {
                roundReviews.put(r, rounds.getString(r));
            }

            int round = d.getInteger("round");

            if (d.get("interviewerName") == null) {
                Interview newInterview = new Interview(a, p, date, null, roundReviews, round);


                a.addInterview(newInterview);
                p.addInterview(newInterview);

            } else {
                Interviewer i = c.findInterviewer(d.getString("interviewerName"));

                Interview newInterview = new Interview(a, p, date, i, roundReviews, round);

                a.addInterview(newInterview);
                p.addInterview(newInterview);
            }

        }


    }

    private void loadAppliedTo(Applicant a, List<Document> appliedTo) throws IOException {
        for (Document d : appliedTo) {
            Company c = this.portal.findCompany(d.getString("company"));
            Posting p = c.findPostingWithID(d.getInteger("postingID"));
            a.addPostingToAppliedList(p, d.getString("status"));
            p.addApplicant(a); //Check this
        }
    }

    public void writeDocument(Document attributes) {
        this.users.insertOne(attributes);

    }

    public void addApplication(Posting p) {
        Document d = new Document().append("name", p.getCompanyName()).append("postingID", p.getPostingID())
                .append("status", "Pending");

        users.updateOne(Filters.eq("username", jobApplicant.getUsername()), Updates.addToSet("appliedTo", d));
    }

    public void withdrawApplication(Posting p) {
        Document filter = new Document("username", this.jobApplicant.getUsername());
        Document update = new Document("$pull", new Document("appliedTo", new Document("postingID", p.getPostingID())));
        Document removeInterview = new Document("$pull", new Document("interviews",
                new Document("postingID", p.getPostingID())));
        users.updateOne(filter, update);
        users.updateOne(filter, removeInterview);

    }

    public void addInterview(Interview i) {
        Document roundReviews = new Document();

        for (String rounds : i.getRoundReviews().keySet()) {
            roundReviews.put(rounds, i.getRoundReviews().get(rounds));

        }

        if (i.getInterviewer() == null) {
            try {
                Document d = new Document().append("company", i.getPosting().getCompanyName())
                        .append("postingID", i.getPosting().getPostingID()).append("interviewerName", null)
                        .append("date", Main.stringToDate("09/10/1999")).append("round",
                                i.getRound()).append("roundReviews", roundReviews);
                users.updateOne(Filters.eq("username", jobApplicant.getUsername()),
                        Updates.addToSet("interviews", d));
            } catch (ParseException e) {
                System.out.println("Please enter a valid date.");
            }


        } else {
            Document d = new Document().append("company", i.getInterviewer().getCompany().getName())
                    .append("postingID", i.getPosting().getPostingID()).append("interviewerName",
                            i.getInterviewer().getUsername())
                    .append("date", i.getDate()).append("round", i.getRound()).append("roundReviews", roundReviews);

            users.updateOne(Filters.eq("username", jobApplicant.getUsername()),
                    Updates.addToSet("interviews", d));

        }


    }

    public void updateInterviewRoundReview(Interview i) {
        Document filter = new Document("username", this.jobApplicant.getUsername());
        Document removeInterview = new Document("$pull", new Document("interviews", new Document("postingID",
                i.getPosting().getPostingID())));

        users.updateOne(filter, removeInterview);
        addInterview(i);


    }

    private void updatePostingStatus(Posting p, String status) {
        Document d = new Document().append("company", p.getCompanyName()).append("postingID",
                p.getPostingID()).append("status", status);

        users.updateOne(Filters.eq("username", jobApplicant.getUsername()),
                Updates.addToSet("appliedTo", d));

    }

    public void updateApplicationStatus(Posting p, String status) {
        Document filter = new Document("username", this.jobApplicant.getUsername());

        Document removeAppliedTo = new Document("$pull", new Document("appliedTo", new Document("postingID",
                p.getPostingID())));

        users.updateOne(filter, removeAppliedTo);

        updatePostingStatus(p, status);
    }

    public void addMessage(String m) {
        users.updateOne(Filters.eq("username", jobApplicant.getUsername()),
                Updates.addToSet("messages", m));

    }

    public void clearMessages() {
        for (String message : jobApplicant.getMessages()) {
            Document removeMessage = new Document("$pull", new Document("messages", message));

            users.updateOne(Filters.eq("username", jobApplicant.getUsername()), removeMessage);
        }
    }
}
