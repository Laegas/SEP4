package util.igc;

import model.IGCJoinWeather;
import model.igc.DataPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kenneth on 24/05/2018.
 */
public class RemoveDuplicate {

    public static List<IGCJoinWeather> getUniqueByGridIndex(List<IGCJoinWeather> list) {
        Map<String, IGCJoinWeather> map = new HashMap<>();

        String key;
        for (IGCJoinWeather item : list) {
            key = item.getDataPoint().getLatitude().getGridIndex() + ":" + item.getDataPoint().getLongitude().getGridIndex();

            map.put(key, item);
        }

        List<IGCJoinWeather> result = new ArrayList<>();
        for (Map.Entry<String, IGCJoinWeather> item : map.entrySet()) {
            result.add(item.getValue());
        }

        return result;

    }


    public static List<DataPoint> getUniqueDataPoint(List<DataPoint> dataPoints) {
        Map<String, DataPoint> map = new HashMap<>();

        String key;
        for (DataPoint item : dataPoints) {
            key = item.getLatitude().getGridIndex() + ":" + item.getLongitude().getGridIndex();

            map.put(key, item);
        }

        List<DataPoint> result = new ArrayList<>();
        for (Map.Entry<String, DataPoint> item : map.entrySet()) {
            result.add(item.getValue());
        }

        return result;

    }
}
