package com.reservation_system;

 public final class Record {
    private Long pnr;
    private int passengerIndex;
    private int seatClass;
    private int currentNumber;
    public Record(long pnr, int passengerIndex, int seatClass){
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
