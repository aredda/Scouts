package com.example.scoutinterfacedesign.Models.System;

import com.example.scoutinterfacedesign.Models.Norms.EUnitType;

import java.util.ArrayList;
import java.util.List;

public class Group extends Arrangement
{
    public List<Unit> units;

    public Group(int id)
    {
        super(id);

        this.units = new ArrayList<>();
    }
    public Group(int id, String name)
    {
        this(id);

        this.name = name;
    }

    public List<Unit> getUnits(EUnitType unitType)
    {
        ArrayList<Unit> outUnits = new ArrayList<>();

        for (Unit u : this.units)
            if(u.type == unitType)
                outUnits.add(u);

        return outUnits;
    }

    public String unitCount()
    {
        return  this.units.size() + " وحدة";
    }
}
