# RadarMapView [![](https://jitpack.io/v/pwcong/RadarMapView.svg)](https://jitpack.io/#pwcong/RadarMapView)
A simple view to display datas by Radar Map .

![SnapShot01](https://github.com/pwcong/SnapShot/blob/master/RadarMapView/snapshot1.gif)

![SnapShot02](https://github.com/pwcong/SnapShot/blob/master/RadarMapView/snapshot2.gif)

***************

# How To

To get a Git project into your build:

## Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

## Step 2. Add the dependency

```
	dependencies {
	        compile 'com.github.pwcong:RadarMapView:v1.0.0'
	}
```

***************

# Usage

Add it in your layout.

```
    <me.pwcong.radarmapview.view.RadarMapView
        android:id="@+id/radarMapView"
        android:layout_width="400dp"
        android:layout_height="400dp"
        app:textColor="@color/colorPrimaryDark"
        app:fillColor="#cc008891"
        app:lineColor="#666"
        app:textSize="20sp"
        app:division="5"
        app:strokeWidth="1"
        android:layout_centerVertical="true"
        android:layout_centerHorizontal="true" />

```

Here is the list of attributes:

```

        <attr name="division" format="integer"/>
        <attr name="textSize" format="dimension"/>
        <attr name="textColor" format="color"/>
        <attr name="lineColor" format="color"/>
        <attr name="fillColor" format="color"/>
        <attr name="strokeWidth" format="float"/>

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

# Recently

## v1.0.0
简单实现雷达图。


