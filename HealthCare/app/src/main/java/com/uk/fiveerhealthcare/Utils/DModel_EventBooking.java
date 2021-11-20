package com.uk.fiveerhealthcare.Utils;

public class DModel_EventBooking {

    String packageId, bookingId, artistId, startDate, startTime, dressCode, eventType, eventSetup, eventAddress, amount;
    double latitude, longitude;

    String selectedRequestedLocation="";

    public String getSelectedRequestedLocation() {
        return selectedRequestedLocation;
    }

    public void setSelectedRequestedLocation(String selectedRequestedLocation) {
        this.selectedRequestedLocation = selectedRequestedLocation;
    }


    private String instrumentType;
    private String addInfo;
    private String playTime;

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public DModel_EventBooking() {
        bookingId = "";
        packageId = "";
        artistId = "";
        startDate = "";
        startTime = "";
        dressCode = "";
        eventType = "";
        eventSetup = "";
        eventAddress = "";
        amount = "";
        latitude = 0;
        longitude = 0;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDressCode() {
        return dressCode;
    }

    public void setDressCode(String dressCode) {
        this.dressCode = dressCode;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getAmount() {
        return amount;
    }

    public void setEventSetup(String eventSetup) {
        this.eventSetup = eventSetup;
    }

    public String getEventSetup() {
        return eventSetup;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getEventType() {
        return eventType;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return bookingId;
    }
}
