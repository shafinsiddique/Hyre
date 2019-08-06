package sample;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.List;
import java.util.ArrayList;

class CompanyCollectionHelper {
    private Portal portal;
    private final MongoCollection companies;
    private Company company;

    protected CompanyCollectionHelper(Company c, MongoCollection companies) {
        this.company = c;
        this.companies = companies;
    }

    protected CompanyCollectionHelper(Portal portal, MongoCollection companies) {
        this.portal = portal;
        this.companies = companies;
    }

    public ArrayList<Company> loadCompanies() {
        FindIterable<Document> documents = this.companies.find();

        MongoCursor<Document> cursor = documents.iterator();

        ArrayList<Company> companies = new ArrayList<>();

        while (cursor.hasNext()) {
            Document d = cursor.next();

            Company c = new Company(d.getString("name"));
            c.setPostings(loadPostings((List<Document>) d.get("postings"), c));
            c.setCoordinators(loadEmployees((List<Document>) d.get("coordinators"), c, "coordinator"));
            c.setInterviewers(loadEmployees((List<Document>) d.get("interviewers"), c, "interviewer"));

            companies.add(c);
        }

        return companies;
    }

    private ArrayList loadEmployees(List<Document> documents, Company c, String type) {
        ArrayList<Coordinator> coordinators = new ArrayList<>();
        ArrayList<Interviewer> interviewers = new ArrayList<>();
        for (Document d : documents) {
            String username = d.getString("username");
            String password = d.getString("password");
            if (type.equals("coordinator")) {
                coordinators.add(new Coordinator(username, password, c));
            } else {
                interviewers.add(new Interviewer(username, password, c));
            }
        }
        if (type.equals("coordinator")) {
            return coordinators;
        } else {
            return interviewers;
        }
    }

    private ArrayList<Posting> loadPostings(List<Document> postings, Company c) {
        ArrayList<Posting> docsToPostings = new ArrayList<>();
        for (Document d : postings) {
            docsToPostings.add(new Posting(d.getInteger("postingID"), d.getString("position"),
                    d.getString("description"), d.getString("requirements"), c, d.getDate("expiryDate"),
                    (ArrayList<String>) d.get("rounds")));

        }
        return docsToPostings;
    }

    /**
     * A method to add a new Posting
     *
     * @param p a Posting
     */
    public void addPosting(Posting p) {
        Document d = new Document().append("postingID", p.getPostingID()).append("position", p.getPosition())
                .append("description", p.getDescription()).append("requirements", p.getRequirements())
                .append("expiryDate", p.getClosingDate()).append("rounds", p.getRoundTypes());

        companies.updateOne(Filters.eq("name", company.getName()), Updates.addToSet("postings", d));

    }

    /**
     * A method to add a new Interviewer
     *
     * @param i an Interviewer
     */
    public void addInterviewer(Interviewer i) {
        Document d = new Document().append("username", i.getUsername()).append("password", i.getPassword());

        companies.updateOne(Filters.eq("name", i.getCompany().getName()),
                Updates.addToSet("interviewers", d));
    }

    /**
     * A method to add a new Coordinator
     *
     * @param c a Coordinator
     */
    public void addCoordinator(Coordinator c) {
        Document d = new Document().append("username", c.getUsername()).append("password", c.getPassword());

        companies.updateOne(Filters.eq("name", c.getCompany().getName()),
                Updates.addToSet("coordinators", d));
    }

    /**
     * A method to add a new Company
     *
     * @param c a Company name
     */
    public void addNewCompany(Company c) {
        Document d = new Document("company", c.getName()).append("postings", new ArrayList<>())
                .append("interviewers", new ArrayList<>()).append("coordinators", new ArrayList<>());
        this.companies.insertOne(d);

    }
}

