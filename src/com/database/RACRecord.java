package com.database;

 final class RACRecord {
    private Long pnr;
    private int passengerIndex;
   protected RACRecord(long pnr, int passengerIndex){
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
