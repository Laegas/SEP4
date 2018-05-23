package util.thermal;

import model.igc.DataPoint;
import model.outputData.IGCDataGroup;

public class ThermalFinderImp implements ThermalFinder  {


    public boolean isThermalUsingPressureAltitude(IGCDataGroup points) {
        if(points.getDataPoints().size()<10)
            return false;
        DataPoint[] scope = new DataPoint[10];
        for(int i = 0;i<points.getDataPoints().size();i++)
        {
            scope[i%10] = points.getDataPoints().get(i);
            if(checkGPS(scope,i))
                return true;
        }
        return false;
    }

    public boolean isThermalUsingGPSAltitude(IGCDataGroup points) {
        if(points.getDataPoints().size()<10)
             return false;
        DataPoint[] scope = new DataPoint[10];
        for(int i = 0;i<points.getDataPoints().size();i++)
        {
            scope[i%10] = points.getDataPoints().get(i);
            if(checkPressure(scope,i))
                return true;
        }
        return false;
    }

    private boolean checkGPS(DataPoint[] scope, int index)
    {
        if(scope[(index+1)%10] ==null)
            return false;
        int first = scope[(index+1)%10].getGPSAltitude();
        double sum = 0;
        for(int i = 0;i<scope.length;i++)
        {
                sum+= scope[(i+1+index)%10].getGPSAltitude();
        }
        return 0<(sum/10 - first);
    }
    private boolean checkPressure(DataPoint[] scope,int index)
    {
        if(scope[(index+1)%10] ==null)
            return false;
        int first = scope[(index+1)%10].getPressureAltitude();
        int sum = 0;
        for(int i = 0;i<scope.length;i++)
        {
            sum+= scope[(i+1+index)%10].getGPSAltitude();
        }
        return 0<(sum/10 - first);
    }
}
