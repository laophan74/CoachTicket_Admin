package com.example.coachticket_admin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.coachticket_admin.Adapter.TripAdapter;
import com.example.coachticket_admin.Model.Ticket;
import com.example.coachticket_admin.Model.Trip;
import com.example.coachticket_admin.databinding.ActivityControlTicketBinding;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ControlTicketActivity extends AppCompatActivity {
    private ActivityControlTicketBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private ImageView back;

    private TripAdapter tripAdapter;
    private ArrayList<Trip> lTrip = new ArrayList<>();
    private static String search;

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(ControlTicketActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    CheckTicket(result.getContents());
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityControlTicketBinding.inflate(getLayoutInflater());
        getSupportActionBar().hide();
        /*back = findViewById(R.id.backPress);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ControlTicketActivity.this, Main.class));

            }
        });*/

        LoadAllTrip();

        setContentView(binding.getRoot());
    }

    private void LoadAllTrip(){
        db.collection("Trips").whereEqualTo("isDone", false)
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc : task.getResult()){
                            Trip trip = doc.toObject(Trip.class);
                            trip.setTripID(doc.getId());
                            lTrip.add(trip);
                        }
                        tripAdapter = new TripAdapter(ControlTicketActivity.this, lTrip);
                        binding.listview.setAdapter(tripAdapter);
                    }
                });
        binding.listview.setOnItemClickListener((adapterView, view, i, l) -> {
            Trip trip = lTrip.get(i);
            search = trip.getTripID();
            QRcodeScanner(view);
        });
    }

    public void QRcodeScanner(View view) {
        barcodeLauncher.launch(new ScanOptions());
    }

    private void CheckTicket(String ticketID){
        DocumentReference ref = db.collection("Tickets").document(ticketID);
        ref.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Ticket ticket = task.getResult().toObject(Ticket.class);
                if(!ticket.getTrip().equals(search)){
                    Toast.makeText(ControlTicketActivity.this,
                            "Kh??ng tr??ng chuy???n ??i", Toast.LENGTH_LONG).show();
                    return;
                }
                if(Objects.equals(ticket.getStatus(), "Ch??a thanh to??n") ||
                   Objects.equals(ticket.getStatus(), "???? thanh to??n")){
                    Map<String, Object> update = new HashMap<>();
                    update.put("status", "???? s??? d???ng");
                    ref.update(update).addOnCompleteListener(task1 -> {
                        Toast.makeText(ControlTicketActivity.this, "So??t v?? th??nh c??ng", Toast.LENGTH_LONG).show();
                    });
                }
                else{
                    Toast.makeText(ControlTicketActivity.this, "V?? kh??ng h???p l???", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}