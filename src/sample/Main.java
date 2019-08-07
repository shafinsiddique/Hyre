package sample;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


public class Main extends Application {
    Stage window;
    private Portal portal;
    static final LocalDate date = LocalDate.now();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        MongoClient conn = getConnection();
        portal = new Portal
                (getDatabase(conn), "applicants",
                        "companies");
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("HyRe - All your applications in one place.");
        HomePageGUI gui = new HomePageGUI(portal, window);
        gui.run();
        conn.close();

    }

    private MongoDatabase getDatabase(MongoClient mongoClient){
        MongoDatabase database = mongoClient.getDatabase("infoDatabase");
        //Open(database, "applicants", "companies");
        return database;
    }
    private MongoClient getConnection(){
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://shafinsiddique:csc207@cluster0-nnppo.mongodb.net/test?retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(uri);
        return mongoClient;
    }


    static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        return formatter1.parse(date);
    }



}
