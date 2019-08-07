package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InterviewerGUI {
    private InterviewerPortalInterface interviewerInterface;
    private Stage window;


    InterviewerGUI(InterviewerPortalInterface interviewerInterface, Stage window) {
        this.interviewerInterface = interviewerInterface;
        this.window = window;
    }

    void run() {
        displayInterviewerHome();

    }

    private void displayInterviewerHome() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        Button viewToday = new Button();
        viewToday.setText("View all interviews you have for today");
        viewToday.setOnAction(e -> getTodayInterviews());

        Button viewAll = new Button();
        viewAll.setText("View your entire interview schedule.");


        Button logout = new Button();
        logout.setText("Logout");
        logout.setOnAction( e -> window.setScene(HomePageGUI.getHomeScene()));

        layout.getChildren().addAll(viewToday, viewAll, logout);

        Scene scene = new Scene(layout, 350, 350);
        window.setScene(scene);


    }
}