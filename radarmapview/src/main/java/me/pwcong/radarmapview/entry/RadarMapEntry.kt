package me.pwcong.radarmapview.entry

class RadarMapEntry(var name: String, var value: Float) {
    var angle: Float = 0f
    var x: Float = 0f
    var y: Float = 0f

    override fun toString(): String {
        return "RadarMapEntry{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", angle=" + angle +
                ", x=" + x +
                ", y=" + y +
                '}'
    }
}
