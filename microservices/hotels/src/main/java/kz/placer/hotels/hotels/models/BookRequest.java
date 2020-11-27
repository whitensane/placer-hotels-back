package kz.placer.hotels.hotels.models;

import lombok.Getter;
import lombok.Setter;

public class BookRequest {

	private String userId;
	public BookRequest(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "BookRequest{" +
				"userId='" + userId +
				'}';
	}
}