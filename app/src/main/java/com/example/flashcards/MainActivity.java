package com.example.flashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //sends the main activity to the activity clicked on
    //do i need three different methods for each button? is there a way to know the button that is clicked?
    public void viewSet(View view) {
        //Intent intent = new Intent(this, MySet.class);     //make a class called Set
        //Button clicked = (Button) view;  //i might have to do findByID. Not really sure how the "view" input works
        startActivity(new Intent(this, InputActivity.class));
    }
}
