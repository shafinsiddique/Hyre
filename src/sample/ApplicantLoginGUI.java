package sample;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ApplicantLoginGUI {
    Portal portalInterface;
    Stage window;

    ApplicantLoginGUI(Portal portal, Stage window){
        this.portalInterface = portal;
        this.window = window;
    }


    public void validateApplicant(String username, String password) {
        Applicant a = portalInterface.findApplicant(username, password);
        if (!a.isEmpty()) {
            ApplicantPortalGUI applicantGUI = new ApplicantPortalGUI(
                    portalInterface.Applicantlogin(a), window);
            applicantGUI.run();

        }

    }

    void displayLoginScreen() {
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

        loginbutton.setOnAction(e -> validateApplicant(username.getText(),password.getText()));
        loginPage.getChildren().addAll(nameLabel, username, passLabel, password, loginbutton);

        Scene loginScene = new Scene(loginPage, 300, 200);
        window.setScene(loginScene);


    }


    public void displayRegisterScreen() {
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

        Label coverLetterlabel = new Label("Cover Letter");
        GridPane.setConstraints(coverLetterlabel, 0, 3);


        TextArea coverletter = new TextArea();
        GridPane.setConstraints(coverletter, 1, 3);


        Button registerButton =  new Button();
        registerButton.setText("Register");
        GridPane.setConstraints(registerButton, 1, 4);

        registerButton.setOnAction(e ->
                registerApplicant(username.getText(), password.getText(),
                        resume.getText(), coverletter.getText()));

        registerPage.getChildren().addAll(nameLabel, username, passLabel, password, resumeLabel,
                resume, coverletter,
                coverLetterlabel
                , registerButton);


        Scene scene = new Scene(registerPage, 800,500);
        window.setScene(scene);

    }

    private void registerApplicant(String name, String password, String resume, String coverletter) {
        Applicant newApplicant = new Applicant(name, password, resume, coverletter);
        portalInterface.registerApplicant(newApplicant);
        ApplicantPortalGUI applicantGUI = new ApplicantPortalGUI
                (portalInterface.Applicantlogin(newApplicant), window);
        applicantGUI.run();

    }
}
