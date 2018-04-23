package com.salome.tagthebus;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by 54088 on 23/04/2018.
 */

public class PictureInBdd {

    private int id;
    private int busStopId;
    private String title;
    private Date creationDate;
    //private String pathToPicture;

    public PictureInBdd(String _title, int _busStopId /*, String _pathToPicture*/)
    {
        this.title = _title;
        this.creationDate = new Date();
        this.busStopId = _busStopId;
        creationDate = Calendar.getInstance().getTime();
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

    /*public void setCreationDate(String newDeadline)
    {
        creationDate.setTime(Long.valueOf(newDeadline));
    }*/

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

}
