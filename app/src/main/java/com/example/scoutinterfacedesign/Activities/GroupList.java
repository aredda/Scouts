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
import com.example.scoutinterfacedesign.Models.System.Group;
import com.example.scoutinterfacedesign.R;

import java.util.List;

public class GroupList extends AppCompatActivity {

    private TextView tv_header;
    private ListView groupListView;
    private Button addButton;

    private TriAdapter groupListAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        // Initial setup
        groupListView = findViewById(R.id.lv_groups);
        addButton = findViewById(R.id.btn_add);

        tv_header = findViewById(R.id.tv_header);
        tv_header.setText("لائحة المجموعات");

        // Refresh view
        refresh();
        // Configure add group button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirection to another activity
                Intent i = new Intent(getApplicationContext(), GroupInsert.class);
                // Send
                startActivity(i);
            }
        });
        // Configure list view click
        groupListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Create an intent
                        Intent unitListIntent = new Intent(getApplicationContext(), GroupUnitList.class);
                        // Send group
                        Group g = DataController.get().getArrangements(Group.class).get(position);
                        // Start the other activity
                        unitListIntent.putExtra("group", g.id);
                        startActivity(unitListIntent);
                    }
                }
        );
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Refresh view
        refresh();
    }

    private void refresh()
    {
        // Retrieve group list
        List<Group> groups = DataController.get().getArrangements(Group.class);
        // Associate an adapter
        if(groupListAdapter == null)
        {
            groupListAdapter = new TriAdapter(this, groups, "toString", "getLeader", "unitCount");
            groupListView.setAdapter(groupListAdapter);
        }
        else
            groupListAdapter.setSource(groups);
    }
}
