//package sample;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Calendar;
//
//class Interviewer extends User {
//    private Company company;
//
//    Interviewer() {
//        this.username = null;
//        this.password = null;
//    }
//
//    /**
//     * A User who of the HRPortal, who is an Interviewer of the specified Company.
//     *
//     * @param username a String of the Interviewer's username for logging in the HRPortal
//     * @param password a String of the Interviewer's password for logging in to HRPortal
//     * @param company  a String of the Interviewer's Company
//     */
//    Interviewer(String username, String password, Company company) {
//        super(username, password);
//        this.company = company;
//    }
//
//    /**
//     * A method to get this Interviewer's Company.
//     *
//     * @return company
//     */
//    protected Company getCompany() {
//        return this.company;
//    }
//
//    protected void viewScheduledInterviews() {
//        System.out.println("Here are your scheduled interviews");
//        for (int x = 0; x < getAssignedInterviews().size(); x++) {
//            System.out.println(getAssignedInterviews().get(x).getAllInfo());
//        }
//    }
//
//    /**
//     * A method to view this Interviewer's assigned interviews.
//     *
//     * @return ArrayList<Interview>
//     */
//    private ArrayList<Interview> getAssignedInterviews() {
//        ArrayList<Interview> scheduledInterviews = new ArrayList<>();
//        for (Posting p : company.getPostings()) {
//            for (Interview i : p.getInterviews()) {
//                if (i.getInterviewer() == this) {
//                    scheduledInterviews.add(i);
//                }
//            }
//        }
//
//        return scheduledInterviews;
//    }
//
//    /**
//     * A method view today's interviews.
//     *
//     * @return ArrayList<Interview>
//     */
//    protected ArrayList<Interview> getTodayInterviews() {
//        ArrayList<Interview> today = new ArrayList<>();
//        for (Interview interview : this.getAssignedInterviews()) {
//            Calendar cal = Calendar.getInstance();
//
//            cal.setTime(interview.getDate());
//
//            int month = cal.get(Calendar.MONTH);
//            int year = cal.get(Calendar.YEAR);
//            int day = cal.get(Calendar.DAY_OF_MONTH);
//
//            Calendar cal2 = Calendar.getInstance();
//
//            cal2.setTime(new Date());
//
//            int month2 = cal2.get(Calendar.MONTH);
//            int year2 = cal2.get(Calendar.YEAR);
//            int day2 = cal2.get(Calendar.DAY_OF_MONTH);
//
//            if (month == month2 && year == year2 && day == day2) {
//                today.add(interview);
//            }
//        }
//        return today;
//    }
//
//    /**
//     * A method to enter a review.
//     *
//     * @param i      an Interview
//     * @param review a String of the review
//     */
//    protected void enterReview(Interview i, String review) {
//        String round = i.getPosting().getRoundTypes().get(i.getRound());
//        i.setReviewForRound(round, review);
//    }
//
//}
//

