package kz.placer.hotels.hotels.models;


import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class RoomFeedback{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	@NotNull
	private int hotelId;

	@NotNull
	private int roomId;

	@NotNull
	private String title;

	@NotNull
	private String description;

	@NotNull
	private int rating;

	@NotNull
	private int userId;

}
