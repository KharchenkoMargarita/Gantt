package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static java.time.temporal.ChronoUnit.DAYS;
import static sample.Task.endDateOfProject;
import static sample.Task.startDateOfProject;

public class HomeController {
    public TextField title;
    public DatePicker dateStart;
    public DatePicker dateEnd;

    public void createProject(ActionEvent actionEvent) {

        Task.projectTitle = title.getText();
        Task.startDateOfProject = dateStart.getValue();
        endDateOfProject = dateEnd.getValue();
        Task.durationInDays = DAYS.between(startDateOfProject, endDateOfProject);

        Main.stage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
