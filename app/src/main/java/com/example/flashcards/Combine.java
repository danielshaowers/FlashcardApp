package com.example.flashcards;


import java.util.ArrayList;


//an arraylist of arraylists of Infos. converts allData from the input adapter into the nested arraylist
//first term is the term, the subsequent are the definitions
public class Combine {
    private ArrayList<ArrayList<Info>> combined;
    private ArrayList<Info> all;


    public Combine(ArrayList<Info> everything) {
        all = everything;
    }

    public ArrayList<Info> getAll() {
        return all;
    }


    public void setCombined(ArrayList<ArrayList<Info>> combined) {
        this.combined = combined;
    }

    public void setAll(ArrayList<Info> all) {
        this.all = all;
    }

    public ArrayList<ArrayList<Info>> getCombined() {
        return combined;
    }

    //runs through two arraylists to their entirety. not too efficient
    //ideally i could just use locationstore and allData to do everything. all i'd have to do then is make locationstore parcelable
    public ArrayList<ArrayList<Info>> combineAll() {

        if (getCombined() == null && getAll() != null) {
            setCombined(new ArrayList<ArrayList<Info>>());
            getCombined().add(new ArrayList<Info>());
            getCombined().get(0).add(getAll().get(0)); //the 0th index of each arraylist of infos is the term
            getCombined().get(0).add(getAll().get(1));
            System.out.println("combined size" + getCombined().get(0).size());
        }

        for (int i = 2; i < getAll().size(); i += 2) { //for every term in allData, runs through the terms in Combined. if equal, adds definition.
            boolean found = false;
            for (int j = 0; !found && j < getCombined().size(); j++) {
                if (found = getAll().get(i).equals(getCombined().get(j).get(0))) //found becomes true when the terms are equal
                    getCombined().get(j).add(getAll().get(i + 1));   //adds the definition if the term already exists
            }
            if (!found) {
                getCombined().add(new ArrayList<Info>());
                getCombined().get(getCombined().size() - 1).add(getAll().get(i));
                getCombined().get(getCombined().size() - 1).add(getAll().get(i + 1));
            }
        }
        return getCombined();
    }

    //combines a single new definition, updates their fraction too (don't mess with the fraction part...right? why am i doing stuff with fractions
    public void combineNew(Info term, Info def) {

        int i = 0;
        while (i < getCombined().size() - 1 && !term.equals(getCombined().get(i))) {
            if (term.equals(getCombined().get(++i))) {
                ArrayList<Info> currentTerm = getCombined().get(i);
                currentTerm.add(def);
                for (int j = 0; j < currentTerm.size(); j++) {
                    currentTerm.get(j).setFraction(j + 1, currentTerm.size());
                }
            }
        }
        if (i == getCombined().size() && !term.equals(getCombined().get(i))) {
            getCombined().add(new ArrayList<Info>());
            ArrayList<Info> newTerm = getCombined().get(getCombined().size() - 1);
            newTerm.add(term);
            newTerm.add(def);
            newTerm.get(0).setFraction(1, 1);
            newTerm.get(1).setFraction(1, 1);
        }
    }

}