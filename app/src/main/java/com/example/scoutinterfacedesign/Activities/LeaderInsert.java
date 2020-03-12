package com.example.scoutinterfacedesign.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.scoutinterfacedesign.Helpers.HelperMessage;
import com.example.scoutinterfacedesign.Models.DataController;
import com.example.scoutinterfacedesign.Models.Norms.ECourse;
import com.example.scoutinterfacedesign.Models.Norms.EGender;
import com.example.scoutinterfacedesign.Models.Staff.Leader;
import com.example.scoutinterfacedesign.Models.System.Badge;
import com.example.scoutinterfacedesign.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LeaderInsert extends AppCompatActivity {

    private DataController controller;
    private Leader leader;

    private ArrayList<CheckBox> courseCheckBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_insert);

        // Retrieve controller
        controller = DataController.get();
        // Prepare view model
        leader = new Leader(controller.Staff.size() + 1);
        // Change activity's header
        ((TextView) $(R.id.tv_header)).setText("اضافة قائد جديد");
        // Show the list of badges
        showBadges();
        // Configure gender spinner
        ((Spinner) $(R.id.spn_gender)).setAdapter(
                new ArrayAdapter<EGender>(this, R.layout.spinner_list_item, R.id.tv_spnItemTv,  controller.getEnumList(EGender.class))
        );
        // Configure add button
        $(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    leader.code = validateField((EditText) $(R.id.et_code), "رقم الانخراط");
                    leader.firstName = validateField((EditText) $(R.id.et_firstName), "الاسم الشخصي");
                    leader.lastName = validateField((EditText) $(R.id.et_lastName), "الاسم العائلي");
                    leader.nationality = validateField((EditText) $(R.id.et_nationality), "الجنسية");
                    leader.birthPlace = validateField((EditText) $(R.id.et_birthPlace), "مكان الازدياد");
                    leader.mobile = validateField( (EditText) $(R.id.et_mobile), "رقم الهاتف" );
                    leader.gender = (EGender) ((Spinner) $(R.id.spn_gender)).getSelectedItem();

                    DatePicker dp_birthdate = $(R.id.dp_birthDate);
                    DatePicker dp_enrollment = $(R.id.dp_enrollment);

                    Calendar dateGenerator = Calendar.getInstance();

                    dateGenerator.set(dp_birthdate.getYear(), dp_birthdate.getMonth(), dp_birthdate.getDayOfMonth());
                    leader.birthDate = dateGenerator.getTime();

                    dateGenerator.set(dp_enrollment.getYear(), dp_enrollment.getMonth(), dp_enrollment.getDayOfMonth());
                    leader.enrollmentDate = dateGenerator.getTime();

                    if (leader.image == null)
                        throw new Exception("يجب اختيار صورة القائد!");

                    for (int i = 0; i < courseCheckBoxes.size(); i++)
                        if (courseCheckBoxes.get(i).isChecked())
                            leader.badges.add(new Badge(leader, controller.getEnumList(ECourse.class).get(i)));

                    //  َAdd leader to people's list
                    controller.Staff.add(leader);

                    // Save data
                    controller.save(LeaderInsert.this);

                    finish();
                }
                catch (Exception x) {
                    HelperMessage.showAlertMessage(LeaderInsert.this, x.getMessage());
                }
            }
        });
        // Configure image chooser
        $(R.id.btn_pick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Configure a pick action
                Intent pickImage = new Intent(Intent.ACTION_PICK);

                String[] types = {"image/jpeg"};
                pickImage.setType("image/*");
                pickImage.putExtra(Intent.EXTRA_MIME_TYPES, types);

                startActivityForResult(pickImage, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == 1)
            if(data != null)
                leader.image = data.getData();
    }

    private <T extends View> T $(int id) {
        return findViewById(id);
    }

    private void showBadges() {
        LinearLayout body = $(R.id.lyt_body);
        List<ECourse> courses = controller.getEnumList(ECourse.class);

        courseCheckBoxes = new ArrayList<>();
        for (ECourse c : courses) {
            // Create a checkbox
            CheckBox courseCheckBox = new CheckBox(getApplicationContext());
            courseCheckBox.setText(c.toString());
            courseCheckBox.setTextSize(getResources().getDimension(R.dimen.h4));
            courseCheckBox.setTextColor(getResources().getColor(R.color.colorMain));

            int padding = (int) getResources().getDimension(R.dimen.m4);
            courseCheckBox.setPadding(padding, padding, padding, padding);

            // Configure checkbox
            body.addView(courseCheckBox);
            courseCheckBox.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
            courseCheckBox.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;

            // Save checkbox
            courseCheckBoxes.add(courseCheckBox);
        }
    }

    private String validateField(EditText editText, String fieldName) throws Exception {
        if (editText.length() == 0)
            throw new Exception("يجب تحديد " + fieldName + "!");

        return editText.getText().toString();
    }
}
