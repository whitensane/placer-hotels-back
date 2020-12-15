package kz.placer.hotels.gateway.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Role{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String name;

	@Column
	private String description;


}
