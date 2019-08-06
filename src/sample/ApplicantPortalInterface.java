package sample;

import com.mongodb.client.MongoCollection;
import javafx.geometry.Pos;

import java.util.ArrayList;

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

    ArrayList<String> getApplicationsHistory() {
        ArrayList<String>strings = new ArrayList<>();

        for (Posting p: jobApplicant.getAppliedTo().keySet()) {
            String info = p.getAllInfo() + "Status: " + jobApplicant.getAppliedTo().get(p);
            strings.add(info);

        }

        return strings;
    }

    ArrayList<String> getAllPostingsString(){
        ArrayList<String> strings = new ArrayList<>();

        for (Posting p : jobApplicant.getAppliedTo().keySet()) {
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
        this.dbUpdater.withdrawApplication(p);

    }

}
