package com.example.scoutinterfacedesign.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.scoutinterfacedesign.Helpers.HelperMessage;
import com.example.scoutinterfacedesign.Models.DataController;
import com.example.scoutinterfacedesign.Models.Staff.User;
import com.example.scoutinterfacedesign.R;

public class Authentication extends AppCompatActivity {

    private DataController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        try {
            // Load data
            DataController.load(this);
        }
        catch (Exception x)
        {
            // Initialize the singleton
            DataController.get();
        }

        controller = DataController.get();

        if(controller.Users.size() == 0)
            controller.Users.add(new User("admin", "admin"));

        $(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String username, password;

                    username = ((EditText) $(R.id.tv_username)).getText().toString();
                    password = ((EditText) $(R.id.tv_password)).getText().toString();

                    if (username.length() == 0 || password.length() == 0)
                        throw new Exception("أدخل جميع المعلومات!");

                    User target = controller.findUser(username);

                    if (target == null)
                        throw new Exception("لا يوجد أي حساب بهذا الاسم!");

                    if (target.password.compareTo(password) != 0)
                        throw new Exception("كلمة المرور ليست صحيحة!");

                    startActivity(new Intent(getApplicationContext(), Home.class));
                } catch (Exception x) {
                    HelperMessage.showAlertMessage(Authentication.this, x.getMessage());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ((EditText) $(R.id.tv_username)).setText("");
        ((EditText) $(R.id.tv_password)).setText("");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            // Save
            DataController.save(this);
        } catch (Exception x)
        {
            HelperMessage.showAlertMessage(this, "Cannot save data!");
        }
    }

    private View $(int id) {
        return findViewById(id);
    }
}
