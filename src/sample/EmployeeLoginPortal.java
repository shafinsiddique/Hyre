//package sample;
//import com.mongodb.client.MongoDatabase;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//class EmployeeLoginPortal extends Portal {
//    Stage window;
//
//    EmployeeLoginPortal(MongoDatabase db, String applicantCollectionsName, String companyCollectionsName, Stage stage) {
//        super(db, applicantCollectionsName, companyCollectionsName);
//        window = stage;
//    }
//
//    private Coordinator findCoordinator(String username, String password) {
//        for (Company company : super.companies) {
//            if (!company.findCoordinator(username, password).isEmpty()) {
//                return company.findCoordinator(username, password);
//            }
//        }
//        return new Coordinator();
//    }
//
//    private Interviewer findInterviewer(String username, String password) {
//        for (Company company : super.companies) {
//            if (!company.findInterviewer(username, password).isEmpty()) {
//                return company.findInterviewer(username, password);
//            }
//        }
//        return new Interviewer();
//    }
//
//    /**
//     * This method adds a Coordinator to the database.
//     *
//     * @param c a Coordinator object
//     */
//    private void addCoordinatorToDatabase(Coordinator c) {
//        companyDatabaseHelper.addCoordinator(c);
//    }
//
//    /**
//     * This method adds an Interviewer to the database.
//     *
//     * @param i an Interviewer object
//     */
//    private void addInterviewerToDatabase(Interviewer i) {
//        companyDatabaseHelper.addInterviewer(i);
//    }
//
//    /**
//     * Creates a new employee based on what the employeeType is and adds them to the database.
//     *
//     * @param employeeType Specifies what type of employee to create.
//     */
//    public void displayRegisterScreen(String employeeType) {
//        Label usernameLabel = new Label("Username:");
//        TextField usernameField = new TextField();
//        HBox usernameBox = new HBox(usernameLabel, usernameField);
//        Label passwordLabel = new Label("Password:");
//        TextField passwordField = new TextField();
//        HBox passwordBox = new HBox(passwordLabel, passwordField);
//        Label companyLabel = new Label("Company:");
//        TextField companyField = new TextField();
//        HBox companyBox = new HBox(companyLabel, companyField);
//        Button submit = new Button("Submit");
//        submit.setOnAction(actionEvent -> {
//            String username = usernameField.getText();
//            String password = passwordField.getText();
//            String companyName = companyField.getText();
//            Company companyFound = findCompany(companyName);
//            if (companyFound.isEmpty() && employeeType.equals("c")) {  //TODO: Fix when a coordinator from an unknown company registers
//                Company newCompany = new Company(companyName);
//                companyDatabaseHelper.addNewCompany(newCompany);
//                super.companies.add(newCompany);
//                companyFound = newCompany;
//            } else if (companyFound.isEmpty() && employeeType.equals("i")) {
//                System.out.println("Sorry. This company does not exist.");
//                displayRegisterScreen(employeeType);
//            }
//            if (employeeType.equals("c")) {
//                Coordinator c = new Coordinator(username, password, companyFound);
//                companyFound.addCoordinator(c);
//                addCoordinatorToDatabase(c);
//                CoordinatorPortalInterface CP = new CoordinatorPortalInterface(c, super.applicantsCollection,
//                        super.companiesCollection);
//                CoordinatorGUI CPGUI = new CoordinatorGUI(CP, this.window);
//                CPGUI.run();
//            } else if (employeeType.equals("i")) {
//                Interviewer i = new Interviewer(username, password, companyFound);
//                companyFound.addInterviewer(i);
//                addInterviewerToDatabase(i);
////                InterviewerPortal ip = new InterviewerPortal(i, super.applicantsCollection);
////                ip.displayInterviewerPortal();
//            } else {
//                displayRegisterScreen(employeeType);
//            }
//        });
//        Button back = new Button("Go Back");
//        back.setOnAction(actionEvent -> super.activityTypePage(employeeType));
//        VBox vBox = new VBox(20);
//        vBox.getChildren().addAll(usernameBox, passwordBox, companyBox, submit, back);
//        Scene scene = new Scene(vBox);
//        window.setScene(scene);
//        window.show();
//    }
//
//    public void displayLoginScreen(String userType) {
//        Label usernameLabel = new Label("Username:");
//        TextField usernameField = new TextField();
//        HBox usernameBox = new HBox();
//        Label passwordLabel = new Label("Password:");
//        TextField passwordField = new TextField();
//        HBox passwordBox = new HBox();
//        Button submit = new Button("Submit");
//        submit.setOnAction(actionEvent -> {
//            String username = usernameField.getText();
//            String password = passwordField.getText();
//            if (userType.equalsIgnoreCase("c")) {
//                Coordinator c = findCoordinator(username, password);
//                if (!c.isEmpty()) {
//                    CoordinatorPortalInterface CP = new CoordinatorPortalInterface(c, super.applicantsCollection,
//                            super.companiesCollection);
//                    CoordinatorGUI CPGUI = new CoordinatorGUI(CP, new Stage());
//                    CPGUI.run();
//                } else {
//                    System.out.println("Invalid Credentials");
//                }
//            } else if (userType.equalsIgnoreCase("i")){
//                Interviewer i = findInterviewer(username, password);
//                if (!i.isEmpty()) {
////                    InterviewerPortal ip = new InterviewerPortal(i, super.applicantsCollection);
////                    ip.displayInterviewerPortal();
//                } else {
//                    System.out.println("Invalid Credentials");
//                }
//            }
//            displayLoginScreen(userType);
//        });
//        Button back = new Button("Go Back");
//        back.setOnAction(actionEvent -> super.activityTypePage(userType));
//        VBox vBox = new VBox(20);
//        vBox.getChildren().addAll(usernameBox, passwordBox, submit, back);
//        Scene scene = new Scene(vBox);
//        window.setScene(scene);
//    }
//
//}