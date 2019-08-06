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
import javafx.stage.Window;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class CoordinatorGUI {
    private CoordinatorPortalInterface coordinatorInterface;
    private Stage window;


    CoordinatorGUI(CoordinatorPortalInterface coordinatorInterface, Stage window) {
        this.coordinatorInterface = coordinatorInterface;
        this.window = window;
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
        logout.setOnAction( e -> window.setScene(Main.getHomeScene()));

        layout.getChildren().addAll(postings, interviews, createPosting, logout);

        Scene scene = new Scene(layout, 350, 350);
        window.setScene(scene);


    }

    private void displayPostingsScreen() {

    }

    private void createPostingScreen(){
        try {
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
            GridPane.setConstraints(createButton, 1, 7);
            createButton.setOnAction(e -> AlertBox.display("Success", "Posting has been created"));

            createP.getChildren().addAll(idLabel, postingID, positionLabel, position, descriptionLabel, description, requirementsLabel
                    , requirements, dateLabel, date, roundsLabel, rounds, branchlabel, branch, createButton);
            Scene scene = new Scene(createP, 600, 800);
            window.setScene(scene);



            coordinatorInterface.createPosting((Integer.parseInt(postingID.getText())), position.getText(),
                    description.getText(), requirements.getText(), Main.stringToDate(date.getText()),
                    rounds.getText(), branch.getText());
        }

        catch (ParseException p) {
            System.out.println("Parse Exception.");
        }

        catch (IOException i) {
            System.out.println("IO exception");
        }
    }
}
