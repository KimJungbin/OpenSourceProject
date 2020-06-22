/*
프로그램명: Water
작성자: 김정빈
작성일: 2020.06.22
프로그램 설명: Water 클래스
© 2020 JungBin Kim <kiria7373@gmail.com>
 */
package app.android.ww.com.myfit;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Water {

    public String waterMount;

    public Water() {
        // Default constructor required for calls to DataSnapshot.getValue(Water.class)
    }

    public Water(String waterMount) {
        this.waterMount = waterMount;
    }

    public String getWaterMount() {
        return waterMount;
    }

    public void setWaterMount(String waterMount) {
        this.waterMount = waterMount;
    }

    @Override
    public String toString() {
        return "Water{" +
                "waterMount='" + waterMount + '\'' + '}';
    }
}
