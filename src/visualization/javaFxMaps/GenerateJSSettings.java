package visualization.javaFxMaps;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static config.VisualizationConfig.*;

public class GenerateJSSettings {
    private static GenerateJSSettings instance;

    private GenerateJSSettings() {}

    public static GenerateJSSettings getInstance() {
        if(instance == null)
            instance = new GenerateJSSettings();
        return instance;
    }

    public void generateSettings() {
        List<String> lines = new ArrayList<>();
        lines.add("// settings.js generated by GenerateJSSettings.java");
        lines.add("var latitude = " + ((LATITUDE_START + LATITUDE_END) / 2) + ";");
        lines.add("var longitude = " + ((LONGITUDE_START + LONGITUDE_END) / 2) + ";");
        lines.add("var zoom = " + ZOOM + ";");

        try {
            Path file = Paths.get("src/visualization/javaFxMaps/settings.js");
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}