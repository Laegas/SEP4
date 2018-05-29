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
        Map<String, IGCJoinWeather> map = new HashMap<>();

        String key;
        for (IGCJoinWeather item : list) {
            try {
                key = item.getDataPoint().getLatitude().getGridIndex() + ":" + item.getDataPoint().getLongitude().getGridIndex();
                map.put(key, item);
            } catch (InvalidCoordinatesException e) {
                System.out.println("REMOVE ME WHEN ETL IS FINISHED. NIKI, GET YOUR SHIT TOGETHER");
                e.printStackTrace();
            }
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
            try {
                key = item.getLatitude().getGridIndex() + ":" + item.getLongitude().getGridIndex();
                map.put(key, item);
            } catch (InvalidCoordinatesException e) {
                System.out.println("REMOVE ME WHEN ETL IS FINISHED. NIKI, GET YOUR SHIT TOGETHER");
                e.printStackTrace();
            }
        }

        List<DataPoint> result = new ArrayList<>();
        for (Map.Entry<String, DataPoint> item : map.entrySet()) {
            result.add(item.getValue());
        }

        return result;

    }
    public static List<LocationPoint> getUniqueLocationPointsIndexes(List<LocationPoint> points)
    {
        List<LocationPoint> uniqueList = new ArrayList<>();
        for (LocationPoint point: points)
        {
                if(!uniqueList.contains(point)) {
                    uniqueList.add(point);
                }
        }
        return uniqueList;
    }

}
