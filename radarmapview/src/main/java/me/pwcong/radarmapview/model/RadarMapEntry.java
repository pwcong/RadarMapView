package me.pwcong.radarmapview.model;

/**
 * Created by pwcong on 2016/9/9.
 */
public class RadarMapEntry {

    String name;
    float value;

    float angle;
    float x;
    float y;

    public RadarMapEntry(String name, float value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "RadarMapEntry{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", angle=" + angle +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
