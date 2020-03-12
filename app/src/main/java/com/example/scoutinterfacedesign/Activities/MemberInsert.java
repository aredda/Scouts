package com.example.scoutinterfacedesign.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.scoutinterfacedesign.Helpers.HelperMessage;
import com.example.scoutinterfacedesign.Models.DataController;
import com.example.scoutinterfacedesign.Models.Norms.EGender;
import com.example.scoutinterfacedesign.Models.Staff.Adherent;
import com.example.scoutinterfacedesign.Models.Staff.Member;
import com.example.scoutinterfacedesign.Models.System.Enrollment;
import com.example.scoutinterfacedesign.Models.System.Unit;
import com.example.scoutinterfacedesign.R;

import java.util.Calendar;

public class MemberInsert extends AppCompatActivity {

    private DataController controller;
    private Unit unit;
    private Adherent member;

    private TextView tv_appHeader;
    private EditText et_code,
            et_fname, et_lname,
            et_birthplace, et_nationality, et_address,
            et_fatherName, et_motherName,
            et_fatherMobile, et_motherMobile;
    private DatePicker dp_birthdate, dp_enrollment;
    private TextView tv_title;
    private Spinner spn_gender;

    private Button btn_add, btn_pick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_insert);

        controller = DataController.get();
        unit = controller.findUnit( getIntent().getIntExtra("unit", -1) );
        member = new Adherent( controller.getStaff(Member.class).size() + 1 );

        // retrieve views
        findViews();

        // configure gender spinner
        spn_gender.setAdapter(
                new ArrayAdapter<EGender>(getApplicationContext(), R.layout.spinner_list_item, R.id.tv_spnItemTv, controller.getEnumList(EGender.class))
        );
        spn_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve selected gender
                EGender gender = controller.getEnumList(EGender.class).get(position);
                // Update the member gender
                member.gender = gender;
                // Show the title
                tv_title.setText( controller.getTitle(unit.type, gender));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tv_title.setText("");
            }
        });

        // Configure picking image button actionb
        btn_pick.setOnClickListener(new View.OnClickListener() {
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

        // Configure finish and register button
        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try
                {
                    member.code = validateField(et_code, "رقم الانخراط");
                    member.firstName = validateField(et_fname,"الاسم الشخصي");
                    member.lastName = validateField(et_lname,"الاسم العائلي");
                    member.birthPlace = validateField(et_birthplace, "مكان الازدياد");
                    member.address = validateField(et_address, "العنوان");
                    member.fatherName = validateField(et_fatherName, "اسم الأب");
                    member.fatherMobile = validateField(et_fatherMobile, "هاتف الأب");
                    member.motherName = validateField(et_motherName, "اسم الأم");
                    member.motherMobile = validateField(et_motherMobile, "هاتف الأم");
                    member.nationality = validateField(et_nationality, "الجنسية");

                    Calendar datePickerFormatter = Calendar.getInstance();

                    datePickerFormatter.set(dp_birthdate.getYear(), dp_birthdate.getMonth(), dp_birthdate.getDayOfMonth());
                    member.birthDate = datePickerFormatter.getTime();

                    datePickerFormatter.set(dp_enrollment.getYear(), dp_enrollment.getMonth(), dp_enrollment.getDayOfMonth());
                    member.enrollmentDate = datePickerFormatter.getTime();

                    if( member.image == null )
                        throw new Exception("اختر صورة لتعريف الفرد!");

                    Enrollment enrollment = new Enrollment(unit, member, member.enrollmentDate);

                    // Relational configuration
                    unit.enrollments.add(enrollment);
                    member.currentState = enrollment;
                    // Add model to its respective container
                    controller.Staff.add(member);
                    // Save changes
                    controller.save (MemberInsert.this);

                    finish();
                }
                catch (Exception x)
                {
                    HelperMessage.showToastMessage(getApplicationContext(), x.getMessage());
                }
            }
        });
    }

    private void findViews()
    {
        tv_appHeader = findViewById(R.id.tv_header);
        tv_appHeader.setText("اضافة فرد جديد");

        et_code = findViewById(R.id.et_code);
        et_fname = findViewById(R.id.et_firstName);
        et_lname = findViewById(R.id.et_lastName);
        et_birthplace = findViewById(R.id.et_birthPlace);
        et_nationality = findViewById(R.id.et_nationality);
        et_address = findViewById(R.id.et_address);
        et_fatherName = findViewById(R.id.et_fatherName);
        et_motherName = findViewById(R.id.et_motherName);
        et_fatherMobile = findViewById(R.id.et_fatherMobile);
        et_motherMobile = findViewById(R.id.et_motherMobile);

        dp_birthdate = findViewById(R.id.dp_birthDate);
        dp_enrollment = findViewById(R.id.dp_enrollment);

        spn_gender = findViewById(R.id.spn_gender);

        tv_title = findViewById(R.id.tv_title);

        btn_add = findViewById(R.id.btn_add);
        btn_pick = findViewById(R.id.btn_pick);
    }

    private String validateField(EditText editText, String fieldName) throws Exception
    {
        if(editText.length() == 0)
            throw new Exception("يجب تحديد " + fieldName + "!");

        return editText.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == 1)
            if(data != null)
                member.image = data.getData();
    }
}
