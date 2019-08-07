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

public class CoordinatorGUI {
    private CoordinatorPortalInterface coordinatorInterface;
    private Stage window;
    private Button backButton;
    private Scene homeScene;


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

    private void displayCoordinatorHome() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        Button postings = new Button();
        postings.setText("Select applicants for Interviews");
        postings.setOnAction(e -> displayPostingsScreen());

        Button interviews = new Button();
        interviews.setText("Review Interviews that have taken place");

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

    private void viewInterviewersScreen(int index) {

    }

    private void viewApplicantsScreen(int index) {
        if (index < 0) {
            AlertBox.display("Error", "You need to make atleast one selection.");
        }
        else {
            ObservableList<String> applicants = convertArrayList(coordinatorInterface.viewAllApplicants(

                    coordinatorInterface.findPosting(index)));
            ListView<String> applicantsList = new ListView();
            applicantsList.setItems(applicants);

            Button select = new Button();
            select.setText("Select a posting");

            select.setOnAction(e -> viewInterviewersScreen(applicantsList.getSelectionModel().getSelectedIndex()));

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(10,10,10,10));
            layout.getChildren().addAll(applicantsList, select, backButton);

            Scene scene = new Scene(layout, 800,400);
            window.setScene(scene);


        }




    }
    private void displayPostingsScreen() {
        ObservableList<String>postings = convertArrayList(coordinatorInterface.allPostingsString());

        ListView<String> postingsList = new ListView();
        postingsList.setItems(postings);

        Button select = new Button();
        select.setText("Select a posting");

        select.setOnAction(e -> viewApplicantsScreen(postingsList.getSelectionModel().getSelectedIndex()));

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

