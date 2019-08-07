package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ApplicantPortalGUI {
    private ApplicantPortalInterface applicantInterface;
    private Stage window;
    private Button backButton;
    private Scene homeScene;

    protected ApplicantPortalGUI(ApplicantPortalInterface applicantinterface, Stage window) {
        this.applicantInterface = applicantinterface;
        this.window = window;
        backButton = new Button();
        backButton.setText("Go Back.");
        backButton.setOnAction(e-> window.setScene(homeScene));
    }

    public void run() {
        displayApplicantHome();
    }

    private void showProfile() {
        Label username = new Label("Username: " + applicantInterface.getJobApplicant().getUsername());
        Label resume = new Label("Resume: " + applicantInterface.getJobApplicant().getResume());
        Label coverLetter = new Label("Cover Letter: " + applicantInterface.getJobApplicant().getCoverLetter());
        Label dateCreated = new Label("Date Created: " + applicantInterface.getJobApplicant().getDateCreated());
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(username, resume, coverLetter, dateCreated, backButton);
        Scene scene = new Scene(layout, 300, 300);
        window.setScene(scene);


    }

    private ObservableList<String> convertArrayList(ArrayList<String> a) {
        return FXCollections.observableArrayList(a);
    }


    private void showApplicationHistory() {
        ListView<String> applicationsList = new ListView();
        ObservableList<String>applications = convertArrayList(applicantInterface.getApplicationsHistory());

        applicationsList.setItems(applications);

        Button withdraw = new Button();
        withdraw.setText("Withdraw");
        withdraw.setOnAction(e -> withdrawIndeX(applicationsList.getSelectionModel().getSelectedIndex()));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(applicationsList, withdraw, backButton);

        Scene scene = new Scene(layout, 800,400);
        window.setScene(scene);

    }

    private void apply(int index) {

        if (index < 0) {
            AlertBox.display("Error","You must make a selection");
        }

        else {
            Posting p = applicantInterface.getPosting(index);

            if (p.isClosed()) {
                AlertBox.display("Error","Sorry the posting closed on " + p.getClosingDate());
            }

            else if (applicantInterface.getJobApplicant().alreadyAppliedFor(p)) {
                AlertBox.display("Error","You have already applied for this position");
            }

            else{
                applicantInterface.apply(p);
                AlertBox.display("Success!", "Congratulations! You have successfully applied" +
                        " for the position of " + p.getPosition() + " at " + p.getCompanyName());
            }
        }
    }

    private void displayFilteredPostings(String filter) {
        if (applicantInterface.findPostingwithKeyword(filter).size() == 0) {
            AlertBox.display("Error", "No such posting found");
        }

        else {
            showPostingsPage(applicantInterface.getPostingsStrings(
                    applicantInterface.findPostingwithKeyword(filter)));
        }

    }

    private void searchPostingsPage() {
        GridPane searchPage = new GridPane();
        searchPage.setPadding(new Insets(10,10,10,10));
        searchPage.setVgap(8);
        searchPage.setHgap(10);
        searchPage.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Search Keyword(s):");
        GridPane.setConstraints(nameLabel, 0, 0);

        TextField search = new TextField();
        GridPane.setConstraints(search, 0, 1);

        Button searchButton = new Button();
        searchButton.setText("Search");
        searchButton.setOnAction(e->
        {
            displayFilteredPostings(search.getText().toLowerCase());
        });
        GridPane.setConstraints(searchButton, 0, 2);
        searchPage.getChildren().addAll(nameLabel, search, searchButton);
        Scene scene = new Scene(searchPage, 300,200);
        window.setScene(scene);
    }

    private void showPostingsPage(ArrayList<String>strings) {
        ObservableList<String>postings = convertArrayList(strings);

        ListView<String>postingsList = new ListView<>();
        postingsList.setItems(postings);
        Button apply = new Button();
        apply.setText("Apply");
        apply.setOnAction(e -> {
            apply(postingsList.getSelectionModel().getSelectedIndex());
        });

        Button search = new Button();
        search.setText("Search for postings");
        search.setOnAction(e -> searchPostingsPage());
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(postingsList, apply, backButton, search);

        Scene scene = new Scene(layout, 800,400);
        window.setScene(scene);


    }

    private void withdrawIndeX(int index){
        if (index < 0) {
            AlertBox.display("Error", "You must make a selection");
        }

        else {
            applicantInterface.withdraw(applicantInterface.getPosting(index));
            AlertBox.display("Success","You have successfully withdrawn this application");

        }
    }

    private void showNotificationsPage() {
        if (applicantInterface.getJobApplicant().getMessages().size() == 0) {
            AlertBox.display("Error", "You have no new messages.");
        }

        else {
            ObservableList<String> messages = convertArrayList(applicantInterface.getJobApplicant().getMessages());
            Label label = new Label("Your messages");
            ListView<String>messagesView = new ListView<>();
            messagesView.setItems(messages);

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(10,10,10,10));
            layout.getChildren().addAll(label, messagesView, backButton);

            Scene scene = new Scene(layout);
            window.setScene(scene);

        }
    }

    private void displayApplicantHome() {
        Label welcome = new Label("Welcome Back, " + applicantInterface.getJobApplicant().getUsername() + "!");
        Button viewPostings = new Button();
        viewPostings.setText("View Open Postings");
        viewPostings.setOnAction(e -> showPostingsPage(applicantInterface.getPostingsStrings
                (applicantInterface.getAllPostings())));
        Button applicanHistory = new Button();
        applicanHistory.setText("View all your applications");
        applicanHistory.setOnAction(e -> showApplicationHistory());
        Button profile = new Button();
        profile.setText("View Profile");
        profile.setOnAction(e -> showProfile());
        Button notifications = new Button();
        notifications.setText("View Notifications");
        notifications.setOnAction(e -> showNotificationsPage());
        Button logOut = new Button();
        logOut.setText("Logout");
        logOut.setOnAction(e -> window.setScene(HomePageGUI.getHomeScene()));
        VBox homeLayout = new VBox(10);
        homeLayout.setAlignment(Pos.CENTER);
        homeLayout.getChildren().addAll(welcome, profile, notifications, viewPostings, applicanHistory, logOut);

        homeScene = new Scene(homeLayout, 300,300);

        window.setScene(homeScene);

    }

}
