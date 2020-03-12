package com.example.scoutinterfacedesign.Models.System;

import com.example.scoutinterfacedesign.Models.Norms.EUnitType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Unit extends Arrangement
{
    public EUnitType type;
    public Group group;

    public List<Enrollment> enrollments;

    public Unit(int id)
    {
        super(id);

        this.enrollments = new ArrayList<>();
    }
    public Unit(int id, String name, EUnitType type, Group group)
    {
        this(id);

        this.name = name;
        this.type = type;
        this.setGroup(group);
    }

    public void setGroup(Group g)
    {
        g.units.add(this);

        this.group = g;
    }

    public String memberCount()
    {
        return 0 + " فرد";
    }

    @Override
    public String toString() {
        return type.toString() + " " + this.name;
    }
}
