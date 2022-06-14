package com.example.coachticket_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coachticket_admin.Adapter.TripAdapter;
import com.example.coachticket_admin.Model.City;
import com.example.coachticket_admin.Model.Trip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EditTrip extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Button edit;
    private ImageButton datePicker;
    private RelativeLayout delete;

    private TextView dateTextview;
    private EditText starttime;

    private Spinner city1;
    private Spinner city2;

    private ArrayList<City> lCity = new ArrayList<>();
    private ArrayList<String> lDist = new ArrayList<>();
    private ArrayList<Trip> lTrip = new ArrayList<>();
    private Trip trip;


    private City cStart;
    private City cEnd;

    private int y;
    private int m;
    private int d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);
        getSupportActionBar().hide();

        city1 = findViewById(R.id.city1);
        city2 = findViewById(R.id.city2);
        starttime = findViewById(R.id.starttime);
        delete = findViewById(R.id.deleteTrip);
        dateTextview = findViewById(R.id.dateTextview);
        datePicker = findViewById(R.id.datePickerActions);
        edit = findViewById(R.id.editBtn);

        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);

        // Tạo DateTimePicker
        datePicker.setOnClickListener(view -> {
            DatePickerDialog pickerDialog = new DatePickerDialog(EditTrip.this,
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

        //Xoá trip
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteTrip();
            }
        });

        //Edit trip
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditBtnClick();
            }
        });

        LoadCities();
        LoadTrip();
    }

    private void LoadTrip(){
        db.collection("Trips").document(AllTrip.document)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    trip = doc.toObject(Trip.class);
                    starttime.setText(trip.getStarttime());
                    dateTextview.setText(trip.getDate());
                    setPintext(city1,trip.getStart());
                    setPintext(city2,trip.getFinish());
                }
            }
        });
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
                                ArrayAdapter<String> aaC = new ArrayAdapter<>(EditTrip.this,
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

    private void setPintext(Spinner spinner, String text){
        for(int i = 0; i < spinner.getAdapter().getCount(); i++){
            if(spinner.getAdapter().getItem(i).toString().contains(text)){
                spinner.setSelection(i);
            }
        }
    }

    private void DeleteTrip(){
        AlertDialog.Builder altd = new AlertDialog.Builder(EditTrip.this);
        altd.setMessage("Bạn có muốn xoá?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.collection("Trips").document(AllTrip.document)
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EditTrip.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditTrip.this, AllTrip.class));

                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alert = altd.create();
        alert.setTitle("Cảnh báo!!!");
        alert.show();
    }

    private void EditBtnClick(){
        String start = cStart.getCname();
        String finish = cEnd.getCname();
        String date = dateTextview.getText().toString();
        String timestart = starttime.getText().toString();

        Map<String, Object> docData = new HashMap<>();
        docData.put("tripName", start+" - "+finish);
        docData.put("start", start);
        docData.put("finish", finish);
        docData.put("date", date);
        docData.put("starttime", timestart);

        db.collection("Trips").document(AllTrip.document)
                .set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(EditTrip.this, "Thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditTrip.this, AllTrip.class));

            }
        });

    }
}