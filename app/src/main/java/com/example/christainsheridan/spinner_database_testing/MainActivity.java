package com.example.christainsheridan.spinner_database_testing;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // References to the editText field in the layout
        final EditText AddBuilding, AddRoom;
        AddBuilding = findViewById(R.id.editText);
        AddRoom = findViewById(R.id.editText2);

        // Buttons for Add/Delete Values
        final Button AddValues = findViewById(R.id.button);
        final Button DeleteValues = findViewById(R.id.button2);

        // References to the Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference fDatabaseRoot = database.getReference("");

        // Looking for the Buildings and displaying it in the spinner
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

        // Looking for the Rooms and displaying it in the spinner
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

        // Reference to the Database
        final FirebaseDatabase uDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mDatabaseRoot = uDatabase.getReference("");

        // Adding Values to the Database
        AddValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If both Building and Room are empty
                if(AddBuilding.getText().toString().equals("") && AddRoom.getText().toString().equals("")){

                    Toast.makeText(MainActivity.this, "You can't add nothing!", Toast.LENGTH_SHORT).show();
                }
                // If both Building and Room is filled
                else if (!AddBuilding.getText().toString().equals("") && !AddRoom.getText().toString().equals("")){

                    mDatabaseRoot.child("buildings").child(AddBuilding.getText().toString().trim()).child("buildingName").setValue(AddBuilding.getText().toString().trim());
                    mDatabaseRoot.child("rooms").child(AddRoom.getText().toString().trim()).child("roomName").setValue(AddRoom.getText().toString().trim());

                    AddBuilding.setText("");
                    AddRoom.setText("");
                    Toast.makeText(MainActivity.this, "Added Building and Room", Toast.LENGTH_SHORT).show();
                }
                // If Building is Filled but Room is empty
                else if (!AddBuilding.getText().toString().equals("") && AddRoom.getText().toString().equals("")){

                    mDatabaseRoot.child("buildings").child(AddBuilding.getText().toString().trim()).child("buildingName").setValue(AddBuilding.getText().toString().trim());

                    AddBuilding.setText("");
                    Toast.makeText(MainActivity.this, "Building Added", Toast.LENGTH_SHORT).show();
                }
                // If Room is filled but Building is empty
                else if (AddBuilding.getText().toString().equals("") && !AddRoom.getText().toString().equals("")){

                    mDatabaseRoot.child("rooms").child(AddRoom.getText().toString().trim()).child("roomName").setValue(AddRoom.getText().toString().trim());

                    AddRoom.setText("");
                    Toast.makeText(MainActivity.this, "Room Added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Removing Values from the Database
        DeleteValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If both Building and Room are empty
                if(AddBuilding.getText().toString().equals("") && AddRoom.getText().toString().equals("")){

                    Toast.makeText(MainActivity.this, "You can't delete nothing!", Toast.LENGTH_SHORT).show();
                }
                // If both Building and Room is filled
                else if (!AddBuilding.getText().toString().equals("") && !AddRoom.getText().toString().equals("")){

                    fDatabaseRoot.child("buildings").child(AddBuilding.getText().toString().trim()).child("buildingName").removeValue();
                    fDatabaseRoot.child("rooms").child(AddRoom.getText().toString().trim()).child("roomName").removeValue();

                    AddBuilding.setText("");
                    AddRoom.setText("");
                    Toast.makeText(MainActivity.this, "Deleted Building and Room", Toast.LENGTH_SHORT).show();
                }
                // If Building is Filled but Room is empty
                else if (!AddBuilding.getText().toString().equals("") && AddRoom.getText().toString().equals("")){

                    fDatabaseRoot.child("buildings").child(AddBuilding.getText().toString().trim()).child("buildingName").removeValue();

                    AddBuilding.setText("");
                    Toast.makeText(MainActivity.this, "Building Deleted", Toast.LENGTH_SHORT).show();
                }
                // If Room is filled but Building is empty
                else if (AddBuilding.getText().toString().equals("") && !AddRoom.getText().toString().equals("")){

                    fDatabaseRoot.child("rooms").child(AddRoom.getText().toString().trim()).child("roomName").removeValue();

                    AddRoom.setText("");
                    Toast.makeText(MainActivity.this, "Room Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
