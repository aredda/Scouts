package com.example.scoutinterfacedesign.Models.System;

import com.example.scoutinterfacedesign.Models.Norms.EMission;
import com.example.scoutinterfacedesign.Models.Staff.Leader;
import com.example.scoutinterfacedesign.Models.Staff.Member;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Mission
{
    public Arrangement arrangement;
    public Leader leader;
    public EMission mission;
    public Date date;

    public Mission(Arrangement arrangement, Leader leader, EMission mission, Date date) {
        this.arrangement = arrangement;
        this.leader = leader;
        this.mission = mission;
        this.date = date;
    }

    public Leader getMember()
    {
        return this.leader;
    }
    public EMission getMission()
    {
        return this.mission;
    }
    public String getDate() {
        return new SimpleDateFormat("yyyy/MM/dd").format(this.date);
    }

    @Override
    public String toString() {
        return mission.toString() + " " + arrangement.toString();
    }
}
