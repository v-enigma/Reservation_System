package com.Reservation_System;
import java.time.LocalDate;
import java.util.List;
import com.Reservation_System.Enum.BookingStatus;


public class Booking {
	
	private final User customer;
	private final List<UserDetails> passenger;
	private final Train train;
	private final LocalDate journeyDate;
	private final Station source;
	private final Station destination;
	private final long PNR;
	private List<BookingStatus> status = null;
	private final List<Seat>allocatedSeats;
	private final List<String> coachIds;
	Booking(User customer, List<UserDetails> passenger, Train train, LocalDate journeyDate, Station source, Station destination, Long PNR,List<Seat> allocatedSeats,List<BookingStatus>status, List<String>coachIds){
		this.customer = customer;
		this.passenger = passenger;
		this.train =train;
		this.journeyDate = journeyDate;
		this.source = source;
		this.destination =destination;
		this.PNR = PNR;
		this.allocatedSeats = allocatedSeats;
		this.coachIds = coachIds;
	}
	public long getPNR() {
		return PNR;
	}
	public User getCustomer() {
		return customer;
	}
	public LocalDate getjourneyDate() {
		return journeyDate;
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
	public List<BookingStatus> getStatus() {
		return status;
	}
	public void setStatus(List<BookingStatus>  status) {
		this.status = status;
	}
	public List<UserDetails> getPassenger(){
		return passenger;
	}
	public List<Seat> getAllocatedSeats(){
		return allocatedSeats;
	}
	public List<String>getCoachIds() {
		return coachIds;
	}
	@Override
	public String toString() {
		String details;
		int i=0;
		String  topLevel ="CoachNo    SeatNo      Name\n";
		details=topLevel;
		for(UserDetails userdetails: passenger) {
			String temp = coachIds.get(i)+"          " +allocatedSeats.get(i).getId()+"          "+ userdetails.getName()+"\n";
			details+=temp;
			i++;
		}
		
		String ticket = "Source: "+ this.source +" Destination : "+ this.destination + "PNR " + this.PNR+" \n " ;
		ticket+=details;
		return ticket;
	}
	

}
