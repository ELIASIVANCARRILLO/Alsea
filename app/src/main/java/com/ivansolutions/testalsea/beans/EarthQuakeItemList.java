package com.ivansolutions.testalsea.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eliasivancarrillogonzalez on 25/05/15.
 */
public class EarthQuakeItemList implements Parcelable {

    private float magnitud;
    private long time;
    private String place;

    private double latitude;
    private double longitude;

    public EarthQuakeItemList() {
    }

    public EarthQuakeItemList(Parcel in){
        readFromParcel(in);
    }

    public EarthQuakeItemList(float magnitud, long time, String place, double latitude, double longitude) {
        this.magnitud = magnitud;
        this.time = time;
        this.place = place;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public float getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(float magnitud) {
        this.magnitud = magnitud;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeFloat(magnitud);
        parcel.writeLong(time);
        parcel.writeString(place);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);

    }

    private void readFromParcel(Parcel in) {
        magnitud = in.readFloat();
        time = in.readLong();
        place = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Parcelable.Creator<EarthQuakeItemList> CREATOR = new Parcelable.Creator<EarthQuakeItemList>() {
        public EarthQuakeItemList createFromParcel(Parcel in) {
            return new EarthQuakeItemList(in);
        }

        public EarthQuakeItemList[] newArray(int size) {
            return new EarthQuakeItemList[size];
        }
    };
}
