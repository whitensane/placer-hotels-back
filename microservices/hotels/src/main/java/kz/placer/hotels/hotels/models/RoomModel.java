package kz.placer.hotels.hotels.models;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;


@Data
public class RoomModel{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	@NotNull
	private int hotelId;

	@NotNull
	private int number;

	@NotNull
	private int floor;

	@NotNull
	private int sleepingPlaces;

	@NotNull
	private String description;

	@NotNull
	private int rooms;

}
