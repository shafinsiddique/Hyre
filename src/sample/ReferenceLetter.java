package sample;

class ReferenceLetter {

    private  String contents;
    private  Posting posting;
    private  Applicant applicant;
    private  Referee referee;

    /**
     * The constructor for a referenceLetter.
     *
     * @param contents a String of the referenceLetter
     * @param posting         a Posting
     * @param applicant       an Applicant
     * @param referee         a Referee
     */

    ReferenceLetter(String contents, Posting posting, Applicant applicant, Referee referee) {
        this.contents = contents;
        this.posting = posting;
        this.applicant = applicant;
        this.referee = referee;
    }

    ReferenceLetter(String contents, Applicant applicant) {
        this.contents = contents;
        this.posting = posting;
        this.applicant = applicant;
        this.referee = referee;
    }

    ReferenceLetter(){}

    String getApplicant(){
        return this.applicant.getUsername();
    }
    String getContents(){
        return this.contents;
    }

    protected void setContents(String contents) {
        this.contents = contents;
    }

}
