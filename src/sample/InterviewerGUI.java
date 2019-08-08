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

public class InterviewerGUI {
    private InterviewerPortalInterface interviewerInterface;
    private Interview chosenInterview;
    private Stage window;
    private Button backButton;
    private Scene homeScene;


    InterviewerGUI(InterviewerPortalInterface interviewerInterface, Stage window) {
        this.interviewerInterface = interviewerInterface;
        this.window = window;
        backButton = new Button();
        backButton.setText("Go Back");
        backButton.setOnAction(e -> window.setScene(homeScene));
    }

    void run() {
        displayInterviewerHome();

    }

    private ObservableList<String> convertArrayList(ArrayList<String> a) {
        return FXCollections.observableArrayList(a);
    }

    private void displayReviewPage(Interview interview){
        GridPane reviewP = new GridPane();
        reviewP.setPadding(new Insets(10, 10, 10, 10));
        reviewP.setVgap(8);
        reviewP.setHgap(10);
        reviewP.setAlignment(Pos.CENTER);

        Label reviewLabel = new Label("Enter your review for this applicant");
        GridPane.setConstraints(reviewLabel, 0, 0);

        TextField review = new TextField();
        GridPane.setConstraints(review, 0, 1);
        Button submitButton = new Button();
        submitButton.setText("Submit review");
        submitButton.setOnAction(e-> {
            interviewerInterface.addReview(interview, review.getText());

            AlertBox.display("Success","Review has been submitted.");
        }
        );

        GridPane.setConstraints(submitButton, 0, 3);
        GridPane.setConstraints(this.backButton,1, 3);

        reviewP.getChildren().addAll(reviewLabel, review, submitButton, backButton);
        Scene scene = new Scene(reviewP, 600, 600);
        window.setScene(scene);
    }

    private void displayAllInterviewsScreen(){
        ObservableList<String>interviews = convertArrayList(interviewerInterface.interviewer.getAssignedInterviewsString());

        ListView<String> interviewList = new ListView();
        interviewList.setItems(interviews);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(interviewList, backButton);

        Scene scene = new Scene(layout, 800,400);
        window.setScene(scene);
    }

    private void displayTodayInterviewsScreen() {
        ObservableList<String>interviews = convertArrayList(interviewerInterface.interviewer.getTodayInterviewsString());

        ListView<String> interviewList = new ListView();
        interviewList.setItems(interviews);

        Button select = new Button();
        select.setText("Select an interview");

        select.setOnAction(e ->
        {
            int chosenIndex = interviewList.getSelectionModel().getSelectedIndex();
            if (chosenIndex < 0){
                AlertBox.display("Error","You need to make a selection");
            }

            else{
                chosenInterview = interviewerInterface.findTodayInterview(chosenIndex);
                displayReviewPage(chosenInterview);
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(interviewList, select, backButton);

        Scene scene = new Scene(layout, 800,400);
        window.setScene(scene);

    }

    private void displayInterviewerHome() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        Button viewToday = new Button();
        viewToday.setText("View all interviews you have for today");
        viewToday.setOnAction(e -> displayTodayInterviewsScreen());

        Button viewAll = new Button();
        viewAll.setText("View your entire interview schedule.");
        viewAll.setOnAction(e-> displayAllInterviewsScreen());


        Button logout = new Button();
        logout.setText("Logout");
        logout.setOnAction( e -> window.setScene(HomePageGUI.getHomeScene()));

        layout.getChildren().addAll(viewToday, viewAll, logout);

        homeScene = new Scene(layout, 350, 350);
        window.setScene(homeScene);


    }
}