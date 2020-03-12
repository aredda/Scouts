package com.example.scoutinterfacedesign.Models.Norms;

public enum EUnitType
{
    UT1, UT2, UT3;

    @Override
    public String toString() {
        switch (this)
        {
            case UT1:
                return "سرب";
            case UT2:
                return "كتيبة";
            case UT3:
                return "فوج";
        }
        return "وحدة";
    }

    public static EUnitType getValue (String type)
    {
        switch (type)
        {
            case "سرب":
                return UT1;
            case "كتيبة":
                return UT2;
        }
        return UT3;
    }
}
