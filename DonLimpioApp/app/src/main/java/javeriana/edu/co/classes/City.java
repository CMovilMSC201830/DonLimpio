package javeriana.edu.co.classes;

public class City {

    int cityCode;
    String cityName;

    public City(int cityCode, String cityName) {
        this.cityCode = cityCode;
        this.cityName = cityName;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public City(int cityCode) {

        this.cityCode = cityCode;
    }
}
