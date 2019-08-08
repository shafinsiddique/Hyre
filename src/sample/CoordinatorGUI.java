package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
public class CoordinatorGUI {
    private CoordinatorPortalInterface coordinatorInterface;
    private Stage window;
    private Button backButton;
    private Scene homeScene;
    private Posting chosenPosting;
    private Applicant chosenApplicant;
    private Interviewer chosenInterviewer;
    private Date chosenDate;


    CoordinatorGUI(CoordinatorPortalInterface coordinatorInterface, Stage window) {
        this.coordinatorInterface = coordinatorInterface;
        this.window = window;
        backButton = new Button();
        backButton.setText("Go Back");
        backButton.setOnAction(e -> window.setScene(homeScene));

    }


    private ObservableList<String> convertArrayList(ArrayList<String> a) {
        return FXCollections.observableArrayList(a);
    }

    void run() {
        displayCoordinatorHome();

    }


    private void hiringScene(Interview i){
        Label label = new Label("This candidate has completed all stages of the interview. Would you like to hire" +
                "the candidate for this position?");

        ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Yes");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("No");
        rb2.setToggleGroup(group);


        Button submit = new Button();
        submit.setText("Submit");

        submit.setOnAction(e -> {
            if (rb1.isSelected()) {
                coordinatorInterface.hire(i);
                AlertBox.display("Hired.",i.getApplicant() + " has been offered the position of " +
                        i.getPosting().getPosition() + ".");

            }

            else{
                coordinatorInterface.reject(i);
                AlertBox.display("Rejected",i.getApplicant() + " has been rejected for the position" +
                        "of " + i.getPosting().getPosition());
            }
        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label,rb1, rb2, submit);
        Scene scene = new Scene(layout, 200, 200);
        window.setScene(scene);

    }
    private void reviewInterviewsScreen(){
        ObservableList<String>interviewers = convertArrayList(coordinatorInterface.getInProcessInterviews());

        ListView<String> interviewersList = new ListView<>();
        interviewersList.setItems(interviewers);

        Button select = new Button();
        select.setText("Select an Interview");


        select.setOnAction(e -> {
            int i = interviewersList.getSelectionModel().getSelectedIndex();
            if (i < 0){
                AlertBox.display("Error","You need to make a selection");
            }

            else {
                Interview interview = coordinatorInterface.findInProcessInterview(i);

                if (interview.completedFinalRound()){
                    hiringScene(interview);
                }

                else {
                    moveUpCandidateScene(interview);
                }

            }

        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(interviewersList, select, backButton);

        Scene scene = new Scene(layout, 800,400);
        window.setScene(scene);

    }

    private void moveUpCandidateScene(Interview i) {
        Label label = new Label("Would you like to move this candidate to the next round?");

        ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Yes");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("No");
        rb2.setToggleGroup(group);


        Button submit = new Button();
        submit.setText("Submit");

        submit.setOnAction(e -> {
            if (rb1.isSelected()) {
                chosenApplicant = i.getApplicant();
                chosenPosting = i.getPosting();
                viewInterviewersScreen();
            }

            else{
                coordinatorInterface.reject(i);
                AlertBox.display("Rejected",i.getApplicant() + " has been rejected for the position" +
                        "of " + i.getPosting().getPosition());
            }
        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label,rb1, rb2, submit);
        Scene scene = new Scene(layout, 200, 200);
        window.setScene(scene);

    }
    private void displayCoordinatorHome() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        Button postings = new Button();
        postings.setText("Select applicants for Interviews");
        postings.setOnAction(e -> displayPostingsScreen());

        Button interviews = new Button();
        interviews.setText("Review Interviews that have taken place");
        interviews.setOnAction(e -> {
            reviewInterviewsScreen();
        });

        Button createPosting = new Button();
        createPosting.setText("Add Posting");


        createPosting.setOnAction(e -> createPostingScreen());



        Button logout = new Button();
        logout.setText("Logout");
        logout.setOnAction( e -> window.setScene(HomePageGUI.getHomeScene()));

        layout.getChildren().addAll(postings, interviews, createPosting, logout);
        homeScene = new Scene(layout, 350, 350);
        window.setScene(homeScene);


    }

    private void scheduleInterview(){
        if (chosenApplicant.hasInterviewFor(chosenPosting)) {
            AlertBox.display("Error","Applicant has already been chosen for an interview");
        }

        else{
            Interview i =
                    coordinatorInterface.scheduleInterview(chosenPosting, chosenApplicant,
                            chosenInterviewer,
                    chosenDate);

            AlertBox.display("Success","Interview has been scheduled.\n\nInterview Info:" +
                    "\n" + i.getAllInfo());
        }
    }

    private void selectDateForm(){

        GridPane createP = new GridPane();
        createP.setPadding(new Insets(10, 10, 10, 10));
        createP.setVgap(8);
        createP.setHgap(10);
        createP.setAlignment(Pos.CENTER);

        Label idLabel = new Label("Enter Date (dd/MM/YYYY)");
        GridPane.setConstraints(idLabel, 0, 0);

        TextField dateD = new TextField();
        GridPane.setConstraints(dateD, 0, 1);

        Button submit = new Button();
        submit.setText("Schedule Interview");
        submit.setOnAction(e -> {
            try {
                chosenDate = Main.stringToDate(dateD.getText());
                scheduleInterview();
            } catch (ParseException p){
                AlertBox.display("error","parse exvception");
            }
        });

        GridPane.setConstraints(submit, 0, 2);
        createP.getChildren().addAll(idLabel, dateD, submit);

        Scene scene = new Scene(createP, 200,200);

        window.setScene(scene);
    }
    private void viewInterviewersScreen() {
        ObservableList<String>interviewers = convertArrayList(coordinatorInterface.viewAllInterviewers());

        ListView<String> interviewersList = new ListView<>();
        interviewersList.setItems(interviewers);

        Button select = new Button();
        select.setText("Select an interviewer");
        select.setOnAction(e -> {
            int index = interviewersList.getSelectionModel().getSelectedIndex();

            if (index < 0) {
                AlertBox.display("Error","Please make a selection");
            }

            else{
                chosenInterviewer = coordinatorInterface.findInterviewer(index);
                selectDateForm();
            }

        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(interviewersList, select, backButton);

        Scene scene = new Scene(layout, 800,400);
        window.setScene(scene);

    }

    private void viewApplicantsScreen() {

            ObservableList<String> applicants = convertArrayList(coordinatorInterface.viewAllApplicants(
                    chosenPosting));

            ListView<String> applicantsList = new ListView();
            applicantsList.setItems(applicants);

            Button select = new Button();
            select.setText("Select a posting");
            select.setOnAction(e -> {
                int index = applicantsList.getSelectionModel().getSelectedIndex();

                if (index < 0){
                    AlertBox.display("Error","You need to make a selection");
                }

                else {
                    chosenApplicant = coordinatorInterface.findApplicant(chosenPosting, index);
                    viewInterviewersScreen();
                }
            });


            VBox layout = new VBox(10);
            layout.setPadding(new Insets(10,10,10,10));
            layout.getChildren().addAll(applicantsList, select, backButton);

            Scene scene = new Scene(layout, 800,400);
            window.setScene(scene);


        }




    private void displayPostingsScreen() {
        ObservableList<String>postings = convertArrayList(coordinatorInterface.allPostingsString());

        ListView<String> postingsList = new ListView();
        postingsList.setItems(postings);

        Button select = new Button();
        select.setText("Select a posting");

        select.setOnAction(e ->
        {
            int chosenIndex = postingsList.getSelectionModel().getSelectedIndex();
            if (chosenIndex < 0){
                AlertBox.display("Error","You need to make a selection");
            }

            else{
                chosenPosting = coordinatorInterface.findPosting(chosenIndex);
                viewApplicantsScreen();
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(postingsList, select, backButton);

        Scene scene = new Scene(layout, 800,400);
        window.setScene(scene);

    }

    private void createPostingScreen(){

            GridPane createP = new GridPane();
            createP.setPadding(new Insets(10, 10, 10, 10));
            createP.setVgap(8);
            createP.setHgap(10);
            createP.setAlignment(Pos.CENTER);

            Label idLabel = new Label("Posting ID (Integer)");
            GridPane.setConstraints(idLabel, 0, 0);

            TextField postingID = new TextField();
            GridPane.setConstraints(postingID, 1, 0);

            Label positionLabel = new Label("Position:");

            GridPane.setConstraints(positionLabel, 0, 1);

            TextField position = new TextField();
            GridPane.setConstraints(position, 1, 1);

            Label descriptionLabel = new Label("Description");
            GridPane.setConstraints(descriptionLabel, 0, 2);

            TextField description = new TextField();
            GridPane.setConstraints(description, 1, 2);

            Label requirementsLabel = new Label("Requirements");
            GridPane.setConstraints(requirementsLabel, 0, 3);

            TextField requirements = new TextField();
            GridPane.setConstraints(requirements, 1, 3);

            Label dateLabel = new Label("Expiry Date (dd/MM/YYYY)");
            GridPane.setConstraints(dateLabel, 0, 4);

            TextField date = new TextField();
            GridPane.setConstraints(date, 1, 4);

            Label roundsLabel = new Label("Rounds (Seperate by Comma)");
            GridPane.setConstraints(roundsLabel, 0, 5);

            TextField rounds = new TextField();
            GridPane.setConstraints(rounds, 1, 5);

            Label branchlabel = new Label("Branch");
            GridPane.setConstraints(branchlabel, 0, 6);

            TextField branch = new TextField();
            GridPane.setConstraints(branch, 1, 6);

            Button createButton = new Button();
            createButton.setText("Create Posting");
            GridPane.setConstraints(createButton, 0, 7);
            GridPane.setConstraints(this.backButton,1, 7);
            createButton.setOnAction(e ->
            {
                try {
                    coordinatorInterface.createPosting((Integer.parseInt(postingID.getText())),
                            position.getText(),
                            description.getText(), requirements.getText(),
                            Main.stringToDate(date.getText()),
                            rounds.getText(), branch.getText());

                    AlertBox.display("Success","Posting has been created.");
                } catch (ParseException p){
                    AlertBox.display("Error","Parse Exception Error");
                }
                catch (IOException i){
                    AlertBox.display("Error","IOException Error");
                }


            });

            createP.getChildren().addAll(idLabel, postingID, positionLabel, position,
                    descriptionLabel, description, requirementsLabel
                    , requirements, dateLabel, date, roundsLabel, rounds, branchlabel,
                    branch, createButton, backButton);
            Scene scene = new Scene(createP, 600, 600);
            window.setScene(scene);

        }

    }

