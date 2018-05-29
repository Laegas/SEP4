package model.geography;

public class CountryName {

    private String countryName;

    public CountryName(String name)
    {
        setCountryName(name);
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }
}
