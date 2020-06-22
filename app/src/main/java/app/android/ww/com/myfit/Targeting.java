/*
프로그램명: Targeting
작성자: 김정빈
작성일: 2020.06.22
프로그램 설명: 수분 섭취 목표 클래스
© 2020 JungBin Kim <kiria7373@gmail.com>
 */
package app.android.ww.com.myfit;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Targeting {

    public String waterTarget;
    public String waterTime;

    public Targeting() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Targeting(String waterTarget, String waterTime) {
        this.waterTarget = waterTarget;
        this.waterTime = waterTime;
    }

    public String getWaterTarget() {
        return waterTarget;
    }

    public void setWaterTarget(String waterTarget) {
        this.waterTarget = waterTarget;
    }

    public String getWaterTime() {
        return waterTime;
    }

    public void setWaterTime(String waterTime) {
        this.waterTime = waterTime;
    }

    @Override
    public String toString() {
        return "Targeting{" +
                "waterTarget='" + waterTarget + '\'' +
                ", waterTime='" + waterTime + '\'' +
                '}';
    }
}