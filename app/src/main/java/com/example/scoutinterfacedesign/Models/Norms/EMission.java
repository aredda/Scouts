package com.example.scoutinterfacedesign.Models.Norms;

public enum EMission
{
    M1, M2, M3, M4, M5, M6;

    @Override
    public String toString() {
        switch (this)
        {
            case M1:
                return "قائد";
            case M2:
                return "نائب";
            case M3:
                return "مكلف بالادارة";
            case M4:
                return "مكلف بالمالية";
            case M5:
                return "مكلف بالتجهيز";
            case M6:
                return "مكلف بالتوثيق و الاعلام";
        }
        return "فرد";
    }
}
