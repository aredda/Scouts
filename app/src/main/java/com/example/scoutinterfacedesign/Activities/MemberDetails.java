package com.example.scoutinterfacedesign.Activities;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.provider.MediaStore;
import android.support.constraint.solver.widgets.Helper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scoutinterfacedesign.Helpers.HelperMessage;
import com.example.scoutinterfacedesign.Helpers.HelperResource;
import com.example.scoutinterfacedesign.Models.DataController;
import com.example.scoutinterfacedesign.Models.Staff.Adherent;
import com.example.scoutinterfacedesign.Models.Staff.Member;
import com.example.scoutinterfacedesign.R;

import java.text.DateFormat;
import java.util.Date;

public class MemberDetails extends AppCompatActivity {

    private DataController controller;

    private Adherent target;

    private ImageView img_photo;
    private TextView tv_fullName;
    private TextView tv_nationality;
    private TextView tv_birth;
    private TextView tv_enrollment;
    private TextView tv_unit;
    private TextView tv_address;
    private TextView tv_father, tv_mother;
    private TextView tv_title;

    private HelperResource.ColorTheme theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        try {
            // Model processing
            controller = DataController.get();
            target = (Adherent) controller.findPerson(Adherent.class, (int) getIntent().getExtras().get("member"));

            theme = HelperResource.getUnitTheme(target.currentState.unit.type);

            // Retrieve views
            findViews();
            // Distribute data
            distribute();
        } catch (Exception x) {
            HelperMessage.showAlertMessage(this, x.getMessage());
        }
    }

    private void findViews() {
        img_photo = findViewById(R.id.img_photo);

        tv_fullName = findViewById(R.id.tv_fullName);
        tv_nationality = findViewById(R.id.tv_nationality);
        tv_birth = findViewById(R.id.tv_birth);
        tv_enrollment = findViewById(R.id.tv_enrollment);
        tv_unit = findViewById(R.id.tv_unit);
        tv_address = findViewById(R.id.tv_address);
        tv_father = findViewById(R.id.tv_father);
        tv_mother = findViewById(R.id.tv_mother);
        tv_title = findViewById(R.id.tv_title);
    }

    private void distribute() throws Exception {
        img_photo.setImageBitmap( HelperResource.compressBitmap(getContentResolver(), target.image));

        tv_fullName.setText(target.firstName + " " + target.lastName);
        tv_nationality.setText(target.nationality);
        tv_birth.setText(HelperMessage.formatDate(target.birthDate) + ", " + target.birthPlace);
        tv_enrollment.setText(HelperMessage.formatDate(target.enrollmentDate));
        tv_address.setText(target.address);
        tv_father.setText(target.fatherName + " - " + target.fatherMobile);
        tv_mother.setText(target.motherName + " - " + target.motherMobile);
        tv_title.setBackgroundColor(getResources().getColor(theme.colorMain));
        tv_title.setTextColor(getResources().getColor(theme.colorSecondary));
        tv_title.setText(controller.getTitle(target.currentState.unit.type, target.gender));
        tv_unit.setText(target.currentState.unit.toString());
    }
}
