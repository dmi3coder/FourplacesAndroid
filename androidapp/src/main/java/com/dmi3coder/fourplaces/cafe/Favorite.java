package com.dmi3coder.fourplaces.cafe;

import java.util.ArrayList;

import com.dmi3coder.fourplaces.menu.Meal;


public class Favorite   {

    private Cafe favoriteCafe;
    private ArrayList<Meal> FIRST, SECOND, DRINK, SNACKS, BAKERY, CANDY, SEA, ETC;
    private int cafeId;

    public Favorite(Cafe favoriteCafe, int cafeId, ArrayList<Meal>... meals) {
        FIRST = meals[0];
        SECOND = meals[1];
        DRINK = meals[2];
        SNACKS = meals[3];
        BAKERY = meals[4];
        CANDY = meals[5];
        SEA = meals[6];
        ETC = meals[7];
        this.favoriteCafe = favoriteCafe;
        this.cafeId = cafeId;
    }


    public int getCafeId() {
        return cafeId;
    }

    public void setCafeId(int cafeId) {
        this.cafeId = cafeId;
    }

    public ArrayList<Meal> getFIRST() {
        return FIRST;
    }

    public void setFIRST(ArrayList<Meal> FIRST) {
        this.FIRST = FIRST;
    }

    public ArrayList<Meal> getSECOND() {
        return SECOND;
    }

    public void setSECOND(ArrayList<Meal> SECOND) {
        this.SECOND = SECOND;
    }

    public ArrayList<Meal> getDRINK() {
        return DRINK;
    }

    public void setDRINK(ArrayList<Meal> DRINK) {
        this.DRINK = DRINK;
    }

    public ArrayList<Meal> getSNACKS() {
        return SNACKS;
    }

    public void setSNACKS(ArrayList<Meal> SNACKS) {
        this.SNACKS = SNACKS;
    }

    public ArrayList<Meal> getBAKERY() {
        return BAKERY;
    }

    public void setBAKERY(ArrayList<Meal> BAKERY) {
        this.BAKERY = BAKERY;
    }

    public ArrayList<Meal> getCANDY() {
        return CANDY;
    }

    public void setCANDY(ArrayList<Meal> CANDY) {
        this.CANDY = CANDY;
    }

    public ArrayList<Meal> getSEA() {
        return SEA;
    }

    public void setSEA(ArrayList<Meal> SEA) {
        this.SEA = SEA;
    }

    public ArrayList<Meal> getETC() {
        return ETC;
    }

    public void setETC(ArrayList<Meal> ETC) {
        this.ETC = ETC;
    }

    public Cafe getFavoriteCafe() {
        return favoriteCafe;
    }

    public void setFavoriteCafe(Cafe favoriteCafe) {
        this.favoriteCafe = favoriteCafe;
    }
}
