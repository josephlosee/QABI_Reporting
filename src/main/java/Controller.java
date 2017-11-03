import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import org.jfree.chart.ChartFrame;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable{

    @FXML
    public Button displayButton;
    @FXML
    DatePicker startDatePick, endDatePick;
    @FXML
    private LineChart lineChart;
    private ObservableList<XYChart.Series> olSeries = FXCollections.observableList(new ArrayList<>());

    private void setChartData(){
        lineChart.setData(olSeries);
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");

        LocalDateTime testData = LocalDateTime.now();
        for (int i=30; i>=0; i--){
            //techRequest.getData().add(new XYChart.Data<>(created.toLocalDateTime().toLocalDate(), days));
            series.getData().add(new XYChart.Data<String, Long>(testData.minusDays(i).toLocalDate().toString(), (long) (i/4)+1));

        }
        lineChart.getData().add(series);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChartData();
    }

    public void onDispButtonClicked(ActionEvent actionEvent) {
        LocalDate start = startDatePick.getValue();
        LocalDate end = startDatePick.getValue();
        XYChart.Series techRequest = new XYChart.Series();

        if (start!=null && end!=null){
            //Select * from JIRAIssues where project like "TECH" and statusCode=6 //(closed)
            ResultSet results = null;
            try {
                if (results!=null && results.first()){
                    do{
                        Timestamp created = results.getTimestamp("created");

                        Timestamp closed = results.getTimestamp("closedTime");
                        long i;
                        i = closed.getTime() - created.getTime();
                        long days = TimeUnit.MILLISECONDS.toDays(i);
                        XYChart.Data test = new XYChart.Data<LocalDate, Long>();
                        test.setXValue(created.toLocalDateTime().toLocalDate());
                        techRequest.getData().add(new XYChart.Data<>(created.toLocalDateTime().toLocalDate(), days));

                    }while (results.next());

                    lineChart.getData().add(techRequest);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } {

                }

        }
    }

}
