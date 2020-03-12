package com.example.scoutinterfacedesign.Models.Staff;

import com.example.scoutinterfacedesign.Models.Norms.EGender;
import com.example.scoutinterfacedesign.Models.System.Badge;
import com.example.scoutinterfacedesign.Models.System.Mission;

import java.util.ArrayList;

public class Leader extends Member
{

    public String mobile;

    // Mission tracking
    public Mission currentMission;
    public ArrayList<Mission> missions;

    // Badge
    public ArrayList<Badge> badges;

    public Leader(int id) {
        super(id);

        missions = new ArrayList<>();
        badges = new ArrayList<>();
    }

    public Leader(int id, String firstName, String lastName) {
        super(id, firstName, lastName);

        missions = new ArrayList<>();
        badges = new ArrayList<>();
    }

    public Leader(int id, String firstName, String lastName, EGender gender) {
        super(id, firstName, lastName, gender);

        missions = new ArrayList<>();
        badges = new ArrayList<>();
    }

    public String getCurrentMission()
    {
        if(currentMission != null)
            return currentMission.mission.toString() + " " + currentMission.arrangement.toString();

        return "لم يقم بأي مهمة";
    }

    @Override
    public String getNature() {
        return "قائد";
    }
}
