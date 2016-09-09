package me.pwcong.radarmapviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import me.pwcong.radarmapview.model.RadarMapEntry;
import me.pwcong.radarmapview.view.RadarMapView;

public class MainActivity extends AppCompatActivity {

    RadarMapView radarMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radarMapView= (RadarMapView) findViewById(R.id.radarMapView);
        radarMapView.setData(getList());

    }

    private ArrayList<RadarMapEntry> getList(){

        ArrayList<RadarMapEntry> entries=new ArrayList<>();

        entries.add(new RadarMapEntry("小彭",26));
        entries.add(new RadarMapEntry("小花",15));
        entries.add(new RadarMapEntry("小黑",23));
        entries.add(new RadarMapEntry("小红",18));
        entries.add(new RadarMapEntry("小黄",33));
        entries.add(new RadarMapEntry("小可",43));

        return entries;

    }

}
