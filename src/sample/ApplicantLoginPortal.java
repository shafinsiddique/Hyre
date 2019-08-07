package sample;

import com.mongodb.client.MongoDatabase;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bson.Document;
import java.util.ArrayList;

public class ApplicantLoginPortal extends Portal {
    private Stage window;

    /**
     * The MainPortal is a text-based system for use by an Applicants, a Coordinator or an Interviewer
     *
     * @param db                       a MongoDB cloud database
     * @param applicantCollectionsName a String pointing to table in MongoDB database
     * @param companyCollectionsName   a String pointing to table in MongoDB database
     */
    ApplicantLoginPortal(MongoDatabase db, String applicantCollectionsName, String companyCollectionsName, Stage window) {
        super(db, applicantCollectionsName, companyCollectionsName);
        window = window;
    }

    /**
     * A method to register an Applicant.
     *
     * @param a an Applicant
     */
    private void registerApplicant(Applicant a) {
        super.jobApplicantUsers.add(a);
        Document document = new Document();
        document.append("username", a.username);
        document.append("password", a.password);
        document.append("dateCreated", a.getDateCreated());
        document.append("resume", a.getResume());
        document.append("coverLetter", a.getCoverLetter());
        document.append("appliedTo", new ArrayList<>());
        document.append("interviews", new ArrayList<>());
        super.applicantsDatabaseHelper.writeDocument(document);
    }

    public void validateApplicant(String username, String password) {
        Applicant a = this.findApplicant(username);
        if (!a.isEmpty()) {
            this.activityTypePage("a");
        }
    }

    /**
     * method to check if a username has already been used by another applicant
     *
     * @param username
     * @return true if the username has already been used by another applicant
     */
    private boolean checkUsername(String username) {
        boolean result = false;
        for (Applicant u : super.jobApplicantUsers) {
            if (u.getUsername().equals(username)) {
                result = true;
            }
        }
        return result;
    }

    public void displayRegisterScreen(String type)  {
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        HBox usernameBox = new HBox(usernameLabel, usernameField);
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        HBox passwordBox = new HBox(passwordLabel, passwordField);
        Button submit = new Button("Submit");
        submit.setOnAction(actionEvent -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            while (checkUsername(username) || username.equals("")) {
                username = Main.getInput("Sorry, this username is invalid. " +
                        "Please choose a different username.");
                displayRegisterScreen(type);
            }
            while(password.equals("")){
                password = Main.getInput("Sorry, this password is invalid. Please choose a different password");
                displayRegisterScreen(type);
            }
            String resume = new ApplicantFileManager().fileMaker("resume");
            String coverLetter = new ApplicantFileManager().fileMaker("coverletter");

            Applicant newApplicant = new Applicant(username, password, resume, coverLetter);
            registerApplicant(newApplicant);
            ApplicantPortalInterface AP = new ApplicantPortalInterface(newApplicant, allPostings(), super.applicantsCollection);
            ApplicantPortalGUI APGUI = new ApplicantPortalGUI(AP, this.window);
            APGUI.run();

        });
        Button back = new Button("Go Back");
        back.setOnAction(actionEvent -> activityTypePage(type));
        VBox vBox = new VBox(usernameBox, passwordBox, submit, back);
        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();

    } //TODO: Have users type out their resume. as an option

    public void displayLoginScreen(String usertype) {
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        HBox usernameBox = new HBox(usernameLabel, usernameField);
        Label passwordLabel = new Label("Password:");

        TextField passwordField = new TextField();
        HBox passwordBox = new HBox(passwordLabel, passwordField);

        Button submit = new Button("Submit");
        submit.setOnAction(actionEvent -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            Applicant a = this.findApplicant(username);
            if (!a.isEmpty() && a.getPassword().equals(password)) {
                ApplicantPortalInterface AP = new ApplicantPortalInterface(a, allPostings(), super.applicantsCollection);
                ApplicantPortalGUI APGUI = new ApplicantPortalGUI(AP, this.window);
                APGUI.run();
            } else {
                System.out.println("Invalid Credentials.");
            }
        });
        Button back = new Button("Go Back");
        back.setOnAction(actionEvent -> this.activityTypePage(usertype));
        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(usernameField, passwordField, submit, usernameBox, passwordBox, back);
        Scene scene = new Scene(vBox, 550, 250);
        Stage s = new Stage();
        s.setTitle("VBox Layout Demo");
        s.setScene(scene);
        s.show();

    }
}
