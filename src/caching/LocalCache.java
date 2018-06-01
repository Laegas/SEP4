package caching;

import com.mysql.jdbc.NotImplemented;
import database.DAO.DaoManager;
import model.time.Date;
import model.weather.ICAOAirportCode;
import model.weather.WeatherRecord;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kenneth on 01/06/2018.
 */
public class LocalCache {

    private Map<String, List<WeatherRecord>> localWeatherRecordByDateAndICAOCode;
    private Map<Integer,ICAOAirportCode> localCacheAirportICAOCodeBySurrKey;
    private Map<String, Integer> localCacheAirportSurrKeyByICAOCache;

    public static final LocalCache INSTANCE = new LocalCache();

    private LocalCache() {
        this.localCacheAirportICAOCodeBySurrKey = new HashMap<>();
        this.localCacheAirportSurrKeyByICAOCache = new HashMap<>();
        this.localWeatherRecordByDateAndICAOCode = new HashMap<>();
    }

    public ICAOAirportCode airportICAOCodeBySurrKey(int surrKey) {
        ICAOAirportCode code = this.localCacheAirportICAOCodeBySurrKey.get(surrKey);
        if (code == null) {
            Map<Integer, String> tmpMap = DaoManager.WEATHER_DIMENSIONAL_DAO.airportICAOCodeBySurrKey();

            for (Integer key : tmpMap.keySet()) {
                this.localCacheAirportICAOCodeBySurrKey.put(key, new ICAOAirportCode(tmpMap.get(key)));
            }
        }

        return this.localCacheAirportICAOCodeBySurrKey.get(surrKey);
    }

    public int airportSurrKeyByICAOCode(ICAOAirportCode icaoAirportCode) {
        Integer surrCode = this.localCacheAirportSurrKeyByICAOCache.get(icaoAirportCode.getICAOCode());
        if (surrCode == null) {
            Map<String,Integer> tmpMap = DaoManager.WEATHER_DIMENSIONAL_DAO.airportSurrKeyByICAOCode();
            for (String key : tmpMap.keySet()) {
                this.localCacheAirportSurrKeyByICAOCache.put(key, tmpMap.get(key));
            }
        }
        return this.localCacheAirportSurrKeyByICAOCache.get(icaoAirportCode.getICAOCode());
    }

    public List<WeatherRecord> weatherRecordsByDateAndICAOCod(Date date, ICAOAirportCode code) {
        List<WeatherRecord> tmpList = this.localWeatherRecordByDateAndICAOCode.get(getKeyString(date, code));
        if (tmpList == null) {
            List<WeatherRecord> tmpWeather = DaoManager.WEATHER_DIMENSIONAL_DAO.getWeatherRecord(date, code);
            this.localWeatherRecordByDateAndICAOCode.put(getKeyString(date, code), tmpWeather);
        }
        return this.localWeatherRecordByDateAndICAOCode.get(getKeyString(date, code));
    }

    private static String getKeyString(Date date, ICAOAirportCode icaoAirportCode) {
        return date.getYear() + ":" + date.getMonth() + ":" + date.getDay() + ":" + icaoAirportCode.getICAOCode();
    }

}
