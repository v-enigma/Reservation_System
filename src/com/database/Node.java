package com.database;

class Node {
    private Long pnr;
    private int passengerIndex;
    Node(long pnr, int passengerIndex){
        this.pnr = pnr;
        this.passengerIndex = passengerIndex;
    }
     int getPassengerIndex(){
        return passengerIndex;
    }
     long getPNR(){
        return pnr;
    }
}
