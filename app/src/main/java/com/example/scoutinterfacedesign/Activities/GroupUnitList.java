package com.example.scoutinterfacedesign.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.scoutinterfacedesign.Adapters.TriAdapter;
import com.example.scoutinterfacedesign.Models.DataController;
import com.example.scoutinterfacedesign.Models.Norms.EUnitType;
import com.example.scoutinterfacedesign.Models.System.Group;
import com.example.scoutinterfacedesign.Models.System.Unit;
import com.example.scoutinterfacedesign.R;

import java.util.ArrayList;
import java.util.List;

public class GroupUnitList extends AppCompatActivity {

    private DataController controller;
    private Group group;

    // Active information
    private List<Unit> currentUnits;
    private EUnitType activeUnitType;

    private LinearLayout lyt_tabLinkContainer;
    private ListView lv_units;
    private Button btn_addUnit;

    private ArrayList<TextView> tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_unit_list);

        controller = DataController.get();

        // Retrieve views
        lyt_tabLinkContainer = findViewById(R.id.lyt_tabLinks);
        lv_units = findViewById(R.id.lv_units);
        btn_addUnit = findViewById(R.id.btn_add);

        // Retrieve group
        group = controller.findGroup ((int) getIntent().getExtras().get("group"));

        // Create unit tabs
        createTabLinks();

        // Configure unit click event
        lv_units.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Retrieve selected unit
                        Unit u = currentUnits.get(position);
                        // Create intent
                        Intent unitIntent = new Intent(getApplicationContext(), UnitDetails.class);
                        unitIntent.putExtra("unit", u.id);
                        // Start
                        startActivity(unitIntent);
                    }
                }
        );

        // Add a new unit
        btn_addUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new activity
                Intent addUnitIntent = new Intent(getApplicationContext(), UnitInsert.class);
                addUnitIntent.putExtra("group", group.id);
                addUnitIntent.putExtra("unitType", activeUnitType.toString());

                startActivity(addUnitIntent);
            }
        });

        // Show the first unit type
        this.tabs.get(0).performClick();
    }

    private void createTabLinks()
    {
        // Initialize
        tabs = new ArrayList<>();
        // Retrieve inflater
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

        for(final EUnitType t : EUnitType.values())
        {
            // Instantiate a tab link
            TextView tabLinkItem = (TextView) inflater.inflate(R.layout.tab_link_item, lyt_tabLinkContainer, false);
            // Change that tab name
            tabLinkItem.setText(t.toString());
            // Configure tab click
            tabLinkItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Change adapter source
                    loadUnits(t);
                    // Change the active
                    activeUnitType = t;
                    // Activate tab
                    activateTab((TextView) v);
                    // Change button text
                    btn_addUnit.setText( "اضافة " + t.toString());
                }
            });
            // Add view to the parent
            lyt_tabLinkContainer.addView(tabLinkItem);
            // Store the tab
            tabs.add(tabLinkItem);
        }
    }

    private void loadUnits(EUnitType unitType)
    {
        // Retrieve the list of the units
        currentUnits = group.getUnits(unitType);
        // Update the adapter
        lv_units.setAdapter(new TriAdapter(this, currentUnits, "toString", "getLeader", "memberCount"));
    }

    private void activateTab(TextView tab)
    {
        for (View t : tabs)
            t.setBackgroundColor( getResources().getColor(R.color.colorMainDark) );

        tab.setBackgroundColor(getResources().getColor(R.color.colorMain));
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        this.tabs.get(0).performClick();
    }
}
