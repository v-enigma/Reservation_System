package com.Reservation_System;

import java.time.LocalDateTime;
import java.util.List;

import com.Reservation_System.Enum.BookingStatus;


public class Booking {
	
	private final User customer;
	private final List<UserDetails> passenger;
	private final Train train;
	private final LocalDateTime dateOfJourney;
	private final Station source;
	private final Station destination;
	private final long PNR;
	private BookingStatus status = null;
	private final List<Seat>seatsAllocated;
	private final List<Integer> coachIds;
	Booking(Customer customer, List<UserDetails> passenger, Train train, LocalDateTime dJ, Station source, Station destination ,List<Seat> seatsAllocated, List<Integer>coachIds){
		this.customer = customer;
		this.passenger = passenger;
		this.train =train;
		this.dateOfJourney = dJ;
		this.source = source;
		this.destination =destination;
		this.PNR = BookingDB.getInstance().generateId();
		this.seatsAllocated = seatsAllocated;
		this.coachIds = coachIds;
	}
	public long getPNR() {
		return PNR;
	}
	public User getCustomer() {
		return customer;
	}
	public LocalDateTime getDateOfJourney() {
		return dateOfJourney;
	}
	public Station getSource() {
		return source;
	}
	public Station getDestination() {
		return destination;
	}
	public Train getTrain() {
		return train;
	}
	public BookingStatus getStatus() {
		return status;
	}
	public void setStatus(BookingStatus status) {
		this.status = status;
	}
	public List<UserDetails> getPassenger(){
		return passenger;
	}
	public List<Seat> getAllocatedSeats(){
		return seatsAllocated;
	}
	public List<Integer>getCoachIds() {
		return coachIds;
	}
	@Override
	public String toString() {
		String details="";
		int i=0;
		String  topLevel ="CoachNo    SeatNo      Name";
		details+=topLevel;
		for(UserDetails userdetails: passenger) {
			String temp = coachIds.get(i)+"          " +seatsAllocated.get(i)+"          "+ userdetails.getName()+"\n";
			details+=temp;
		}
		String ticket = " Source: "+ this.source +" Destination : "+ this.destination + "PNR " + this.PNR+" \\n " ;
		ticket+=details;
		return ticket;
	}

}
