package com.example.scoutinterfacedesign.Models.Staff;

import android.provider.ContactsContract;

import com.example.scoutinterfacedesign.Models.DataController;
import com.example.scoutinterfacedesign.Models.Norms.EGender;
import com.example.scoutinterfacedesign.Models.System.Enrollment;

import java.util.ArrayList;
import java.util.List;

public class Adherent extends Member {

    // Parents Info
    public String fatherName, fatherMobile;
    public String motherName, motherMobile;

    // Career tracking
    public Enrollment currentState;
    public ArrayList<Enrollment> career;

    public Adherent(int id) {
        super(id);
    }

    public Adherent(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public Adherent(int id, String firstName, String lastName, EGender gender) {
        super(id, firstName, lastName, gender);
    }

    public String getTitle()
    {
        return DataController.get().getTitle(currentState.unit.type, gender);
    }

    @Override
    public String getNature() {
        return getTitle();
    }
}
