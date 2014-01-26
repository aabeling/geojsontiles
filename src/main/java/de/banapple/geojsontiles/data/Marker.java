package de.banapple.geojsontiles.data;

public class Marker
{
    private int id;
    private float lat;
    private float lng;

    public Marker(int id, float lat, float lng)
    {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public float getLat()
    {
        return lat;
    }

    public void setLat(float lat)
    {
        this.lat = lat;
    }

    public float getLng()
    {
        return lng;
    }

    public void setLng(float lng)
    {
        this.lng = lng;
    }

    @Override
    public String toString()
    {
        return id + "/" + lat + "/" + lng;
    }
}
