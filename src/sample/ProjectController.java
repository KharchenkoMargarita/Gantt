package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

public class ProjectController implements Initializable {

    public TextField taskName;
    public DatePicker dateFrom;
    public DatePicker dateTo;
    public ComboBox colorsComboBox;
    public TextField performerName;
    public ComboBox premadeTasksList;

    ArrayList<Task> tasks = new ArrayList<>();
    ArrayList<String> titleList = new ArrayList<>();

    Alert alert = new Alert(Alert.AlertType.ERROR);

    public ScrollPane scroll;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        colorsComboBox.getItems().addAll("Красный", "Зеленый", "Синий", "Оранжевый", "Желтый", "Фиолетовый");
        premadeTasksList.getItems().addAll("Планирование", "Анализ", "Проектирование", "Разработка", "Тестирование", "Оценка Затрат", "Свой вариант");
    }

    public void add(ActionEvent actionEvent) {
        String taskNameFromForm = null;
        if (premadeTasksList.getValue() != null) {
            if (premadeTasksList.getValue() == "Свой Вариант") {
                taskNameFromForm = taskName.getText();
            } else
                taskNameFromForm = premadeTasksList.getValue().toString();
        }
        if (titleList.contains(taskNameFromForm)) {
            alert.setContentText("Такая задача уже существует, введите другое название");
            alert.showAndWait();
        } else if (dateFrom.getValue().isBefore(Task.startDateOfProject)) {
            alert.setContentText("Задача выходит за рамки проекта");
            alert.showAndWait();
        } else if (dateTo.getValue().isAfter(Task.endDateOfProject)) {
            alert.setContentText("Задача выходит за рамки проекта");
            alert.showAndWait();
        } else if (dateFrom.getValue().isAfter(dateTo.getValue())) {
            alert.setContentText("Задача выходит за рамки проекта");
            alert.showAndWait();
        } else {
            Task newTask = new Task(taskNameFromForm, dateFrom.getValue(), dateTo.getValue(), parseColor(), performerName.getText());
            tasks.add(newTask);
            titleList.add(taskNameFromForm);

            refreshChart();
        }
    }

    private String parseColor() {
        if (colorsComboBox.getValue() == "Красный") {
            return "color-red";
        }
        if (colorsComboBox.getValue() == "Зеленый") {
            return "color-green";
        }
        if (colorsComboBox.getValue() == "Синий") {
            return "color-blue";
        }
        if (colorsComboBox.getValue() == "Оранжевый") {
            return "color-orange";
        }
        if (colorsComboBox.getValue() == "Желтый") {
            return "color-yellow";
        }
        if (colorsComboBox.getValue() == "Фиолетовый") {
            return "color-purple";
        }
        return "color-blue";
    }

    public void refreshChart() {
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        final GanttChart<Number, String> chart = new GanttChart<Number, String>(xAxis, yAxis);
        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.BLACK);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);
        yAxis.setCategories(FXCollections.observableArrayList(titleList));

        chart.setTitle(Task.projectTitle);
        chart.setLegendVisible(false);
        chart.setBlockHeight(50);

        for (int i = 0; i < tasks.size(); i++) {
            XYChart.Series series = new XYChart.Series();
            series.getData().add(new XYChart.Data(DAYS.between(Task.startDateOfProject, tasks.get(i).getDateFrom()), tasks.get(i).getName(),
                    new GanttChart.ExtraData(tasks.get(i).getDiffInDays(), tasks.get(i).getColor())));
            chart.getData().add(series);
        }
        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());

        scroll.setContent(chart);
    }

    public void showTable() {
        TableView<Task> table = new TableView<Task>();
        ObservableList<Task> data = FXCollections.observableArrayList();
        for (int i = 0; i < tasks.size(); i++) {
            data.add(tasks.get(i));
        }

        TableColumn taskNameCol = new TableColumn("Название задачи");
        taskNameCol.setMinWidth(100);
        taskNameCol.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));

        TableColumn dateFromCol = new TableColumn("Дата Начала");
        dateFromCol.setMinWidth(100);
        dateFromCol.setCellValueFactory(
                new PropertyValueFactory<Task, LocalDate>("dateFrom"));

        TableColumn dateToCol = new TableColumn("Дата окончания");
        dateToCol.setMinWidth(100);
        dateToCol.setCellValueFactory(
                new PropertyValueFactory<Task, LocalDate>("dateTo"));

        TableColumn performerNameCol = new TableColumn("Исполнитель");
        performerNameCol.setMinWidth(200);
        performerNameCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("performer"));

        TableColumn daysCol = new TableColumn("Трудоемкость (в днях)");
        daysCol.setMinWidth(200);
        daysCol.setCellValueFactory(
                new PropertyValueFactory<Task, Long>("diffInDays"));

        table.setItems(data);
        table.getColumns().addAll(taskNameCol, performerNameCol, dateFromCol, dateToCol, daysCol);

        scroll.setContent(table);
    }

    public void showChart(ActionEvent actionEvent) {
        refreshChart();
    }

    public void getColor(ActionEvent actionEvent) {
    }
}
