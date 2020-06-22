/*
프로그램명: WaterTarget
작성자: 김정빈
작성일: 2020.06.22
프로그램 설명: 당일의 수분 섭취 목표와 섭취 빈도를 설정한다.
© 2020 JungBin Kim <kiria7373@gmail.com>
 */
package app.android.ww.com.myfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class WaterTarget extends AppCompatActivity {

    EditText et_water_target;
    EditText et_water_cycle;
    Button btn_target_save;

    private DatabaseReference mDatabase;

    Date currentDate = Calendar.getInstance().getTime();
    String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(currentDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watertarget);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        btn_target_save = findViewById(R.id.btn_target_save);

        btn_target_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WaterActivity.class);

                et_water_target = findViewById(R.id.et_water_target);
                et_water_cycle = findViewById(R.id.et_water_cycle);

                String getWaterTarget = et_water_target.getText().toString();
                String getWaterCycle = et_water_cycle.getText().toString();

                HashMap result = new HashMap<>();
                result.put("waterTarget", getWaterTarget);
                result.put("waterTime", getWaterCycle);

                writeWaterTarget(getWaterTarget,getWaterCycle);

                int a = Integer.parseInt(getWaterTarget);
                int b = Integer.parseInt(getWaterCycle);
                int c = a/(16/b);

                intent.putExtra("twater", getWaterTarget);
                intent.putExtra("wcycle", getWaterCycle);
                intent.putExtra("wwater", c);
                startActivity(intent);
            }
        });
    }
    private void writeWaterTarget(String waterTarget, String waterTime) {
        Targeting targeting = new Targeting(waterTarget,waterTime);

        mDatabase.child("targets").child(date).setValue(targeting)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(WaterTarget.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(WaterTarget.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
