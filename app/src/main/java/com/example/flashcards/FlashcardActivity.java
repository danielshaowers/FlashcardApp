package com.example.flashcards;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import static android.support.v7.widget.AppCompatDrawableManager.get;

//Activity
public class FlashcardActivity extends AppCompatActivity{
    private Combine merged;
    private boolean shuffle = false;
    private ArrayList<Integer> shuffleTracker = new ArrayList<>();
    private int currentTermIndex = 0;  //tracks which term in Combine to use
    private int previousDefIndex = 0;
    private ArrayList<Info> currentTerm;
    private Info currentInfo;
    private TextView text;
    private ImageView pic;
    private TextView frac;
    private TextView termOrDef;
    private int currentProgress; //tracks progression through the terms
    private int maxProgress; //helps determine whether we're discovering something new or not


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_flashcard);

        ArrayList<Info> receipt = getIntent().<Info>getParcelableArrayListExtra("success");
        receipt.remove(receipt.size() -1);
        receipt.remove(receipt.size() - 1); //removes the two useless spaces
        merged = new Combine(receipt);
         merged.combineAll(); //can i confirm this part works?
        for (ArrayList<Info> a: merged.getCombined()){
            for (Info i: a){
                System.out.println("combineAll attempt " + i.getText().toString());
            }
        }
        for (Info i: receipt)
            System.out.println("parcel attempt" + i.getText().toString()); //i have an issue when someone clicks test without clicking out, the last thing they input isn't saved

       if (!shuffle) {
           currentTerm = merged.getCombined().get(0);
       }
       else{
           currentTermIndex = (int)(Math.random() * merged.getCombined().size());
           currentTerm = merged.getCombined().get(currentTermIndex);
           shuffleTracker.add(currentTermIndex);
       }
        currentInfo = currentTerm.get(0);
        text = findViewById(R.id.body);
        pic = findViewById(R.id.imageView);
        frac = findViewById(R.id.fraction);
        termOrDef = findViewById(R.id.termOrDef);

       updateAll();
    }
    //updates the views based on
    public void updateAll(){

        text.setText(currentInfo.getText());
        frac.setText(currentInfo.getFraction()); //this won't be accurate if we do shuffle.
        if (currentTermIndex == 0)
            termOrDef.setText("Term");
        else
            termOrDef.setText("Definition " + previousDefIndex + " / " + merged.getCombined().get(currentTermIndex).size());
        try {
            pic.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), currentInfo.getUri()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shuffleClicked(View view){
        shuffle = !shuffle;
    }
    public void nextClicked(View view) {
        next(shuffle);
        updateAll();
    }

    public void previousClicked(View view){
            if (--previousDefIndex < 0) { //decreases def index by one
                currentTermIndex = shuffleTracker.get(--currentProgress); //decreases progress by one
                currentInfo = currentTerm.get(previousDefIndex = currentTerm.size() - 1);  //goes to the last definition. oh man i need the last hint also
            }
            else {
                currentInfo = currentTerm.get(previousDefIndex);
            }
            updateAll();
    }

    //updates all this shit. link this to on button click
    //shuffle is true when "shuffle" is toggled
    public void next(boolean shuffle) {
            //i can't imagine previous term ever being null
            if (currentTerm != null && previousDefIndex++ >= currentTerm.size() - 1) { //if we went through all the definitions of the previous term, then we get a new card
                if (shuffle) {
                    currentTermIndex = (int) (Math.random() * merged.getCombined().size());
                    currentTerm = merged.getCombined().get(currentTermIndex); //gets a random term from the list of possible terms
                }
                else { //if shuffle isn't on, then we get a new card just by incrementing by one.
                    // currentTerm = merged.getCombined().get(++currentTermIndex % merged.getCombined().size()); //not sure why i'm doing mod here.
                 if (++currentTermIndex >= merged.getCombined().size()) //checks if we're at the limit or not
                     currentTermIndex = 0;
                    currentTerm = merged.getCombined().get(currentTermIndex);
                }
                previousDefIndex = 0;
                currentProgress++; //tracks how many words we've gone through?
            }
            else      //else there are more definitions to go through
                currentTerm = merged.getCombined().get(currentTermIndex);
           if (currentProgress > maxProgress) {
               maxProgress = currentProgress;
               shuffleTracker.add(currentTermIndex);
           }
            currentInfo = currentTerm.get(previousDefIndex);
    }
}


//so the way shuffletracker is working, it stores currentTermIndex as many times as there are definitions for each term. not sure if that's what i actually want though...i don't think it is.
//i think i need to track the definition index as well as the




