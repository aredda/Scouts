package com.example.scoutinterfacedesign.Models.System;

import com.example.scoutinterfacedesign.Models.Entity;
import com.example.scoutinterfacedesign.Models.Norms.EMission;
import com.example.scoutinterfacedesign.Models.Staff.Leader;
import com.example.scoutinterfacedesign.Models.Staff.Member;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Arrangement extends Entity implements Serializable
{

    public String name;
    public List<Mission> direction;

    public Arrangement(int id)
    {
        super(id);

        this.direction = new ArrayList<>();
    }
    public Arrangement(int id, String name)
    {
        this(id);

        this.name = name;
    }

    public Leader getLeader()
    {
        return getDirectionMember(EMission.M1);
    }

    public Leader getDirectionMember(EMission mission)
    {
        for(Mission m : this.direction)
            if(m.mission == mission)
                return m.leader;

            return null;
    }

    public Mission getMemberMission(Leader leader)
    {
        for( Mission m : direction )
            if(m.leader.id == leader.id)
                return m;

            return null;
    }

    public Mission addMission(Leader leader, EMission mission)
    {
        if (getDirectionMember(mission) != null)
            return null;

        Mission m = new Mission(this, leader, mission, new Date());

        this.direction.add(m);

        return m;
    }

    public boolean removeMission(EMission mission)
    {
        Mission target = null;

        for(Mission m : direction)
            if(m.mission == mission)
                target = m;

            if(target == null)
                return false;

            return direction.remove(target);
    }

    public ArrayList<EMission> getAvailableMissions()
    {
        ArrayList<EMission> available = new ArrayList<>();

        for(EMission m : EMission.values())
            if(getDirectionMember(m) == null)
                available.add(m);

            return available;
    }

    @Override
    public String toString() {
        return "م. " + this.name;
    }
}
