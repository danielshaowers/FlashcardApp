package com.example.flashcards;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.util.ArrayList;
//each Data object contains all the info for the term, and all the info for the definitions associated with that term
public class Data{
   private ArrayList<Info> defList;
   private Info termInfo;

    public Data(Info term, ArrayList<Info> information){
      defList = information;
      termInfo = term;
    }

    public Data(Info term){
        termInfo = term;
        defList = new ArrayList<Info>();
    }

    //gives the number of definitions
    public int getCount() {
        return defList.size();
    }

    public ArrayList<Info> getDef(){
        return defList;
    }

    public Info getTerm(){
        return termInfo;
    }

    public String getTermText(){return getTerm().getText();}

    public BitmapDrawable getTermImage(){return getTerm().getImage(); }

    public MediaStore.Audio getTermAudio(){return getTerm().getAudio();}

    public void setTermText(String s) {getTerm().setText(s);}

    public void setTermImage(BitmapDrawable iv) {getTerm().setImage(iv);}

    public void setTermAudio(MediaStore.Audio a) {getTerm().setAudio(a);}


}
