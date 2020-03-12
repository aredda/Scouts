package com.example.scoutinterfacedesign.Models.System;

import com.example.scoutinterfacedesign.Models.Staff.Adherent;
import com.example.scoutinterfacedesign.Models.Staff.Member;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Enrollment {
    public Unit unit;
    public Adherent member;
    public Date date;

    public Enrollment(Unit unit, Adherent member) {
        this.unit = unit;
        this.member = member;
        this.date = new Date();
    }

    public Enrollment(Unit unit, Adherent member, Date date) {
        this(unit, member);

        this.date = date;
    }

    public Adherent getMember() {
        return member;
    }

    public String getDate() {
        return new SimpleDateFormat("yyyy/MM/dd").format(this.date);
    }

    public String getMemberTitle() {
        return member.getTitle();
    }
}
