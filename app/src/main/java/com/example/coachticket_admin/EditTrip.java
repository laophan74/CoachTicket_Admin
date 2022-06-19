package com.example.coachticket_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coachticket_admin.Model.City;
import com.example.coachticket_admin.Model.Coach;
import com.example.coachticket_admin.Model.Trip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditTrip extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Button edit;
    private ImageButton datePicker;
    private RelativeLayout delete;
    private RadioButton rdYes, rdNo;
    private TextView dateTextview;

    private Spinner city1;
    private Spinner city2;
    private Spinner coach;
    private Calendar calendar;

    private Trip trip;

    private Coach coachName;
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
        coach = findViewById(R.id.coach);
        delete = findViewById(R.id.deleteTrip);
        dateTextview = findViewById(R.id.dateTextview);
        datePicker = findViewById(R.id.datePickerActions);
        edit = findViewById(R.id.editBtn);
        rdYes = findViewById(R.id.radioYes);
        rdNo = findViewById(R.id.radioNo);
        calendar = Calendar.getInstance();

        // Tạo DateTimePicker
        datePicker.setOnClickListener(view -> ShowDateTimePicker());

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

        LoadSpinner();
        LoadTrip();
    }

    private void LoadTrip(){
        db.collection("Trips").document(AllTrip.document)
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                        trip = doc.toObject(Trip.class);
                        Date d = trip.getDeparture_time().toDate();
                        calendar.setTime(d);
                        SetDateTextView();
                        if(trip.isDone()) rdYes.setChecked(true);
                        setPintext(city1,trip.getStart());
                        setPintext(city2,trip.getFinish());
                        setPintext(coach,trip.getCoach());
                    }
                });
    }

    private void LoadSpinner() {
        ArrayAdapter<String> aaC = new ArrayAdapter<>(EditTrip.this,
                android.R.layout.simple_spinner_dropdown_item, Main.lDist);
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
        ArrayAdapter<String> aaC1 = new ArrayAdapter<>(EditTrip.this,
                android.R.layout.simple_spinner_dropdown_item, Main.lDist1);
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

    private City ReturnCity(String x) {
        for (City item : Main.lCity) {
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

    private Coach ReturnCoach(String x) {
        for (Coach item : Main.lCoach) {
            if (x == item.getPlate()) return item;
        }
        return null;
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
        Date date = calendar.getTime();
        Timestamp ts = new Timestamp(date);
        String coach = coachName.getPlate();
        boolean bool = rdYes.isChecked();

        Map<String, Object> docData = new HashMap<>();
        docData.put("tripName", start+" - "+finish);
        docData.put("start", start);
        docData.put("finish", finish);
        docData.put("coach", coach);
        docData.put("departure_time", ts);
        docData.put("isDone", bool);

        db.collection("Trips").document(AllTrip.document)
                .set(docData).addOnSuccessListener(unused -> {
                    Toast.makeText(EditTrip.this, "Thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditTrip.this, AllTrip.class));
                });
    }

    private void ShowDateTimePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, i, i1, i2) -> {
            calendar.set(Calendar.YEAR, i);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.DAY_OF_MONTH, i2);

            TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, t, t1) -> {
                calendar.set(Calendar.HOUR_OF_DAY, t);
                calendar.set(Calendar.MINUTE, t1);
                SetDateTextView();
            };
            new TimePickerDialog(this, timeSetListener, 0, 0, false).show();
        };
        new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void SetDateTextView(){
        String pickUp =
                calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " "
                        + calendar.get(Calendar.DAY_OF_MONTH) + "/"
                        +(calendar.get(Calendar.MONTH)+1) + "/"
                        + calendar.get(Calendar.YEAR);
        dateTextview.setText(pickUp);
    }
}