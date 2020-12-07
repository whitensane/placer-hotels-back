package kz.placer.hotels.hotels.models;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
@Data
public class HotelModel{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;
	@NotNull
	private String title;
	@NotNull
	private String address;
	@NotNull
	private String phone;
	@NotNull
	private String email;
	@NotNull
	private int stars;
}
