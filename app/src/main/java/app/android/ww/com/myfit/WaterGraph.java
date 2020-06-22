/*
프로그램명: WaterGraph
작성자: 김정빈
작성일: 2020.06.22
프로그램 설명: DB에 저장된 기록을 불러와 최근 일주일간의 수분 그래프를 표시하여준다.
또한 당일, 일주일간 수분섭취가 올바른지 판단하여 알려준다.
© 2020 JungBin Kim <kiria7373@gmail.com>
 */
package app.android.ww.com.myfit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;


public class WaterGraph extends Activity {

    private LineChart lineChart;
    Button btn_back;

    private DatabaseReference mDatabase;

    Date currentDate = Calendar.getInstance().getTime();
    String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(currentDate);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watergraph);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("waters").limitToLast(7).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int[] a = new int[7];
                int b=0;
                for(DataSnapshot dateD : dataSnapshot.getChildren()) {
                    Water post = dateD.getValue(Water.class);

                    a[b] = Integer.parseInt(post.getWaterMount());
                    if(a[b]<2000){
                        TextView warning = (TextView) findViewById(R.id.text_week_warning);
                        warning.setVisibility(View.VISIBLE);

                        TextView safe = (TextView) findViewById(R.id.text_week_safe);
                        safe.setVisibility(View.GONE);
                    }
                    b++;
                }
                if(a[6]<2000) {
                    TextView warning = (TextView) findViewById(R.id.text_today_warning);
                    warning.setVisibility(View.VISIBLE);

                    TextView safe = (TextView) findViewById(R.id.text_today_safe);
                    safe.setVisibility(View.GONE);
                }

                int day = Integer.parseInt(date)-20200600;
                lineChart = (LineChart)findViewById(R.id.chart);
                List<Entry> entries = new ArrayList<>();
                entries.add(new Entry(day-6, a[0]));
                entries.add(new Entry(day-5, a[1]));
                entries.add(new Entry(day-4, a[2]));
                entries.add(new Entry(day-3, a[3]));
                entries.add(new Entry(day-2, a[4]));
                entries.add(new Entry(day-1, a[5]));
                entries.add(new Entry(day, a[6]));

                LineDataSet lineDataSet = new LineDataSet(entries, "마신 물의 양");
                lineDataSet.setLineWidth(2);
                lineDataSet.setCircleRadius(6);
                lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
                lineDataSet.setCircleHoleColor(Color.BLUE);
                lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
                lineDataSet.setDrawCircleHole(true);
                lineDataSet.setDrawCircles(true);
                lineDataSet.setDrawHorizontalHighlightIndicator(false);
                lineDataSet.setDrawHighlightIndicators(false);
                lineDataSet.setDrawValues(false);

                LineData lineData = new LineData(lineDataSet);
                lineChart.setData(lineData);

                XAxis xAxis = lineChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextColor(Color.BLACK);
                xAxis.enableGridDashedLine(8, 24, 0);

                YAxis yLAxis = lineChart.getAxisLeft();
                yLAxis.setTextColor(Color.BLACK);

                YAxis yRAxis = lineChart.getAxisRight();
                yRAxis.setDrawLabels(false);
                yRAxis.setDrawAxisLine(false);
                yRAxis.setDrawGridLines(false);

                Description description = new Description();
                description.setText("");

                lineChart.setDoubleTapToZoomEnabled(false);
                lineChart.setDrawGridBackground(false);
                lineChart.setDescription(description);
                lineChart.animateY(2000, Easing.EaseInCubic);
                lineChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WaterActivity.class);
                startActivity(intent);
            }
        });
    }

    /*private void readWater(String wd){
        mDatabase.child("waters").child(wd).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.getValue(Water.class) != null){
                    Water post = dataSnapshot.getValue(Water.class);
                    Log.w("FireBaseData", "getData" + post.toString());
                } else {
                    Toast.makeText(WaterGraph.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }*/
}