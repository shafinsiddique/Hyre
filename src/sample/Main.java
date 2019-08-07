package sample;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;


public class Main extends Application {
    static Stage window;
    static String usertype;
    private static Scene homeScene;

    private static final Scanner s = new Scanner(System.in);
    static final LocalDate date = LocalDate.now();

    static Scene getHomeScene() {
        return homeScene;
    }

    //KEEP HERE
    static void showHomePage() {
        Button applicant = new Button();
        applicant.setText("Applicant");
        applicant.setOnAction(e-> {
            new ApplicantLoginPortal(connectDatabase(), "applicants","companies").activityTypePage("a");});
        Button coordinator = new Button();
        coordinator.setText("Coordinator");
        coordinator.setOnAction(e -> {
            new EmployeeLoginPortal(connectDatabase(), "applicants", "companies").activityTypePage("c");});
        Button interviewer = new Button();
        interviewer.setText("Interviewer");
        interviewer.setOnAction(e -> {
            new EmployeeLoginPortal(connectDatabase(), "applicants", "companies").activityTypePage("i");});
        GridPane homePageLayout = new GridPane();
        homePageLayout.setPadding(new Insets(10,10,10,10));
        homePageLayout.setVgap(5);
        homePageLayout.setHgap(5);

        homePageLayout.setAlignment(Pos.CENTER);
        homePageLayout.add(applicant, 0, 2);
        homePageLayout.add(coordinator, 1, 2);
        homePageLayout.add(interviewer, 2,2);

        homeScene = new Scene(homePageLayout, 400,150);
        window.setScene(homeScene);
        window.show();
    }

    //KEEP
    private static MongoDatabase connectDatabase(){
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://shafinsiddique:csc207@cluster0-nnppo.mongodb.net/test?retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("infoDatabase");
        //Open(database, "applicants", "companies");
        return database;
    }
    //KEEP
    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        //portal = new Portal(connectDatabase(), "applicants","companies");
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("HyRe - All your applications in one place.");
        showHomePage();

    }

    //KEEP
    static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        return formatter1.parse(date);
    }

    static String getInput(String question) {
        System.out.println(question);

        return s.nextLine();

    }
    //KEEP
    public static void main(String[] args) {
        launch(args);
    }
}
