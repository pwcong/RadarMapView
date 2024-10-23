# RadarMapView [![](https://jitpack.io/v/pwcong/RadarMapView.svg)](https://jitpack.io/#pwcong/RadarMapView)
A simple view to display datas by Radar Map .

![SnapShot01](https://github.com/pwcong/SnapShot/blob/master/RadarMapView/snapshot1.gif)

![SnapShot02](https://github.com/pwcong/SnapShot/blob/master/RadarMapView/snapshot2.gif)

***************

# How To Install
see https://jitpack.io/#pwcong/RadarMapView

# Usage

Add it in your layout.

```
<me.pwcong.radarmapview.view.RadarMapView
    android:id="@+id/radarMapView"
    android:layout_width="400dp"
    android:layout_height="400dp"
    app:text_color="@color/colorPrimaryDark"
    app:fill_color="#cc008891"
    app:line_color="#666"
    app:text_size="20sp"
    app:division="5"
    app:stroke_width="1"
    android:layout_centerVertical="true"
    android:layout_centerHorizontal="true" />

```

Here is the list of attributes:

```
<declare-styleable name="RadarMapView">
    <attr name="division" format="integer" />
    <attr name="text_size" format="dimension" />
    <attr name="text_color" format="color" />
    <attr name="line_color" format="color" />
    <attr name="fill_color" format="color" />
    <attr name="stroke_width" format="float" />
</declare-styleable>
```

And then add some datas.

```
    ...
    
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
```

****************
