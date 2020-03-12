package com.example.scoutinterfacedesign.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.scoutinterfacedesign.Adapters.TriAdapter;
import com.example.scoutinterfacedesign.Models.DataController;
import com.example.scoutinterfacedesign.Models.Staff.Leader;
import com.example.scoutinterfacedesign.R;

public class LeaderList extends AppCompatActivity {

    public static Leader selectedLeader;

    private DataController controller;

    private TextView header;
    private ListView leaderListView;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_list);

        // Get Controller
        controller = DataController.get();
        // Retrieve views
        leaderListView = findViewById(R.id.lv_leaders);
        addButton = findViewById(R.id.btn_add);
        header = findViewById(R.id.tv_header);
        header.setText("لائحة القادة");
        // Load leaders
        loadLeaders();
        // Configure add button
        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), LeaderInsert.class));
                    }
                }
        );
        // Configure list view
        leaderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get clicked leader
                selectedLeader = controller.getStaff(Leader.class).get(position);
                // Create an intent
                startActivity(new Intent(getApplicationContext(), LeaderDetails.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        loadLeaders();
    }

    private void loadLeaders()
    {
        leaderListView.setAdapter(
                new TriAdapter(getApplicationContext(),
                        controller.getStaff(Leader.class),
                        "toString",
                        "getCurrentMission",
                        null)
        );
    }
}
