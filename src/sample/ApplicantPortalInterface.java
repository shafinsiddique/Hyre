package sample;

import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.Arrays;

public class ApplicantPortalInterface {
    private final Applicant jobApplicant;
    private final ArrayList<Posting> allPostings;
    private final UserCollectionHelper dbUpdater;

    protected ApplicantPortalInterface(Applicant a, ArrayList<Posting> allPostings, MongoCollection collection) {
        this.jobApplicant = a;
        this.allPostings = allPostings;
        this.dbUpdater = new UserCollectionHelper(a, collection);
    }


    Applicant getJobApplicant() {
        return jobApplicant;
    }

    ArrayList<Posting>findPostingwithKeyword(String filter) {
        ArrayList<Posting> postings = new ArrayList<>();
        if (!filter.equals("")) {
            ArrayList<String> tags = new ArrayList<>(Arrays.asList(filter.split(", ")));
            for (Posting posting : this.allPostings) {
                for (String tag : posting.getTags()) {
                    if (tags.contains(tag)) {
                        if (!postings.contains(posting)) {
                            postings.add(posting);
                        }
                    }
                }
            }
        } else {
            postings = this.allPostings;
        }
        return postings;
    }

    /**
     * Returns this Applicant's current applications.
     *
     * @return ArrayList</String> of Applicant's current applications
     */
    ArrayList<String> getCurrentApplications() {
        ArrayList<String> applications = new ArrayList<>();
        ArrayList<Posting> posts = CurrentApplications();
        for (Posting p: posts) {
            String info = p.getAllInfo() + "Status:" +jobApplicant.getAppliedTo().get(p);
            applications.add(info);
        }
        return applications;
    }

    ArrayList<Posting> CurrentApplications() {
        ArrayList<Posting> applications = new ArrayList<>();
        for (Posting p: jobApplicant.getInterviews().keySet()) {
            applications.add(p);
        }
        for (Posting p: jobApplicant.getAppliedTo().keySet()) {
            if (jobApplicant.getAppliedTo().get(p).equalsIgnoreCase("Pending")) {
                applications.add(p);
            }
        }
        return applications;
    }

    
    ArrayList<String> getApplicationsHistory() {
        ArrayList<String>strings = new ArrayList<>();

        for (Posting p: jobApplicant.getAppliedTo().keySet()) {
            String info = p.getAllInfo() + "Status: " + jobApplicant.getAppliedTo().get(p);
            strings.add(info);
        }
        return strings;
    }

    ArrayList<Posting>getAllPostings(){
        return this.allPostings;
    };

    ArrayList<String> getPostingsStrings(ArrayList<Posting> posts){
        ArrayList<String> strings = new ArrayList<>();

        for (Posting p : posts) {
            strings.add(p.getAllInfo());
        }

        return strings;
    }

    Posting getPosting(int index) {
        return allPostings.get(index);
    }

    void apply(Posting p) {
        this.jobApplicant.apply(p);
        this.dbUpdater.addApplication(p);
    }

    void withdraw(Posting p) {
        this.jobApplicant.withdraw(p);
        this.allPostings.remove(p);
        this.dbUpdater.updateApplicationStatus(p, "Withdrawn");

    }

    void clearMessages(){
        dbUpdater.clearMessages();
    }


}
