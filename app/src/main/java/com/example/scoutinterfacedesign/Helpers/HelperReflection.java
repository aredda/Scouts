package com.example.scoutinterfacedesign.Helpers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HelperReflection
{
    public static Object Invoke(String methodName, Object target)
    {
        try
        {
            Method m = null;
            for(Method i : target.getClass().getMethods())
                if(i.getName() == methodName) m = i;

                if (m == null)
                    throw new Exception();

                return m.invoke(target);
        }
        catch (Exception x)
        {
            return null;
        }
    }

    public static <T> ArrayList<T> Except(ArrayList<T> origin, T... exceptions)
    {
        ArrayList<T> result = new ArrayList<>();

        for ( T i : origin )
            for ( T x : exceptions )
                if (!i.equals(x)) {
                    result.add(i);
                    break;
                }

        return result;
    }
}
