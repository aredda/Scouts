package com.example.scoutinterfacedesign.Helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelperMessage {
    public static void showToastMessage(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void showAlertMessage(Context context, String message)
    {
        AlertDialog.Builder box = new AlertDialog.Builder(context);
        box.setTitle("انتباه!");
        box.setMessage(message);
        box.setPositiveButton("OK", null);

        box.show();
    }
    public static String formatDate(Date d)
    {
        return new SimpleDateFormat("yyyy/MM/dd").format(d);
    }
}
