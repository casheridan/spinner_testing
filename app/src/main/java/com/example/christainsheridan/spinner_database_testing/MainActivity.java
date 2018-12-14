package com.example.christainsheridan.spinner_database_testing;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText AddBuilding, AddRoom;
        AddBuilding = findViewById(R.id.editText);
        AddRoom = findViewById(R.id.editText2);

        final Button AddValues = findViewById(R.id.button);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference fDatabaseRoot = database.getReference("");


        fDatabaseRoot.child("buildings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> buildings = new ArrayList<>();

                for (DataSnapshot buildingsSnapshot: dataSnapshot.getChildren()) {
                    String buildingName = buildingsSnapshot.child("buildingName").getValue(String.class);
                    buildings.add(buildingName);
                }

                Spinner buildingSpinner = findViewById(R.id.spinner);
                ArrayAdapter<String> buildingAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, buildings);
                buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                buildingSpinner.setAdapter(buildingAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fDatabaseRoot.child("rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> rooms = new ArrayList<>();

                for (DataSnapshot roomsSnapshot: dataSnapshot.getChildren()) {
                    String roomName = roomsSnapshot.child("roomName").getValue(String.class);
                    rooms.add(roomName);
                }

                Spinner roomSpinner = findViewById(R.id.spinner2);
                ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, rooms);
                roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                roomSpinner.setAdapter(roomAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final FirebaseDatabase uDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mDatabaseRoot = uDatabase.getReference("");

        AddValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AddBuilding.getText().toString().equals("") && AddRoom.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "You can't add nothing!", Toast.LENGTH_SHORT).show();
                }else if (!AddBuilding.getText().toString().equals("") && AddRoom.getText().toString().equals("")){
                    mDatabaseRoot.child("buildings").push().child("buildingName").setValue(AddBuilding.getText().toString().trim());
                    AddBuilding.setText("");
                    Toast.makeText(MainActivity.this, "Building Added", Toast.LENGTH_SHORT).show();
                }else if (AddBuilding.getText().toString().equals("") && !AddRoom.getText().toString().equals("")){
                    mDatabaseRoot.child("rooms").push().child("roomName").setValue(AddRoom.getText().toString().trim());
                    AddRoom.setText("");
                    Toast.makeText(MainActivity.this, "Building Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
