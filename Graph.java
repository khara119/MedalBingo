import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class Graph extends Application {
	public static String FILE_NAME;
	private ArrayList<Data> data = new ArrayList<>();
	private HBox root = new HBox(2);
	private VBox config = new VBox();
	private CheckBox showRetiredPlayer = new CheckBox("脱落者を含む");
	private NumberAxis xAxis = new NumberAxis();
	private NumberAxis yAxis = new NumberAxis();
	private LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
	private int count = 0;
	private ScrollPane scrollPane = new ScrollPane();
	private VBox showPlayersCheckBoxes = new VBox();

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("ビンゴメダルゲーム");

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(Graph.FILE_NAME)), "UTF-8"));
		String line = br.readLine();
		String[] splitLine = line.split(",");

		ArrayList<CheckBox> showPlayersBoxes = new ArrayList<>();
		for (String s : splitLine) {
			Data d = new Data(s);
			data.add(d);
			CheckBox cb = d.getShowBox();
			cb.setIndeterminate(false);
			cb.setSelected(true);
			cb.setOnAction((ActionEvent) -> {
				updateGraph();
			});
			showPlayersBoxes.add(cb);
		}

		while((line=br.readLine()) != null) {
			splitLine = line.split(",");
			for (int i=0; i<splitLine.length; i++) {
				data.get(i).add(Integer.parseInt(splitLine[i]));
			}
			count++;
		}
		br.close();

		xAxis.setUpperBound(count);
		xAxis.setTickUnit(1);
		yAxis.setTickUnit(1);
		xAxis.setMinorTickVisible(false);
		yAxis.setMinorTickVisible(false);

		lineChart.setMinHeight(600);
		lineChart.setMaxHeight(600);
		lineChart.setMinWidth(900);
		lineChart.setMaxWidth(900);
		lineChart.setTitle("メダル数推移");

		xAxis.setLabel("回数");
		yAxis.setLabel("メダル");

		showRetiredPlayer.setIndeterminate(false);
		showRetiredPlayer.setSelected(true);
		showRetiredPlayer.setOnAction((ActionEvent) -> {
			for (Data d : data) {
				d.setSelected(showRetiredPlayer.isSelected() || d.getCurrentMedal() > 0);
			}
			updateGraph();
		});

		showPlayersCheckBoxes.getChildren().addAll(showPlayersBoxes);

		scrollPane.setMinHeight(200);
		scrollPane.setMaxHeight(200);
		scrollPane.setContent(showPlayersCheckBoxes);

		config.setMinWidth(100);
		config.setMaxWidth(100);

		config.getChildren().addAll(showRetiredPlayer, scrollPane);

		root.getChildren().addAll(config, lineChart);

		updateGraph();

		Scene scene = new Scene(root, 1000, 600);
		stage.setScene(scene);
		stage.show();
	}

	public void updateGraph() {
		lineChart.getData().clear();

		int step = (int)(Math.pow(255, 3) / data.size());
		int colorNum = 0;
		for (Data d : data) {
			int red = (int)(colorNum / Math.pow(255, 2));
			int green = (colorNum / 255) % 255;
			int blue = colorNum % 255;

			colorNum += step;

			if (!d.isShow()) {
				continue;
			}

			XYChart.Series series = new XYChart.Series();
			series.setName(d.getName());

			for (int i=0; i<count; i++) {
				series.getData().add(new XYChart.Data(i, d.getMedal(i)));
			}

			lineChart.getData().add(series);

			Node line = series.getNode().lookup(".chart-series-line");
			line.setStyle("-fx-stroke: rgba(" + red + ", " + green + ", " + blue + ", 1.0);");

			for (Object obj : series.getData()) {
				XYChart.Data sd = (XYChart.Data)obj;
				Node symbol = sd.getNode().lookup(".chart-line-symbol");
				symbol.setStyle("-fx-background-color: rgb(" + red + ", " + green + ", " + blue + "), white;");
			}
		}
	}

	public static void main(String[] args) {
		if (args.length <= 0) {
			System.out.println("Usage: java Graph [LOG FILE]");
			System.out.println("グラフ用のログファイル名を指定してください");
			System.exit(1);
		}

		Graph.FILE_NAME = args[0];
		launch(args);
	}
}
