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

    ApplicantLoginGUI(Portal portal, Stage window) {
        this.portalInterface = portal;
        this.window = window;
    }

    /**
     * Validates an Applicant's username and password.
     *
     * @param username the Applicant username to be validated
     * @param password the Applicant password
     * @see Applicant
     */
    public void validateApplicant(String username, String password) {
        Applicant a = portalInterface.findApplicant(username, password);
        if (!a.isEmpty()) {
            ApplicantPortalGUI applicantGUI = new ApplicantPortalGUI(
                    portalInterface.Applicantlogin(a), window);
            applicantGUI.run();
        }
    }

    /**
     * Existing Applicant's login screen.
     *
     * @see Applicant
     */
    void displayLoginScreen() {
        GridPane loginPage = new GridPane();
        loginPage.setPadding(new Insets(10, 10, 10, 10));
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

        loginbutton.setOnAction(e -> validateApplicant(username.getText(), password.getText()));

        Button back = new Button("Go Back");
        GridPane.setConstraints(back, 1, 3);

        back.setOnAction(e -> new HomePageGUI(this.portalInterface, this.window).showHomePage());
        loginPage.getChildren().addAll(nameLabel, username, passLabel, password, loginbutton, back);


        Scene loginScene = new Scene(loginPage, 300, 200);
        window.setScene(loginScene);


    }

    /**
     * Register an Applicant login screen.
     *
     * @see Applicant
     */
    public void displayRegisterScreen() {
        GridPane registerPage = new GridPane();
        registerPage.setPadding(new Insets(10, 10, 10, 10));
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


        Button registerButton = new Button();
        registerButton.setText("Register");
        GridPane.setConstraints(registerButton, 1, 4);

        registerButton.setOnAction(e ->
                registerApplicant(username.getText(), password.getText(),
                        resume.getText(), coverletter.getText()));

        Button back = new Button("Go Back");
        GridPane.setConstraints(back, 1, 5);

        back.setOnAction(e -> new HomePageGUI(this.portalInterface, this.window).showHomePage());

        registerPage.getChildren().addAll(nameLabel, username, passLabel, password, resumeLabel,
                resume, coverletter,
                coverLetterlabel
                , registerButton, back);


        Scene scene = new Scene(registerPage, 800, 500);
        window.setScene(scene);

    }

    /**
     * Registers an Applicant's username and password, and accepts their resume and cover letter.
     *
     * @param name        the Applicant username to be registered
     * @param password    the Applicant password to be registered
     * @param resume      text input of the Applicant's resume
     * @param coverletter text input of the Applicant's cover letter
     * @see Applicant
     */
    private void registerApplicant(String name, String password, String resume, String coverletter) {
        Applicant newApplicant = new Applicant(name, password, resume, coverletter);
        portalInterface.registerApplicant(newApplicant);
        ApplicantPortalGUI applicantGUI = new ApplicantPortalGUI
                (portalInterface.Applicantlogin(newApplicant), window);
        applicantGUI.run();

    }
}
