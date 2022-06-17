package com.example.coachticket_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coachticket_admin.Model.City;
import com.example.coachticket_admin.Model.Coach;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AddTrip extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Button addBtn;
    private ImageButton datePicker;

    private TextView dateTextview;
    private EditText starttime;

    private Spinner city1;
    private Spinner city2;
    private Spinner coach;

    private ArrayList<City> lCity = new ArrayList<>();
    private ArrayList<String> lDist = new ArrayList<>();
    private ArrayList<Coach> lCoach = new ArrayList<>();
    private ArrayList<String> lDist1 = new ArrayList<>();

    private City cStart;
    private City cEnd;
    private Coach coachName;

    private int y;
    private int m;
    private int d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        getSupportActionBar().hide();

        addBtn = findViewById(R.id.addBtn);
        city1 = findViewById(R.id.city1);
        city2 = findViewById(R.id.city2);
        starttime = findViewById(R.id.starttime);
        coach = findViewById(R.id.coach);

        dateTextview = findViewById(R.id.dateTextview);
        datePicker = findViewById(R.id.datePickerActions);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBtnClick();
            }
        });

        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);

        datePicker.setOnClickListener(view -> {
            DatePickerDialog pickerDialog = new DatePickerDialog(AddTrip.this,
                    (datePicker, i, i1, i2) -> {
                        dateTextview.setText(i2 + "/" + (i1 + 1) + "/" + i);
                        y = i;
                        m = i1;
                        d = i2;
                    }, y, m, d);
            pickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
            pickerDialog.show();
        });

        dateTextview.setText(y + "/" + (m + 1) + "/" + d);

        LoadCoachs();
        LoadCities();
    }

    private void LoadCities() {
        db.collection("Cities").get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    City city = doc.toObject(City.class);
                                    lCity.add(city);
                                }
                                Collections.sort(lCity);
                                for (City item : lCity) {
                                    lDist.add(item.getCname());
                                }
                                ArrayAdapter<String> aaC = new ArrayAdapter<>(AddTrip.this,
                                        android.R.layout.simple_spinner_dropdown_item, lDist);
                                city1.setAdapter(aaC);
                                city1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        cStart = ReturnCity(city1.getSelectedItem().toString());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                                city2.setAdapter(aaC);
                                city2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        cEnd = ReturnCity(city2.getSelectedItem().toString());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                        }
                );
    }

    private City ReturnCity(String x) {
        for (City item : lCity) {
            if (x == item.getCname()) return item;
        }
        return null;
    }

    private void LoadCoachs() {
        db.collection("TravelCars").get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Coach coach = doc.toObject(Coach.class);
                                    lCoach.add(coach);
                                }
                                //Collections.sort(lCoach);
                                for (Coach item : lCoach) {
                                    lDist1.add(item.getPlate());
                                }
                                ArrayAdapter<String> aaC1 = new ArrayAdapter<>(AddTrip.this,
                                        android.R.layout.simple_spinner_dropdown_item, lDist1);
                                coach.setAdapter(aaC1);
                                coach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        coachName = ReturnCoach(coach.getSelectedItem().toString());
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });

                            }
                        }
                );
    }
    private Coach ReturnCoach(String x) {
        for (Coach item : lCoach) {
            if (x == item.getPlate()) return item;
        }
        return null;
    }
    private void addBtnClick(){
        String start = cStart.getCname();
        String finish = cEnd.getCname();
        String date = dateTextview.getText().toString();
        String coach = coachName.getPlate();

        Map<String, Object> docData = new HashMap<>();
        docData.put("tripName", start+" - "+finish);
        docData.put("start", start);
        docData.put("finish", finish);
        docData.put("departure_time", date);
        docData.put("coach", coach);

        db.collection("Trips").document()
                .set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddTrip.this, "Thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddTrip.this, AllTrip.class));

            }
        });

    }

}