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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


public class Main extends Application {
    Stage window;
    String usertype;
    private static Scene homeScene;
    private Portal portal;
    private String interviewer = "Interviewer";
    private String coordinator = "Coordinator";
    private String applicant = "Applicant";

    static final LocalDate date = LocalDate.now();

    static Scene getHomeScene() {
        return homeScene;
    }

    public void validateApplicant(String username, String password) {
        Applicant a = portal.findApplicant(username, password);
        if (!a.isEmpty()) {
            ApplicantPortalGUI applicantGUI = new ApplicantPortalGUI(portal.Applicantlogin(a), window);
            applicantGUI.run();

        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        portal = new Portal
                (connectDatabase(), "applicants","companies");
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("HyRe - All your applications in one place.");
        showHomePage();

        //

    }

    public void loginCoordinator(String username, String password) {
        Coordinator c = portal.findCoordinator(username, password);

        if (!c.isEmpty()) {
            CoordinatorGUI newCoord = new CoordinatorGUI(portal.coordinatorLogin(c), window);
            newCoord.run();
        }

    }
    public void showLoginPage() {
        GridPane loginPage = new GridPane();
        loginPage.setPadding(new Insets(10,10,10,10));
        loginPage.setVgap(8);
        loginPage.setHgap(10);
        loginPage.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("username");
        GridPane.setConstraints(nameLabel, 0, 0);

        TextField username = new TextField();
        GridPane.setConstraints(username, 1, 0);

        Label passLabel = new Label("password");


        GridPane.setConstraints(passLabel, 0, 1);

        TextField password = new TextField();
        GridPane.setConstraints(password, 1, 1);

        Button loginbutton = new Button();
        loginbutton.setText("Login");
        GridPane.setConstraints(loginbutton, 1, 2);

        if (usertype.equals(applicant)) {
            loginbutton.setOnAction(e -> validateApplicant(username.getText(),password.getText()));
        }

        else if(usertype.equals(coordinator)) {
            loginbutton.setOnAction(e -> loginCoordinator(username.getText(), password.getText()));
        }


        loginPage.getChildren().addAll(nameLabel, username, passLabel, password, loginbutton);

        Scene loginScene = new Scene(loginPage, 300, 200);
        window.setScene(loginScene);

    }

    private void registerApplicant(String name, String password, String resume, String coverletter) {
        Applicant newApplicant = new Applicant(name, password, resume, coverletter);
        portal.registerApplicant(newApplicant);
        ApplicantPortalGUI applicantGUI = new ApplicantPortalGUI(portal.Applicantlogin(newApplicant), window);
        applicantGUI.run();

    }

    private void showApplicantRegisterPage() {
        GridPane registerPage = new GridPane();
        registerPage.setPadding(new Insets(10,10,10,10));
        registerPage.setVgap(8);
        registerPage.setHgap(10);
        registerPage.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Username");
        GridPane.setConstraints(nameLabel, 0, 0);

        TextField username = new TextField();
        GridPane.setConstraints(username, 1, 0);

        Label passLabel = new Label("Password");

        GridPane.setConstraints(passLabel, 0, 1);

        TextField password = new TextField();
        GridPane.setConstraints(password, 1, 1);

        Label resumeLabel = new Label("Resume");
        GridPane.setConstraints(resumeLabel, 0, 2);

        TextArea resume = new TextArea();
        GridPane.setConstraints(resume, 1, 2);

        Label coverLetterlabel = new Label("Password");
        GridPane.setConstraints(coverLetterlabel, 0, 3);


        TextArea coverletter = new TextArea();
        GridPane.setConstraints(coverletter, 1, 3);


        Button registerButton =  new Button();
        registerButton.setText("Register");
        GridPane.setConstraints(registerButton, 1, 4);

        registerButton.setOnAction(e ->
                registerApplicant(username.getText(), password.getText(), resume.getText(), coverletter.getText()));

        registerPage.getChildren().addAll(nameLabel, username, passLabel, password, resumeLabel, resume, coverletter, coverLetterlabel
        , registerButton);


        Scene scene = new Scene(registerPage, 800,500);
        window.setScene(scene);

    }

    private void showEmployeeRegisterPage() {

    }
    private void registrationPage() {
        if (usertype.equals(applicant)){
            showApplicantRegisterPage();
        }

        else {
            showEmployeeRegisterPage();
        }

    }
    private void activityTypePage() {
        Button login = new Button();
        login.setText("Login");
        login.setOnAction(e -> {showLoginPage();});

        Button register = new Button();
        register.setText("Register");
        register.setOnAction( e -> {registrationPage();});

        GridPane pageLayout = new GridPane();
        pageLayout.setAlignment(Pos.CENTER);
        pageLayout.setHgap(5);
        pageLayout.add(login, 0,0);
        pageLayout.add(register, 1, 0);

        Scene pageScene = new Scene(pageLayout, 400, 150);
        window.setScene(pageScene);
    }

    private void showHomePage() {
        Button applicant = new Button();
        applicant.setText("Applicant");
        applicant.setOnAction(e->{
            usertype = this.applicant;
            activityTypePage();

        });

        Button coordinator = new Button();
        coordinator.setText("Coordinator");
        coordinator.setOnAction(e -> {
            usertype = this.coordinator;
            activityTypePage();});

        Button interviewer = new Button();
        interviewer.setText("Interviewer");
        interviewer.setOnAction(e ->
        {   usertype = this.interviewer;
            activityTypePage();
        });

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

    private MongoDatabase connectDatabase(){
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://shafinsiddique:csc207@cluster0-nnppo.mongodb.net/test?retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("infoDatabase");
        //Open(database, "applicants", "companies");
        return database;
    }


    static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        return formatter1.parse(date);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
