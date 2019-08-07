package sample;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HomePageGUI {
    Stage window;
    static Scene homeScene;
    Portal portal;

    HomePageGUI(Portal portal, Stage window){
        this.window = window;
        this.portal = portal;
    }

    void run(){
        showHomePage();
    }

    static Scene getHomeScene(){
        return homeScene;
    }

    public void showLoginPage(){
        if (Portal.getUserType().equals(Portal.getApplicant())) {
            ApplicantLoginGUI loginGUI = new ApplicantLoginGUI(portal, window);
            loginGUI.displayLoginScreen();

        }

        else {
            EmployeeLoginGUI employeeLoginGUI = new EmployeeLoginGUI(portal, window, Portal.getUserType());
            employeeLoginGUI.displayLoginScreen();

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



    void showHomePage(){
        Button applicant = new Button();
        applicant.setText("Applicant");
        applicant.setOnAction(e->{
            Portal.setUserType(Portal.getApplicant());
            activityTypePage();

        });

        Button coordinator = new Button();
        coordinator.setText("Coordinator");
        coordinator.setOnAction(e -> {
            Portal.setUserType(Portal.getCoordinator());
            activityTypePage();});

        Button interviewer = new Button();
        interviewer.setText("Interviewer");
        interviewer.setOnAction(e ->
        {   Portal.setUserType(Portal.getInterviewer());
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

    private void registrationPage() {
        if (Portal.getUserType().equals(Portal.getApplicant())){
            ApplicantLoginGUI registerGUI = new ApplicantLoginGUI(portal, window);
            registerGUI.displayRegisterScreen();
        }

        else {


        }

    }

}
