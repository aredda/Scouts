package com.example.scoutinterfacedesign.Helpers;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;

import com.example.scoutinterfacedesign.Models.Norms.EUnitType;
import com.example.scoutinterfacedesign.R;

import java.util.Hashtable;

public class HelperResource
{

    public static class ColorTheme
    {
        public int colorMain;
        public int colorSecondary;

        public ColorTheme(int main, int secondary)
        {
            colorMain = main;
            colorSecondary = secondary;
        }
    }

    private static Hashtable<EUnitType, ColorTheme> themes;

    private static void initializeThemes()
    {
        if(themes != null)
            return;

        themes = new Hashtable<>();

        themes.put(EUnitType.UT1, new ColorTheme(R.color.colorYellow, R.color.colorRed));
        themes.put(EUnitType.UT2, new ColorTheme(R.color.colorGreenLight, R.color.colorGreen));
        themes.put(EUnitType.UT3, new ColorTheme(R.color.colorRed, R.color.colorOrange));
    }

    public static ColorTheme getUnitTheme(EUnitType unitType)
    {
        initializeThemes();

        if(themes.containsKey(unitType))
            return themes.get(unitType);

        return null;
    }

    public static int getUnitLogo(EUnitType unitType)
    {
        switch (unitType)
        {
            case UT1:
                return R.drawable.ut01;
            case UT2:
                return R.drawable.ut02;
            case UT3:
                return R.drawable.ut03;
        }
        return -1;
    }

    public static Bitmap compressBitmap(ContentResolver contentResolver, Uri targetUri) throws Exception
    {
        // Retrieve the original image
        Bitmap result = MediaStore.Images.Media.getBitmap(contentResolver ,targetUri);
        // Compress the image
        result = Bitmap.createScaledBitmap(result, result.getWidth() / 2, result.getHeight() / 2, true);

        return result;
    }

    public final static int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1;

    public static boolean checkPermission(Context context, String permission)
    {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity, String permission, int request_code)
    {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, request_code);
    }

}
