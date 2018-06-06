package util.igc;

import database.DAO.DaoManager;
import model.IGCJoinWeather;
import model.geography.InvalidCoordinatesException;
import model.geography.LocationPoint;
import model.igc.DataPoint;
import model.igc.Flight;

import java.util.*;

/**
 * Created by kenneth on 24/05/2018.
 */
public class RemoveDuplicate {

    public static List<IGCJoinWeather> getUniqueByGridIndex(List<IGCJoinWeather> list) {
        long startTime = printStartTime();
        Map<String, IGCJoinWeather> map = new HashMap<>();

        String key;
        for (IGCJoinWeather item : list) {
            try {
                key = item.getDataPoint().getLatitude().getGridIndex() + ":" + item.getDataPoint().getLongitude().getGridIndex();
                map.put(key, item);
            } catch (InvalidCoordinatesException e) {
                e.printStackTrace();
            }
        }

        List<IGCJoinWeather> result = new ArrayList<>();
        for (Map.Entry<String, IGCJoinWeather> item : map.entrySet()) {
            result.add(item.getValue());
        }

        printEndTime(startTime);
        return result;

    }


    public static List<DataPoint> getUniqueDataPoint(List<DataPoint> dataPoints) {
        long startTime = printStartTime();
        Map<String, DataPoint> map = new HashMap<>();

        String key;
        for (DataPoint item : dataPoints) {
            try {
                key = item.getLatitude().getGridIndex() + ":" + item.getLongitude().getGridIndex();
                map.put(key, item);
            } catch (InvalidCoordinatesException e) {
                e.printStackTrace();
            }
        }

        List<DataPoint> result = new ArrayList<>();
        for (Map.Entry<String, DataPoint> item : map.entrySet()) {
            result.add(item.getValue());
        }

        printEndTime(startTime);
        return result;

    }
    public static List<LocationPoint> getUniqueLocationPointsIndexes(List<LocationPoint> points)
    {
        long startStime = printStartTime();
        List<LocationPoint> uniqueList = new ArrayList<>();
        for (LocationPoint point: points)
        {
                if(!uniqueList.contains(point)) {
                    uniqueList.add(point);
                }
        }
        printEndTime(startStime);
        return uniqueList;
    }


    private static long printStartTime() {
        return 0;
    }

    private static void printEndTime(long startTime) {
       /* long tmp = System.currentTimeMillis();
        System.out.println("duretion: " + (tmp - startTime)/1000 + " seconds");*/
    }
}
