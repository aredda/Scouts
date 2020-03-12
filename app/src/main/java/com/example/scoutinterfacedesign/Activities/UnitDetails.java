package com.example.scoutinterfacedesign.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.scoutinterfacedesign.Adapters.TriAdapter;
import com.example.scoutinterfacedesign.Helpers.HelperResource;
import com.example.scoutinterfacedesign.Models.DataController;
import com.example.scoutinterfacedesign.Models.System.Enrollment;
import com.example.scoutinterfacedesign.Models.System.Unit;
import com.example.scoutinterfacedesign.R;

import java.util.List;

public class UnitDetails extends AppCompatActivity {

    private DataController controller;

    private Unit unit;

    private HelperResource.ColorTheme theme;
    private RelativeLayout rl_root;

    private TextView tv_appHeader;
    private LinearLayout ll_subHeader;

    private ListView lv_member;

    private Button btn_new;

    private RadioButton rb_members;
    private RadioButton rb_leaders;

    private LayoutInflater inflater;

    private TriAdapter lv_member_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_details);

        // Retrieve models
        controller = DataController.get();
        unit = controller.findUnit(getIntent().getIntExtra("unit", -1));
        // Retrieve theme
        theme = HelperResource.getUnitTheme(unit.type);
        // Retrieve views
        findViews();
        // Configure sub header view
        configureSubHeader();
        // Configure radio buttons
        rb_members.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                reload();
            }
        });
        rb_leaders.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                reload();
            }
        });
        // Configure add new member
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MemberInsert.class);
                i.putExtra("unit", unit.id);

                startActivity(i);
            }
        });
        // Configure list view item click
        lv_member.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!rb_members.isChecked())
                    return;

                Enrollment m = unit.enrollments.get(position);

                Intent memberDetails = new Intent(getApplicationContext(), MemberDetails.class);
                memberDetails.putExtra("member", m.member.id);

                startActivity(memberDetails);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        rb_members.setChecked(true);

        reload();
    }

    private void findViews() {
        rl_root = findViewById(R.id.root);

        tv_appHeader = findViewById(R.id.tv_header);
        tv_appHeader.setText(unit.group.toString());

        ll_subHeader = findViewById(R.id.tv_subHead);
        lv_member = findViewById(R.id.lv_members);

        rb_members = findViewById(R.id.rb_members);
        rb_leaders = findViewById(R.id.rb_leaders);

        btn_new = findViewById(R.id.btn_add);

        inflater = LayoutInflater.from(this);
    }

    private void configureSubHeader() {
        ImageView img = ll_subHeader.findViewById(R.id.img_logo);
        TextView tv = ll_subHeader.findViewById(R.id.tv_header);

        ll_subHeader.setBackgroundColor(getResources().getColor(theme.colorMain));

        tv.setTextColor(getResources().getColor(theme.colorSecondary));
        tv.setText(unit.toString());

        img.setImageResource(HelperResource.getUnitLogo(unit.type));

        RadioGroup group = (RadioGroup) rb_members.getParent();
        group.setBackgroundColor(getResources().getColor(theme.colorSecondary));

        rb_members.setTextColor(getResources().getColor(theme.colorMain));
        rb_leaders.setTextColor(getResources().getColor(theme.colorMain));
    }

    private void loadList(List dataSource, String header, String subHeader, String extra) {
        lv_member_adapter = new TriAdapter(this, dataSource, header, subHeader, extra, theme);
        lv_member.setAdapter(lv_member_adapter);
    }

    private void reload() {
        if (rb_members.isChecked())
            loadList(unit.enrollments, "getMember", "getMemberTitle", "getDate");
        else
            loadList(unit.direction, "getMember", "getMission", "getDate");
    }
}
