package app.android.ww.com.myfit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class WaterActivity extends AppCompatActivity {

    TextView textView;
    TextView textView1;
    EditText et_water_mount;
    Button btn_save;
    Button btn_water_target;
    Button btn_water_graph;

    private DatabaseReference mDatabase;

    Date currentDate = Calendar.getInstance().getTime();
    String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(currentDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        String getString = getIntent().getStringExtra("twater");
        String getString2 = getIntent().getStringExtra("wcycle");
        int getInt = getIntent().getIntExtra("wwater",1);
        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView1);
        if(getInt>1) {
            textView.setText("오늘의 목표 : " + getString + "mL");
            textView1.setText(getString2 + "시간마다 " + getInt + "mL씩 마시자!");
        }
        btn_water_target = findViewById(R.id.btn_water_target);
        btn_water_graph = findViewById(R.id.btn_water_graph);
        et_water_mount = findViewById(R.id.et_water_mount);
        btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getWaterMount = et_water_mount.getText().toString();

                //hashmap 만들기
                HashMap result = new HashMap<>();
                result.put("mount", getWaterMount);

                writeWaterMount(date, getWaterMount);
            }
        });
        btn_water_target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WaterTarget.class);
                startActivity(intent);
            }
        });
        btn_water_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WaterGraph.class);
                startActivity(intent);
            }
        });
    }
    private void writeWaterMount(String wd, String wm) {
        Water water = new Water(wm);

        mDatabase.child("waters").child(wd).setValue(water)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(WaterActivity.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(WaterActivity.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(WaterActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
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
