package com.example.flashcards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.flashcards.InputActivity.GET_FROM_GALLERY; //not sure if it should be input activity

//this class creates views for data, and replaces the content of views when they are no longer available
//dang it i can't figure out how to make the inputviewHolder work as a static class instead of nonstatic class
public class InputAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Info> allData;
    private Context mContext;
    // private int position;
    private int focusPosition = 0;
    private View.OnFocusChangeListener focusChangeListener;
    private int previousPosition;
    private EditText oldEditText;
    private ArrayList<LocationStore> locationStore = new ArrayList<>();
    // private ArrayList<Integer> oldTracker;

    public int getPos() {
        return focusPosition;
    }

    public InputAdapter(ArrayList<Info> input) {
        allData = input;
    }

    public class InputViewHolder extends RecyclerView.ViewHolder {

        public EditText term;
        public ImageView image;
        public ImageButton imageButton, audioButton;
        public MediaStore.Audio audio; //this one i just yoloed. don't know what it actually is
        public TextView fraction;


        public InputViewHolder(final View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.image);
            this.fraction = (TextView) itemView.findViewById(R.id.fraction);
            this.term = itemView.findViewById(R.id.edit_text);  //very important. sets the string of the viewHolder equal to the string in the edit text
            imageButton = itemView.findViewById(R.id.photoButton);
            imageButton.setOnClickListener(new AddImageListener());
            term.setFocusable(true);
           /* term.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("onclick called");
                    term.setOnFocusChangeListener(focusChangeListener); //THIS IS WHAT I JUST CHANGED
                    addNewRow(getLayoutPosition());
                }
            }); */

            term.setOnTouchListener(new myListener());
            focusChangeListener = new View.OnFocusChangeListener() { //i might have to specifically do each textview, edittext, imageview
                @Override
                //called when the focus state of a view has changed. when it gets focus, and when it loses focus
                public void onFocusChange(final View v, boolean hasFocus) { //i guess this isn't used when you click directly
                    if (hasFocus) {
                        //    focusPosition = getAdapterPosition(); //why the heck does this give a different position
                        //  focusPosition = (int)v.getTag();
                        //  System.out.println(focusPosition + " hasFocus");
                        //     looks like adapterposition is different from the click position :(
                    }
                    if (!hasFocus) { //so my problem is that it loses focus while it's still being edited for some reason
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                focusPosition = (int) v.getTag(); // perfect EXCEPT when a new row is added, this !hasFocus is called twice...?
                                System.out.println(focusPosition + "focusposition is!!!!");
                                if (focusPosition >= 0 && allData.get(focusPosition) != null && allData != null) {
                                    oldEditText = v.findViewById(R.id.edit_text);
                                    System.out.println(focusPosition + "lost focus");
                                    Info lastTerm = allData.get(focusPosition).copyInfo(allData.get(focusPosition));   //sets lastterm to what it was before updating the arraylist
                                    System.out.println("the last edittext is " + oldEditText.getText().toString());
                                    if (!lastTerm.getText().equals(oldEditText.getText().toString())) {  //if the edittext changed, update the array's text
                                        System.out.println("the last edit text changed and has been updated");
                                        allData.get(focusPosition).setText(oldEditText.getText().toString());
                                        System.out.println("current focus position " + focusPosition);
                                        System.out.println("allData's text at " + focusPosition + " was set to " + allData.get(focusPosition).getText());
                                        updateFraction(allData.get(focusPosition), focusPosition, lastTerm);
                                    }

                                    // focusPosition = v;

                                    oldEditText.setOnFocusChangeListener(null);
                                }
                            }
                        });
                    }
                }
            };


            //      term.setOnFocusChangeListener(focusChangeListener);
            //   imageButton.setOnFocusChangeListener(focusChangeListener);
            // audioButton.setOnFocusChangeListener(focusChangeListener);
            // image.setOnFocusChangeListener(focusChangeListener);

           /* term.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(final View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        term.setOnFocusChangeListener(focusChangeListener);
                        //updates the focus position to what has the focus
                        System.out.println(getLayoutPosition() + " touched");
                        addNewRow(getLayoutPosition());
                    }

                    return false;
                }
            }); */
        }


        public class myListener implements View.OnTouchListener {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setOnFocusChangeListener(focusChangeListener); //sets it so that when the view loses focus, this is all captured
                    // focusPosition = (int)v.getTag();
                    System.out.println(getLayoutPosition() + " touched");
                    addNewRow((int) v.getTag());
                    //  updateFraction(allData.get(focusPosition), focusPosition);
                  /*  if (focusPosition >= 0 && allData.get(focusPosition) != null && allData != null) {
                        System.out.println(focusPosition + "lost focus");
                        Info lastTerm = allData.get(focusPosition);
                        EditText oldTerm = v.findViewById(R.id.edit_text);  //sets edittext to the one that just LOST focus
                        System.out.println("the last edittext is " + oldTerm.getText().toString());
                        if (lastTerm.getText() != oldTerm.getText().toString()) {  //if the edittext changed, update the array's text
                            System.out.println("the last edit text changed and has been updated");
                            allData.get(focusPosition).setText(oldTerm.getText().toString());
                            updateFraction(allData.get(focusPosition)); //updates the fraction and notifies itemrange changed. seems like this is what causes the focus to be lost

                            notifyItemChanged(focusPosition);
                        }
                       // focusPosition = getLayoutPosition(); //when i reach position 3, it says 1 lost focus, instead of saying 2 lost focus

                    } */
                }
                return false;
            }

        }


        public class AddImageListener implements ImageButton.OnClickListener {
            @Override
            public void onClick(View view) {
                //starts an activity that opens the gallery
                focusPosition = getAdapterPosition(); //I think this one is unnecessary, since I have a focuschangelistener that sets the position on focus
                ((Activity) mContext).startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                //don't i need to override the method...in this class?
                //get the image and set the image
                if (allData.get(focusPosition).getImage() != null)
                    view.findViewById(R.id.image).setVisibility(View.VISIBLE); //does this after startactivityforresult and onactivityresult both finish executing I HOPE
                //if this for some reason doesn't work, try to do it in set1. i could get the view based on the position in the adapter somehow, then setvis from there
                if (allData.get(focusPosition).getUri() != null) {
                }
            }
        }
    }

    public void addNewRow(int position) {
        if ((position >= allData.size() - 1)) { //when it's the last position in the dataset, and it's not empty, we add two more infos
            allData.add(new Info(""));
            allData.get(position + 1).setFraction(1, 1); //replace this with a method that automatically checks the arraylist and updates the fractions
            allData.add(new Info(""));
            allData.get(position + 2).setFraction(1, 1);
            notifyItemInserted(position + 1);
            notifyItemInserted(position + 2);
        }
    }


    //creates new views (is invoked by the layout manager)
    //remember that viewgroup is a view that can be recycled into a different view
    //sets InputViewHolder to store the view given in the parameter
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_set1, parent, false);
        mContext = parent.getContext();
        return new InputViewHolder(view);
    }

    //replace the contents (data) of a view (invoked by the layout manager) when the view needs to be used again
    //get element from your dataset at this position
    //replace the contents of the view with that element
    //((InputViewHolder)holder).term.setOnFocusChangeListener(null); //this way, the FocusChangeListener can't be called WHILE onBindViewHolder is preparing info

    //this catches callback first. if no payload, then passes it on to onBindViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (!payloads.isEmpty()) {
            if (payloads.get(0) instanceof String) {
                ((InputViewHolder) holder).fraction.setText((String) payloads.get(0));
            }
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Info current = allData.get(position);
        EditText et = ((InputViewHolder) holder).term;
        //  if (((current.getText() != null))) {
        //    et.clearFocus();
        et.setText(current.getText());
        et.setTag(position);
        //   et.setOnFocusChangeListener(focusChangeListener);
        // }
       /* if (current.getImage() != null) {
            ((InputViewHolder) holder).image.setImageDrawable(current.getImage());
            ((InputViewHolder) holder).image.setVisibility(View.VISIBLE);
        }
        */
        if (current.getUri() != null) {
            ((InputViewHolder) holder).image.setImageDrawable(current.getImage());
            ((InputViewHolder) holder).image.setVisibility(View.VISIBLE);
            ((InputViewHolder) holder).image.setOnClickListener(new View.OnClickListener() {  //opens up the activity that makes a full screen image with some new buttons
                @Override
                public void onClick(View view) {
                    // open another activity on item click
                    Intent intent = new Intent(mContext, FullImageActivity.class);
                    intent.putExtra("image", allData.get(position).getUri()); // put image data in Intent
                    mContext.startActivity(intent); // start Intent
                }
            });
        }
        ((InputViewHolder) holder).fraction.setText(((current.getFraction())));
    }

    //return the size of the dataset (invoked by layout manager)
    @Override
    public int getItemCount() {
        return allData.size();   //i might want to return 1 greater so that we always have an empty editText that they can edit. or just make a button at the bottom. i think i'll do that
    }

    //returns index and place of the location storer
    public int getTermIndex(Info term) {
        for (int i = 0; i < locationStore.size(); i++) {
            if (term.equals(locationStore.get(i).getInfo())) {
                return i;
            }
        }
        return -1;
    }

    //position = the position of the current term in adapter
    //returns whether the item range was updated or not
    public void updateFraction(Info term, int position, Info lastTerm) {
        //i also need an if statement, that if the focus position is less than the size - 2, update fraction is called and it's not "1/1", then i need to update previous terms too. what a pain
        System.out.println("focus position in updated fraction is " + focusPosition);
        ArrayList<Integer> trackPosition = new ArrayList<>();

        if (focusPosition % 2 == 0) {
            int currentTermIndex = getTermIndex(term), previousTermIndex = getTermIndex((lastTerm));
            //i might want to check this in a different way
            if (currentTermIndex == -1) {  //if the current term added is new, then we add it to the end of the arraylist. also need to check if the position used to be something else
                locationStore.add(new LocationStore(term, position));
                currentTermIndex = locationStore.size() - 1;
            } else {
              if (previousTermIndex == -1){
                  locationStore.get(currentTermIndex).getIndex().add(position);
                }
              else {
                  if (!term.equals(lastTerm)) { //if the term already exists but is different from its original term, remove the previous and update the new term
                      int pos = -1;
                      boolean notFound = true;
                      ArrayList<Integer> previousTerm = locationStore.get(previousTermIndex).getIndex();
                      for (int i = 0; i < previousTerm.size() && notFound; i++) {
                          if (previousTerm.get(i) == position) {
                              pos = i;
                              notFound = false;
                          }
                      }
                      locationStore.get(previousTermIndex).getIndex().remove(pos);
                      locationStore.get(currentTermIndex).getIndex().add(position);
                  }
              }
             //if the term already exists and is the same from its original, do nothing

            }
            //if lastTerm is the same as the current term, do nothing.
            //if lastterm does not exist, just add it
            //if lastterm is different, then delete the previous

            //adds the location to our arraylist
            for (int i = 0; i < allData.size(); i += 2) {
                System.out.println("print all text " + allData.get(i).getText());
                if (term.equals(allData.get(i))) {
                    trackPosition.add(i);
                }
            }
            //sets fraction
          /*  Integer tracker = 1;
            for (int i = 0; i < trackPosition.size(); i++) {
                if (trackPosition.get(i) == position)
                    tracker = i + 1;   //represents the numerator of the term that was changed
                allData.get(trackPosition.get(i)).setFraction(i + 1, trackPosition.size());
                allData.get(trackPosition.get(i) + 1).setFraction(i + 1, trackPosition.size());
            } */
            ArrayList<Integer> currentLocation = locationStore.get(currentTermIndex).getIndex();  //seems like there's some problem with how this is being loaded up?
            //gotta set the fraction of all the current words
            for (int i = 0; i < currentLocation.size(); i++){
                System.out.println("fraction being updated. i = " + i);
                allData.get(currentLocation.get(i)).setFraction(i + 1, currentLocation.size());
                allData.get(currentLocation.get(i) + 1).setFraction(i + 1, currentLocation.size());
               notifyItemRangeChanged(currentLocation.get(i), 2, allData.get(currentLocation.get(i)).getFraction());

            }

            if ((previousTermIndex = getTermIndex(lastTerm)) == -1){

            }
            else{
                ArrayList<Integer> previousLocation = locationStore.get(previousTermIndex).getIndex();
                for (int i = 0; i < previousLocation.size(); i++){
                    Info previousTerm = allData.get(previousLocation.get(i));
                    if (previousLocation.get(i) > position) {
                        previousTerm.setNumerator(previousTerm.getNumerator() - 1);
                        allData.get(previousLocation.get(i) + 1).setNumerator(previousTerm.getNumerator());
                    }
                    previousTerm.setDenominator(previousTerm.getDenominator() - 1);
                    allData.get(previousLocation.get(i) + 1).setDenominator(previousTerm.getDenominator());
                    notifyItemRangeChanged(previousLocation.get(i), 2, allData.get(previousLocation.get(i)).getFraction());
                }
            }
              /*  if (oldTracker == null)
                    oldTracker = trackPosition;
            System.out.println("oldtracker size:" + oldTracker.size());
            System.out.println("current size" + tracker); */
        /*    for (Integer i : trackPosition)
                notifyItemRangeChanged(i, 2, (allData.get(i).getFraction())); // this is the issue apparently. maybe i can look into payloads so only the fraction is adjusted
            //  System.out.println("how many identical term" + trackPosition.size());
            //  System.out.println("position is " + position);
                if (oldTracker.size() >= tracker) {  //the issue is oldTracker corrsponds to the previous term, not the current word, since it's updated every time this method is called
                    if (!allData.get(position).getText().equals(allData.get(oldTracker.get(tracker - 1)).getText()))   //if the current text string is different from the text string in the past
                        //above if statement very possibly doesn't work. need to think about oldtracker a bit more
                        for (int i = 0; i < oldTracker.size(); i++) {
                            if (i != tracker) {
                                Info current = allData.get(oldTracker.get(i));
                                Info currentDef = allData.get(oldTracker.get(i + 1));
                                current.setDenominator(current.getDenominator() - 1);
                                currentDef.setDenominator(current.getDenominator());
                                if (i > tracker) {
                                    current.setNumerator(current.getNumerator() - 1);
                                    currentDef.setNumerator(current.getNumerator());
                                }
                                notifyItemRangeChanged(oldTracker.get(i), 2, allData.get(oldTracker.get(i)).getFraction());
                            }
                        }
                }
            oldTracker = trackPosition; */
        }
        }
    }
//}








