package com.example.scoutinterfacedesign.Models.Norms;

public enum ECourse
{
    C1, C2, C3, C4, C5, C6, C7, C8;

    @Override
    public String toString() {
        switch (this)
        {
            case C1:
                return "دورة المعلومات العامة";
            case C2:
                return "دورة المعلومات الأساسية";
            case C3:
                return "دورة المعلومات المتقدمة - دارس";
            case C4:
                return "دورة المعلومات المتقدمة - مناقش";
            case C5:
                return "دورة مساعد قائد تدريب - دارس";
            case C6:
                return "دورة مساعد قائد تدريب - مناقش";
            case C7:
                return "دورة قائد تدريب - دارس";
        }
        return "دورة قائد تدريب - مناقش";
    }
}
