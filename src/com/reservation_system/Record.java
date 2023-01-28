package com.reservation_system;

 public final class Record {
    private final Long pnr;
    private final int passengerIndex;
    private final int seatClass;
    private int currentNumber;
    public Record(long pnr,int passengerIndex, int seatClass){
        this.currentNumber =0;
        this.pnr = pnr;
        this.passengerIndex = passengerIndex;
        this.seatClass = seatClass;
    }
    public Record(int currentNumber,long pnr, int passengerIndex, int seatClass){
        this.currentNumber = currentNumber;
        this.pnr = pnr;
        this.passengerIndex = passengerIndex;
        this.seatClass = seatClass;
    }
    public int getPassengerIndex(){
        return passengerIndex;
    }
   public   long getPNR(){
        return pnr;
    }
   public  int getSeatClass(){return seatClass;}
   public int getcurrentNumber(){
        return currentNumber;
     }
    public  void setCurrentNumber(int currentNumber){
        this.currentNumber = currentNumber;
    }
}
