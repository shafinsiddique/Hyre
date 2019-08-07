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

        }
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
