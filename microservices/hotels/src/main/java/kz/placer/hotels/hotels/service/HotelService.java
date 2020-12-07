package kz.placer.hotels.hotels.service;


import kz.placer.hotels.hotels.models.HotelModel;

import java.util.List;

public interface HotelService{

	HotelModel save (HotelModel user);

	List<HotelModel> findAll ();

	void delete (long id);

	HotelModel findOne (String username);

	HotelModel findById (Long id);
}
