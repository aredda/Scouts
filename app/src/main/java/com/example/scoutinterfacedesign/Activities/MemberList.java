package com.example.scoutinterfacedesign.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.scoutinterfacedesign.Adapters.FlexAdapter;
import com.example.scoutinterfacedesign.Models.DataController;
import com.example.scoutinterfacedesign.Models.Staff.Adherent;
import com.example.scoutinterfacedesign.Models.Staff.Leader;
import com.example.scoutinterfacedesign.Models.Staff.Member;
import com.example.scoutinterfacedesign.R;

import java.util.ArrayList;
import java.util.Hashtable;

public class MemberList extends AppCompatActivity {

    private EditText et_search;
    private ListView lv_member;

    private void refreshList(ArrayList<Member> list) {
        Hashtable<Integer, String> mapping = new Hashtable<>();
        mapping.put(R.id.tv_primary, "toString");
        mapping.put(R.id.tv_secondary, "getNature");

        lv_member.setAdapter(
                new FlexAdapter(this, list, R.layout.list_dual_item, mapping)
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        TextView header = findViewById(R.id.tv_header);
        header.setText("سجل الانخراط");

        lv_member = findViewById(R.id.lv_members);
        et_search = findViewById(R.id.tv_search);

        refreshList(DataController.get().getStaff());

        lv_member.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the selected member
                Member target = DataController.get().getStaff().get(position);
                // Start new activity
                Intent i = null;

                if (target instanceof Adherent) {
                    i = new Intent(getApplicationContext(), MemberDetails.class);
                    i.putExtra("member", target.id);
                } else if (target instanceof Leader) {
                    LeaderList.selectedLeader = (Leader) target;
                    i = new Intent(getApplicationContext(), LeaderDetails.class);
                }

                if (i != null)
                    startActivity(i);
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Get all members
                ArrayList<Member> all = DataController.get().getStaff();
                // Search by lastName
                if (count > 0) {
                    ArrayList<Member> filter = new ArrayList<>();

                    for (Member m : all)
                        if (m.lastName.toLowerCase().contains(s.toString().toLowerCase()))
                            filter.add(m);

                    refreshList(filter);
                }
                else
                    refreshList(all);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
}
