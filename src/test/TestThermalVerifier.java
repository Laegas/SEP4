package test;

import model.geography.Latitude;
import model.geography.Longitude;
import model.igc.DataPoint;
import model.outputData.IGCDataGroup;
import model.time.Time;

import java.util.ArrayList;

public class TestThermalVerifier {
    public static void main(String[] args){
//        ThermalVerifier victor = new ThermalVerifierImp();


        ArrayList<DataPoint> points = new ArrayList<>();

        for(int i = 0;i<10;i++)
        {
            points.add(new DataPoint(new Time(1,1,1),new Longitude(1,1,1), new Latitude(1,1,1),
                    'c',10-i,10-i));
        }

        IGCDataGroup group = new IGCDataGroup(points, 1,1);
//        System.out.println(victor.isThermalUsingGPSAltitude(group));
    }
}
