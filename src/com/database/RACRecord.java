package com.database;

 final class RACRecord {
    private Long pnr;
    private int passengerIndex;
    private int seatClass;
    private int currentNumber;
    RACRecord(long pnr, int passengerIndex, int seatClass){
        this.pnr = pnr;
        this.passengerIndex = passengerIndex;
        this.seatClass = seatClass;
    }
     int getPassengerIndex(){
        return passengerIndex;
    }
     long getPNR(){
        return pnr;
    }
    int getSeatClass(){return seatClass;}
}
