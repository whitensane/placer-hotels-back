package kz.placer.hotels.auth.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "auth")
public class AuthModel{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NonNull
	private String name;

	@NonNull
	private String lastname;

	@NonNull
	@Column(unique = true)
	private String username;

	@NonNull
	private String password;

	@NonNull
	private String email;

	@NonNull
	private String phone;

	private String hotelToken;

	private String cateringToken;
}