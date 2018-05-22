package launchable;

import config.VisualizationConfig;
import model.geography.InvalidCoordinatesException;
import model.geography.Latitude;
import model.geography.Longitude;

public class LatitudeAndLongitudeTest {
    public static void main(String[] args) {
        try {

            for(int i = 0; i <= VisualizationConfig.HEIGHT; i++){
                Latitude l = new Latitude(54 + ((i + 120) / 240), ((30 + (i / 4)) % 60), ((i % 4) * 15 + 14));
                System.out.println("Latitude: " + l.getDegree() + " " + l.getMinute() + " " + l.getSecond() + " (" + l
                        .getGridIndex() + ")");
            }

            for(int i = 0; i <= VisualizationConfig.WIDTH; i++) {
                Longitude l = new Longitude(8 + (i / 120), (i / 2 % 60), i % 2 * 30);
                System.out.println("Longitude: " + l.getDegree() + " " + l.getMinute() + " " + l.getSecond() + " (" + l
                        .getGridIndex() + ")");
            }
        } catch(InvalidCoordinatesException e) {
            e.printStackTrace();
        }
    }
}
