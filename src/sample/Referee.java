package sample;


import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

class Referee extends User {
    private Date dateCreated;
    private ArrayList<ReferenceLetter> referenceLetters = new ArrayList<>();
    private ArrayList referenceLetterRequests;

    Referee() {

    }

    protected Referee(String username, String password)
            throws IOException {
        super(username, password);
        this.dateCreated = new Date();

    }


    Referee(String username, String password, String referenceLetter,
            Date date) throws IOException {
        this(username, password);
        this.dateCreated = date;

    }

    /**
     * This method returns a reference letter
     *
     * @return referenceLetter
     */
    ArrayList<ReferenceLetter> getReferenceLetters() {
        return this.referenceLetters;
    }

    Date getDateCreated() {
        return this.dateCreated;
    }

    String getAllInfo() throws IOException {
        return "Referee Name: " + this.getUsername() + "\n" +
                "Reference Letter: " + referenceLetters + "\n";
    }

    protected ArrayList<ReferenceLetter> getRefereedFor() {
        return this.referenceLetters;
    }

    protected ArrayList<String> getRequests() {
        return this.referenceLetterRequests;
    }

    /**
     * This method adds a reference letter
     *
     * @param referenceLetter a String of the referenceLetter
     */
    protected void addReferenceLetter(ReferenceLetter referenceLetter) {
        this.referenceLetters.add(referenceLetter);
    }


}
