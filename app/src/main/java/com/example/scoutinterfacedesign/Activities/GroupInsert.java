package com.example.scoutinterfacedesign.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.scoutinterfacedesign.Adapters.FlexAdapter;
import com.example.scoutinterfacedesign.Adapters.TriAdapter;
import com.example.scoutinterfacedesign.Helpers.HelperMessage;
import com.example.scoutinterfacedesign.Helpers.HelperReflection;
import com.example.scoutinterfacedesign.Models.DataController;
import com.example.scoutinterfacedesign.Models.Norms.EMission;
import com.example.scoutinterfacedesign.Models.Staff.Leader;
import com.example.scoutinterfacedesign.Models.System.Group;
import com.example.scoutinterfacedesign.Models.System.Mission;
import com.example.scoutinterfacedesign.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class GroupInsert extends AppCompatActivity {

    private DataController controller;
    private Group group;

    private EditText et_name;
    private Spinner spn_leader, spn_mission, spn_groupLeader;
    private ListView lv_crew;
    private Button btn_appoint, btn_submit;

    private ArrayAdapter spn_mission_adapter;

    private ArrayAdapter configureSpinner(Spinner spn, List source) {
        // Create adapter
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.spinner_list_item, R.id.tv_spnItemTv, source);
        // Set adapter
        spn.setAdapter(adapter);
        // Return
        return adapter;
    }

    private Adapter configureListView(ListView lv, List source) {
        Hashtable<Integer, String> mapping = new Hashtable<>();
        mapping.put(R.id.tv_primary, "getMember");
        mapping.put(R.id.tv_secondary, "getMission");

        FlexAdapter adapter = new FlexAdapter(this, source, R.layout.list_dual_item, mapping);
        lv.setAdapter(adapter);

        return adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_insert);

        controller = DataController.get();

        // Prepare an empty group model
        group = new Group(controller.getArrangements(Group.class).size() + 1);

        // Initial Setup
        et_name = findViewById(R.id.et_name);
        spn_groupLeader = findViewById(R.id.spn_groupLeader);
        spn_leader = findViewById(R.id.spn_leader);
        spn_mission = findViewById(R.id.spn_mission);
        lv_crew = findViewById(R.id.lv_crew);
        btn_appoint = findViewById(R.id.btn_appoint);
        btn_submit = findViewById(R.id.btn_add);

        // Initial data loading
        configureSpinner(spn_groupLeader, controller.getFreeLeaders());
        configureSpinner(spn_leader, controller.getFreeLeaders());
        spn_mission_adapter = configureSpinner(
                spn_mission,
                HelperReflection.<EMission>Except((ArrayList<EMission>) controller.getEnumList(EMission.class), EMission.M1)
        );

        // Configure spinner events
        spn_groupLeader.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Leader leader = (Leader) spn_groupLeader.getSelectedItem();

                    if (group.getMemberMission(leader) != null)
                        throw new Exception("لا يمكن تعيين أكثر من مهمة لقائد واحد!");

                    Mission m = new Mission(group, leader, EMission.M1, new Date());

                    group.removeMission(EMission.M1);
                    group.direction.add(m);

                    configureListView(lv_crew, group.direction);
                } catch (Exception x) {
                    Toast.makeText(getApplicationContext(), x.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Configure button events
        btn_appoint.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Leader selectedLeader = (Leader) spn_leader.getSelectedItem();
                            EMission selectedMission = (EMission) spn_mission.getSelectedItem();

                            if (group.getMemberMission(selectedLeader) != null)
                                throw new Exception("لا يمكن تعيين أكثر من مهمة لقائد واحد!");

                            Mission m = group.addMission(selectedLeader, selectedMission);

                            if (m == null)
                                throw new Exception("لا يمكن تعيين المهمة لهذا القائد!");

                            selectedLeader.missions.add(m);
                            selectedLeader.currentMission = m;

                            configureListView(lv_crew, group.direction);

                            spn_mission_adapter.clear();
                            spn_mission_adapter.addAll(HelperReflection.Except(group.getAvailableMissions(), EMission.M1));

                            Toast.makeText(getApplicationContext(), "تم تعيين ال" + selectedMission.toString() + "!", Toast.LENGTH_SHORT).show();
                        } catch (Exception x) {
                            Toast.makeText(getApplicationContext(), x.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verification
                try {
                    if (et_name.getText().length() == 0)
                        throw new Exception("اسم المجموعة أساسي!");

                    // Assign model's name
                    group.name = et_name.getText().toString();

                    if (group.getLeader() == null)
                        throw new Exception("لم يتم تعيين قائد للمجموعة!");

                    group.getLeader().currentMission = group.getMemberMission(group.getLeader());
                    group.getLeader().missions.add(group.getMemberMission(group.getLeader()));

                    controller.Arrangements.add(group);

                    // Close insertion activity
                    finish();
                } catch (Exception x) {
                    HelperMessage.showToastMessage(getApplicationContext(), x.getMessage());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            // Save
            DataController.save(this);
        } catch (Exception x)
        {
            HelperMessage.showAlertMessage(this, "تعذر حفظ المعلومات");
        }
    }
}
