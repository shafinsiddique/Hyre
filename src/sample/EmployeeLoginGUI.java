package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EmployeeLoginGUI {
    Portal portalInterface;
    Stage window;
    String employeeType;
    EmployeeLoginGUI(Portal portal, Stage window, String employeeType) {
        this.portalInterface = portal;
        this.window = window;
        this.employeeType = employeeType;
    }

    public void loginCoordinator(String username, String password) {
        Coordinator c = portalInterface.findCoordinator(username, password);

        if (!c.isEmpty()) {
            CoordinatorGUI newCoord = new CoordinatorGUI(
                    portalInterface.coordinatorLogin(c), window);
            newCoord.run();
        }
        else {
            AlertBox.display("Error","Invalid Credentials.");
        }

    }

    public void loginInterviewer(String username, String password) {
        Interviewer i = portalInterface.findInterviewer(username, password);

        if (!i.isEmpty()) {
            InterviewerGUI newInterviewer = new InterviewerGUI(
                    portalInterface.interviewerLogin(i), window);
            newInterviewer.run();
        }
        else {
            AlertBox.display("Error","Invalid Credentials.");
        }
    }

    public void registerEmployee(String username, String password, String companyName){
        Company companyFound = portalInterface.findCompany(companyName);
        if (companyFound.isEmpty() && employeeType.equals(portalInterface.getCoordinator())) {
            companyFound = new Company(companyName);
            Coordinator c = new Coordinator(username, password, companyFound);
            portalInterface.registerCoordinator(c, companyFound);
        } else if (companyFound.isEmpty() && employeeType.equals(portalInterface.getInterviewer())) {
            AlertBox.display("Error", "This company does not exist.");
            displayRegisterScreen();
        }
        if (employeeType.equals(portalInterface.getCoordinator())) {
            Coordinator c = new Coordinator(username, password, companyFound);
            CoordinatorGUI newCoord = new CoordinatorGUI(
                    portalInterface.registerCoordinator(c, companyFound), window);
            newCoord.run();
        } else if (employeeType.equals(portalInterface.getInterviewer())) {
            Interviewer i = new Interviewer(username, password, companyFound);
            InterviewerGUI newInterviewer = new InterviewerGUI(
                    portalInterface.registerInterviewer(i, companyFound), window);
            newInterviewer.run();
        } else {
            AlertBox.display("Error", "This company does not exist.");
            displayRegisterScreen();
        }
    }

    public void displayRegisterScreen(){
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

        Label companyLabel = new Label("Company");
        GridPane.setConstraints(passLabel, 0, 2);

        TextField company = new TextField();
        GridPane.setConstraints(company, 1, 1);

        Button registerButton =  new Button();
        registerButton.setText("Register");
        GridPane.setConstraints(registerButton, 1, 4);
        registerButton.setOnAction(e->{
            if(username.getText().isEmpty() || password.getText().isEmpty() || company.getText().isEmpty()){
                AlertBox.display("error", "Please enter valid credentials!");
                displayRegisterScreen();
            }else{
                registerEmployee(username.getText(), password.getText(), company.getText());
            }
        });
    }

    public void displayLoginScreen(){
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

        if (employeeType.equals(Portal.getCoordinator())) {
            loginbutton.setOnAction(e -> loginCoordinator(username.getText(), password.getText()));
        }

        else {
            loginbutton.setOnAction(e -> loginInterviewer(username.getText(), password.getText()));
        }

        loginPage.getChildren().addAll(nameLabel, username, passLabel, password, loginbutton);

        Scene loginScene = new Scene(loginPage, 300, 200);
        window.setScene(loginScene);
    }


}
