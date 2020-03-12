package com.example.scoutinterfacedesign.Models.System;

import com.example.scoutinterfacedesign.Models.Norms.ECourse;
import com.example.scoutinterfacedesign.Models.Staff.Leader;

import java.util.Date;

public class Badge
{
    public Date date;
    public Leader leader;
    public ECourse course;

    public Badge(Leader leader, ECourse course)
    {
        this.course = course;
        this.leader = leader;
        this.date = new Date();
    }
    public Badge(Leader leader, ECourse course, Date date)
    {
        this(leader, course);

        this.date = date;
    }

    public String getBadge()
    {
        return course.toString();
    }

}
