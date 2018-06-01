package launchable.visualization;

import config.FileConfig;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import static config.VisualizationConfig.LAYER_NAMES;

public class JavaFxMap extends Application {
    private Timeline locationUpdateTimeline;

    @Override public void start(Stage stage) {

        // Create a WebView
        WebView webView = new WebView();

        // Get WebEngine via WebView
        WebEngine webEngine = webView.getEngine();

        // Load page
        webEngine.load(getClass().getResource(FileConfig.VISUALIZATION_RESOURCES_PATH+"googlemap.html").toString());

        // create map type buttons
        final ToggleGroup mapTypeGroup = new ToggleGroup();
        final ToggleButton road = new ToggleButton("Road");
        road.setSelected(true);
        road.setToggleGroup(mapTypeGroup);
        final ToggleButton satellite = new ToggleButton("Satellite");
        satellite.setToggleGroup(mapTypeGroup);
        final ToggleButton hybrid = new ToggleButton("Hybrid");
        hybrid.setToggleGroup(mapTypeGroup);
        final ToggleButton terrain = new ToggleButton("Terrain");
        terrain.setToggleGroup(mapTypeGroup);
        mapTypeGroup.selectedToggleProperty().addListener((observableValue, toggle, toggle1) -> {
            if (road.isSelected()) {
                webEngine.executeScript("document.setMapTypeRoad()");
            } else if (satellite.isSelected()) {
                webEngine.executeScript("document.setMapTypeSatellite()");
            } else if (hybrid.isSelected()) {
                webEngine.executeScript("document.setMapTypeHybrid()");
            } else if (terrain.isSelected()) {
                webEngine.executeScript("document.setMapTypeTerrain()");
            }
        });

        // add search
        TextField searchBox = new TextField("95054");
        searchBox.setPromptText("Search");
        searchBox.textProperty().addListener((observableValue, s, s1) -> {
            // delay location updates to we don't go too fast file typing
            if (locationUpdateTimeline!=null) locationUpdateTimeline.stop();
            locationUpdateTimeline = new Timeline();
            locationUpdateTimeline.getKeyFrames().add(
                    new KeyFrame(new Duration(400), actionEvent -> webEngine.executeScript("document.goToLocation(\""+
                            searchBox.getText()+"\")"))
            );
            locationUpdateTimeline.play();
        });

        // check combo box experiment
        // create the data to show in the CheckComboBox
        final ObservableList<String> strings = FXCollections.observableArrayList();
        strings.addAll(LAYER_NAMES);

        // Create the CheckComboBox with the data
        final CheckComboBox<String> checkComboBox = new CheckComboBox<>(strings);
        checkComboBox.setMaxWidth(130);
        checkComboBox.setPrefWidth(130);
        checkComboBox.setMinWidth(130);
        checkComboBox.getCheckModel().check(0);

        // and listen to the relevant events (e.g. when the selected indices or selected items change).
        checkComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
            // todo
            ObservableList<String> selectedStrings = checkComboBox.getCheckModel().getCheckedItems();
            System.out.println(checkComboBox.getCheckModel().getCheckedItems());
        });

        // create slider
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(100);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(100);
        slider.setMinorTickCount(10);
        slider.setBlockIncrement(10);
        slider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            // todo
            public String toString(Double n) {
                if (n < 50) return "None";
                return "All";
            }

            // todo
            @Override
            public Double fromString(String s) {
                switch (s) {
                    case "None":
                        return 0d;
                    case "All":
                        return 100d;
                    default:
                        return 100d;
                }
            }
        });
        PauseTransition pause = new PauseTransition(Duration.millis(500));
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            pause.setOnFinished((event) -> {
                // todo check
                System.out.println("document.restyle(-1, " + (1 - newValue.intValue() / 100.0) + ")");
                webEngine.executeScript("document.restyle(-1, " + (1 - newValue.intValue() / 100.0) + ")");
            });
            pause.playFromStart();
        });

        // create toolbar
        ToolBar toolBar = new ToolBar();
        toolBar.getStyleClass().add("map-toolbar");
        toolBar.getItems().addAll(
                road, satellite, hybrid, terrain,
                createSpacer(),
                new Label("Visible:"), slider,
                createSpacer(),
                checkComboBox,
                createSpacer(),
                new Label("Location:"), searchBox);
        // create root
        BorderPane root = new BorderPane();
        root.getStyleClass().add("map");
        root.setCenter(webView);
        root.setTop(toolBar);
        // create scene
        stage.setTitle("Thermal Seeker");
        Scene scene = new Scene(root,1000,700, Color.web("#666970"));
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource(FileConfig.VISUALIZATION_RESOURCES_PATH+"map.css").toString());
        // show stage
        stage.setMaximized(true);
        //stage.setFullScreen(true);
        stage.show();
    }

    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    static { // use system proxy settings when standalone application
        System.setProperty("java.net.useSystemProxies", "true");
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}
