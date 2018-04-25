package com.salome.tagthebus;

import java.util.Date;

/**
 * Created by 54088 on 23/04/2018.
 */

public class PictureInBdd {

    private int id;
    private int busStopId;
    private String title;
    private Date creationDate;
    private String pathToPicture;
    private boolean isFrontCamera;

    public PictureInBdd(String _title, int _busStopId, String _pathToPicture, Date _creationDate, boolean _isFrontCamera)
    {
        this.title = _title;
        this.creationDate = _creationDate;
        this.pathToPicture = _pathToPicture;
        this.busStopId = _busStopId;
        this.isFrontCamera = _isFrontCamera;
    }

    public PictureInBdd(String _title, int _busStopId, String _pathToPicture, String _creationDate, boolean _isFrontCamera)
    {
        this.title = _title;
        this.creationDate = new Date();
        this.creationDate.setTime(Long.valueOf(_creationDate));;
        this.pathToPicture = _pathToPicture;
        this.busStopId = _busStopId;
        this.isFrontCamera = _isFrontCamera;
    }

    public int getId()
        {
            return this.id;
        }

    public void setId(int newId)
        {
            this.id = newId;
        }

    public String getTitle()
        {
            return this.title;
        }

    public void setTitle(String newTitle)
        {
            this.title = newTitle;
        }

    public void setCreationDate(String newDeadline)
    {
        creationDate.setTime(Long.valueOf(newDeadline));
    }

    public int getBusStopId()
    {
        return this.busStopId;
    }

    public void setBusStopId(int newBusStopId)
    {
        this.busStopId = newBusStopId;
    }

    public Date getCreationDate()
    {
        return this.creationDate;
    }

    public String getPathToPicture() {
        return pathToPicture;
    }

    public void setPathToPicture(String pathToPicture) {
        this.pathToPicture = pathToPicture;
    }

    public boolean getIsFrontCamera(){return this.isFrontCamera;}

    public void setIsFrontCamera(boolean _isFrontCamera){
        this.isFrontCamera = _isFrontCamera;
    }
}
