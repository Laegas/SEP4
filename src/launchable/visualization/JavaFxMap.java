package launchable.visualization;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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

public class JavaFxMap extends Application {
    private Timeline locationUpdateTimeline;

    @Override public void start(Stage stage) {

        // Create a WebView
        WebView webView = new WebView();

        // Get WebEngine via WebView
        WebEngine webEngine = webView.getEngine();

        // Load page
        webEngine.load(getClass().getResource("../../visualization/javaFxMaps/googlemap.html").toString());

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
        Button zoomIn = new Button("Zoom In");
        zoomIn.setOnAction(actionEvent -> webEngine.executeScript("document.zoomIn()"));
        Button zoomOut = new Button("Zoom Out");
        zoomOut.setOnAction(actionEvent -> webEngine.executeScript("document.zoomOut()"));
        // create toolbar
        ToolBar toolBar = new ToolBar();
        toolBar.getStyleClass().add("map-toolbar");
        toolBar.getItems().addAll(
                road, satellite, hybrid, terrain,
                createSpacer(),
                new Label("Location:"), searchBox, zoomIn, zoomOut);
        // create root
        BorderPane root = new BorderPane();
        root.getStyleClass().add("map");
        root.setCenter(webView);
        root.setTop(toolBar);
        // create scene
        stage.setTitle("Web Map");
        Scene scene = new Scene(root,1000,700, Color.web("#666970"));
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("../../visualization/javaFxMaps/map.css").toString());
        // show stage
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
