package me.pwcong.radarmapviewdemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import me.pwcong.radarmapview.entry.RadarMapEntry
import me.pwcong.radarmapview.view.RadarMapView

class MainActivity : AppCompatActivity() {
    var radarMapView: RadarMapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radarMapView = findViewById<View>(R.id.radarMapView) as RadarMapView
        radarMapView!!.setData(list)
    }

    private val list: List<RadarMapEntry>
        get() {
            val entries = mutableListOf<RadarMapEntry>()

            entries.add(RadarMapEntry("小彭", 26F))
            entries.add(RadarMapEntry("小花", 15F))
            entries.add(RadarMapEntry("小黑", 23F))
            entries.add(RadarMapEntry("小红", 18F))
            entries.add(RadarMapEntry("小黄", 33F))
            entries.add(RadarMapEntry("小可", 43F))

            return entries
        }
}
