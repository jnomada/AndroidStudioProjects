package myapp.jsealey.myplaces;

import java.io.Serializable;

public class Place implements Serializable {

    /////////////////////////////////////
    // Attributes

    String name;
    double lattitude;
    double longitude;
    String address = "";

    /////////////////////////////////////
    // Constructor

    public Place(String name, double lattitude, double longitude, String address) {
        this.name = name;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.address = address;
    }


    public String getName() {
        return name;
    }

    public double getLattitude() {
        return lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

}
