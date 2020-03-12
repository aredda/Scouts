package com.example.scoutinterfacedesign.Models.Staff;

import com.example.scoutinterfacedesign.Models.Entity;
import com.example.scoutinterfacedesign.Models.Norms.EGender;

public abstract class Person extends Entity
{
    public String firstName;
    public String lastName;
    public EGender gender;

    public Person(int id)
    {
        super(id);
    }
    public Person(int id, String firstName, String lastName)
    {
        this(id);

        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Person(int id, String firstName, String lastName, EGender gender)
    {
        this(id, firstName, lastName);

        this.gender = gender;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}
