package kz.placer.hotels.hotels.service.impl;

import kz.placer.hotels.hotels.dao.HotelRepository;
import kz.placer.hotels.hotels.models.HotelModel;
import kz.placer.hotels.hotels.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class HotelServiceImpl implements HotelService{

	@Autowired
	private HotelRepository hotelRepository;


	public List<HotelModel> findAll (){
		List<HotelModel> list = new ArrayList<>();
		hotelRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete (long id){
		hotelRepository.deleteById(id);
	}

	@Override
	public HotelModel findOne (String name){
		return hotelRepository.findByTitle(name);
	}

	@Override
	public HotelModel findById (Long id){
		return hotelRepository.findById(id).get();
	}

	@Override
	public HotelModel save (HotelModel hotelModel){
		HotelModel hotel = new HotelModel();
		hotel.setStars(hotelModel.getStars());
		hotel.setTitle(hotelModel.getTitle());
		hotel.setAddress(hotelModel.getAddress());
		hotel.setPhone(hotelModel.getPhone());
		hotel.setEmail(hotelModel.getEmail());
		hotelRepository.save(hotel);
		return hotelModel;
	}
}
