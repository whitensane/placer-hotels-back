package kz.placer.hotels.report.models;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data
public class Report{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	@NotNull
	private int userId;

	@NotNull
	private int hotelId;

	private int roomId;

	private int timestamp;

	@NotNull
	private String title;

	@NotNull
	private String description;
}
