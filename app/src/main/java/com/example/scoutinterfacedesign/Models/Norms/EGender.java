package com.example.scoutinterfacedesign.Models.Norms;

public enum EGender {
    Male, Female;

    @Override
    public String toString() {
        switch (this)
        {
            case Female:
                return "أنثى";
        }
        return "ذكر";
    }
}
