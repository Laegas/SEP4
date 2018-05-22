package util.thermal;

import model.igc.DataPoint;
import model.outputData.IGCDataGroup;

public class ThermalVerifierImp implements ThermalVerifier  {

    @Override
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

    @Override
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
        int first = scope[(index+1)%10].getGPSAltitude();
        double sum = 0;
        for(int i = (index+1)%10;i<scope.length;i = (i+1)%10)
        {
            if(scope[i]!=null)
            {
                sum+= scope[i].getGPSAltitude();

            }
        }
        return 0<(sum/10 - first);
    }
    private boolean checkPressure(DataPoint[] scope,int index)
    {
        int first = scope[(index+1)%10].getPressureAltitude();
        int sum = 0;
        for(int i = (index+1)%10;i<scope.length;i = (i+1)%10)
        {
            if(scope[i]!=null)
            {
                sum+= scope[i].getPressureAltitude();
            }
        }
        return 0<(sum/10 - first);
    }
}
