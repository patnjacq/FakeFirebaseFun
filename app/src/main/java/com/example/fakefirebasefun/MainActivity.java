package com.example.fakefirebasefun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editTextName;

    Button buttonAddFoodItem;
    ListView listViewFoodItems;

    //a list to store all the artist from firebase database
    ArrayList<FoodItem>  mainInventory;

    //our database reference object
    DatabaseReference databaseFakeFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the reference of artists node
        databaseFakeFood = FirebaseDatabase.getInstance().getReference("mainInventory");
        //getting views

        buttonAddFoodItem = (Button) findViewById(R.id.buttonAddFoodItem);
        listViewFoodItems = (ListView) findViewById(R.id.listViewFoodItems);
        mainInventory = new ArrayList<>();

        //adding an onclicklistener to button
        buttonAddFoodItem.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addFoodItem();
            }
        });
        //attaching listener to listview
       /* listViewFoodItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int i, long id) {

                FoodItem artist = mainInventory.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), ArtistActivity.class);

                //putting artist name and id to intent
                intent.putExtra(ARTIST_ID, artist.getArtistId());
                intent.putExtra(ARTIST_NAME, artist.getArtistName());

                //starting the activity with intent
                startActivity(intent);
            }
        });*/

    }
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseFakeFood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                mainInventory.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    FoodItem food = postSnapshot.getValue(FoodItem.class);
                    //adding artist to the list
                    mainInventory.add(food);
                }

                //creating adapter
                FoodList inventoryAdapter = new FoodList(MainActivity.this, mainInventory);
                //attaching adapter to the listview
                listViewFoodItems.setAdapter(inventoryAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void addFoodItem(){
        //getting a unique id using push().getKey() method
        //it will create a unique id and we will use it as the Primary Key for our Artist
        String id = databaseFakeFood.push().getKey();
        //create the foodItem
        FoodItem foodItem1 = new FoodItem( new Date(121, 2, 13), "yogurt", FoodType.Dairy);
        //Save the FoodItem
        databaseFakeFood.child(id).setValue(foodItem1);
    }



    public void onAdd(View view) {
        FoodItem foodItem1 = new FoodItem( new Date(121, 2, 11), "yogurt", FoodType.Dairy);
       // Intent intent = new Intent(this, AddActivity.class);
       // startActivity(intent);
    }

}