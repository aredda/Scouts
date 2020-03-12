package com.example.scoutinterfacedesign.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.scoutinterfacedesign.Adapters.FlexAdapter;
import com.example.scoutinterfacedesign.Helpers.HelperMessage;
import com.example.scoutinterfacedesign.Helpers.HelperResource;
import com.example.scoutinterfacedesign.Models.Staff.Leader;
import com.example.scoutinterfacedesign.R;

import java.util.Hashtable;

public class LeaderDetails extends AppCompatActivity {

    private Leader target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_details);

        target = LeaderList.selectedLeader;


        if (!HelperResource.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE))
            HelperResource.requestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, HelperResource.READ_EXTERNAL_STORAGE_REQUEST_CODE);
    }

    private <T extends View> T $(Class<T> viewClass, int resourceId) {
        return viewClass.cast(this.findViewById(resourceId));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == HelperResource.READ_EXTERNAL_STORAGE_REQUEST_CODE
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            fillForm();
        }
    }

    private void fillForm() {
        try {
            $(ImageView.class, R.id.img_photo).setImageBitmap(HelperResource.compressBitmap(getContentResolver(), target.image));
            $(TextView.class, R.id.tv_fullName).setText(target.toString());
            $(TextView.class, R.id.tv_nationality).setText(target.nationality);
            $(TextView.class, R.id.tv_birth).setText(HelperMessage.formatDate(target.birthDate));
            $(TextView.class, R.id.tv_enrollment).setText(HelperMessage.formatDate(target.enrollmentDate));
            $(TextView.class, R.id.tv_unit).setText(target.currentMission != null ? target.currentMission.toString() : "لا يوجد");
            $(TextView.class, R.id.tv_mobile).setText(target.mobile);
            $(Button.class, R.id.btn_showBadges).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout main = $(LinearLayout.class, R.id.lyt_main);
                    ListView secondary = $(ListView.class, R.id.lv_badges);

                    if (main.getVisibility() == View.GONE) {
                        main.setVisibility(View.VISIBLE);
                        secondary.setVisibility(View.GONE);

                        $(Button.class, R.id.btn_showBadges).setText("رؤية لائحة التداريب");
                    } else {
                        main.setVisibility(View.GONE);
                        secondary.setVisibility(View.VISIBLE);

                        $(Button.class, R.id.btn_showBadges).setText("اخفاء لائحة التداريب");
                    }
                }
            });

            Hashtable<Integer, String> mapConfig = new Hashtable<>();
            mapConfig.put(R.id.tv_main, "getBadge");

            $(ListView.class, R.id.lv_badges).setAdapter(
                    new FlexAdapter(this, target.badges, R.layout.list_single_item, mapConfig)
            );
        } catch (Exception x)
        {
            AlertDialog.Builder box = new AlertDialog.Builder(LeaderDetails.this);
            box.setTitle("Warning!");
            box.setMessage(x.getMessage());
            box.show();
        }
    }
}
