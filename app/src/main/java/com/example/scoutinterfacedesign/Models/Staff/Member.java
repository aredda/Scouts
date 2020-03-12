package com.example.scoutinterfacedesign.Models.Staff;

import android.net.Uri;

import com.example.scoutinterfacedesign.Models.Norms.EGender;
import com.example.scoutinterfacedesign.Models.System.Enrollment;

import java.util.Date;
import java.util.List;

public class Member extends Person
{
    // Personal Info
    public String code;
    public Uri image;
    public Date birthDate;
    public String birthPlace;
    public String nationality;
    public String address;
    public Date enrollmentDate;

    public Member(int id)
    {
        super(id);
    }
    public Member(int id, String firstName, String lastName)
    {
        super(id, firstName, lastName);
    }
    public Member(int id, String firstName, String lastName, EGender gender)
    {
        super(id, firstName, lastName, gender);
    }

    public String getNature()
    {
        return null;
    }
}
