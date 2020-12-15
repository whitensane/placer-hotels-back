package kz.placer.hotels.rooms.models;

import lombok.Data;

@Data
public class Report{

	private Integer id;
	private int userId;
	private int hotelId;
	private int roomId;
	private int timestamp;
	private String title;
	private String description;
}
