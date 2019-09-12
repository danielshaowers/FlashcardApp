package com.example.flashcards;

import java.util.ArrayList;

public class LocationStore {
    private ArrayList<Integer> index = new ArrayList<>();
    private Info info;

    public LocationStore(Info in, int ind){
        info = in;
        index.add(ind);
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public ArrayList<Integer> getIndex() {
        return index;
    }

    public void setIndex(ArrayList<Integer> index) {
        this.index = index;
    }
}
