package com.example.scoutinterfacedesign.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scoutinterfacedesign.Adapters.FlexAdapter;
import com.example.scoutinterfacedesign.Adapters.TriAdapter;
import com.example.scoutinterfacedesign.Helpers.HelperMessage;
import com.example.scoutinterfacedesign.Models.DataController;
import com.example.scoutinterfacedesign.Models.Norms.EMission;
import com.example.scoutinterfacedesign.Models.Norms.EUnitType;
import com.example.scoutinterfacedesign.Models.Staff.Leader;
import com.example.scoutinterfacedesign.Models.System.Group;
import com.example.scoutinterfacedesign.Models.System.Mission;
import com.example.scoutinterfacedesign.Models.System.Unit;
import com.example.scoutinterfacedesign.R;

import java.util.Hashtable;
import java.util.List;

public class UnitInsert extends AppCompatActivity {

    private DataController controller;

    private TextView tv_header;
    private Spinner spn_unitLeader, spn_leader, spn_mission;
    private Button btn_appoint, btn_add;
    private ListView lv_direction;
    private EditText et_name;

    private ArrayAdapter<EMission> spn_mission_adapter;
    private TriAdapter lv_direction_adapter;

    private Group group;
    private Unit unit;
    private EUnitType unitType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_insert);

        // Retrieve controller
        controller = DataController.get();
        // Initialize unit
        group = controller.findGroup( getIntent().getIntExtra("group", -1) );
        unit = new Unit(controller.getArrangements(Unit.class).size() + 1);
        unit.type = EUnitType.getValue( getIntent().getStringExtra("unitType") );
        // Retrieve views
        findViews();
        // Title configuration
        tv_header.setText( "اضافة " + unit.type);
        // Set up adapters
        spn_mission_adapter = loadSpinner(spn_mission, controller.getEnumList(EMission.class));
        loadSpinner(spn_unitLeader, controller.getFreeLeaders());
        loadSpinner(spn_leader, controller.getFreeLeaders());
        // Configure spinner click
        spn_unitLeader.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Leader leader = (Leader) spn_unitLeader.getSelectedItem();

                    if (unit.getMemberMission(leader) != null)
                        throw new Exception("لا يمكن تعيين أكثر من مهمة لقائد واحد!");

                    group.removeMission(EMission.M1);
                    group.addMission(leader, EMission.M1);

                    loadList(lv_direction, group.direction);
                }
                catch (Exception x)
                {
                    Toast.makeText(getApplicationContext(), x.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Configure button click events
        btn_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    // Get selected
                    Leader leader = (Leader) spn_leader.getSelectedItem();
                    EMission mission = (EMission) spn_mission.getSelectedItem();

                    if(unit.getMemberMission(leader) != null)
                        throw new Exception("لا يمكن تعيين أكثر من مهمة لقائد واحد!");

                    Mission m = unit.addMission(leader, mission);

                    if( m == null )
                        throw new Exception("لا يمكن تعيين المهمة!");

                    leader.missions.add(m);
                    leader.currentMission = m;

                    loadList(lv_direction, unit.direction);

                    spn_mission_adapter.clear();
                    spn_mission_adapter.addAll(unit.getAvailableMissions());

                    HelperMessage.showToastMessage(getApplicationContext(),  "تم تعيين المهمة!");
                }
                catch (Exception x)
                {
                    HelperMessage.showToastMessage(getApplicationContext(), x.getMessage());
                }
            }
        });
        btn_add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try
                        {
                            if(et_name.length() == 0)
                                throw new Exception("اسم الوحدة ضروري!");

                            unit.name = et_name.getText().toString();

                            if(unit.getLeader() == null)
                                throw new Exception("يجب تعيين القائد لهاته الوحدة!");

                            Leader l = unit.getLeader();
                            Mission m = unit.getMemberMission(l);

                            l.missions.add(m);
                            l.currentMission = m;

                            unit.setGroup(group);

                            controller.Arrangements.add(unit);

                            // Save changes
                            controller.save(UnitInsert.this);
                            // Return to previous activity
                            finish();
                        }
                        catch (Exception x)
                        {
                            HelperMessage.showToastMessage(getApplicationContext(), x.getMessage());
                        }
                    }
                }
        );
    }

    private void findViews()
    {
        tv_header = findViewById(R.id.tv_header);

        spn_unitLeader = findViewById(R.id.spn_unitLeader);
        spn_leader = findViewById(R.id.spn_leader);
        spn_mission = findViewById(R.id.spn_mission);

        btn_add = findViewById(R.id.btn_add);
        btn_appoint = findViewById(R.id.btn_appoint);

        lv_direction = findViewById(R.id.lv_crew);

        et_name = findViewById(R.id.et_name);
    }

    private ArrayAdapter loadSpinner(Spinner spn, List source)
    {
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_list_item, R.id.tv_spnItemTv, source);
        spn.setAdapter(adapter);

        return adapter;
    }
    
    private FlexAdapter loadList(ListView lv, List source)
    {
        Hashtable<Integer, String> mapping = new Hashtable<>();
        mapping.put(R.id.tv_primary, "getMember");
        mapping.put(R.id.tv_secondary, "getMission");

        FlexAdapter adapter = new FlexAdapter(this, source, R.layout.list_dual_item, mapping);
        lv.setAdapter(adapter);

        return adapter;
    }
}
