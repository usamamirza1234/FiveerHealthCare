package com.uk.fiveerhealthcare.Utils;


public class MyLocation {

    private double latitude;
    private double longitude;
    private String streetAdress;

    public MyLocation() {
        setLatLng(AppConstt.VOID_LATLONG, AppConstt.VOID_LATLONG);
        this.streetAdress = "";
    }


    public MyLocation(double latitude, double longitude) {
        setLatLng(latitude, longitude);
        this.streetAdress = "";
    }


    public MyLocation(double latitude, double longitude, String streetAdress) {
        setLatLng(latitude, longitude);
        this.streetAdress = streetAdress;
    }

    private void setLatLng(double _latitude, double _longitude) {

        //Code snippet from google services
//        if (-180.0D <= _longitude && _longitude < 180.0D) {
//            this.longitude = _longitude;
//        } else {
//            this.longitude = ((_longitude - 180.0D) % 360.0D + 360.0D) % 360.0D - 180.0D;
//        }
//
//        this.latitude  = Math.max(-90.0D, Math.min(90.0D, _latitude));

        if ((-90.0D <= _latitude && _latitude <= 90.0D) &&
                (-180.0D <= _longitude && _longitude <= 180.0D)) {
            this.latitude = _latitude;
            this.longitude = _longitude;
        } else {
            this.latitude = AppConstt.VOID_LATLONG;//Practically impossible value
            this.longitude = AppConstt.VOID_LATLONG;//Practically impossible value
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getStreetAdress() {
        return streetAdress;
    }
}


