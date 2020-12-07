package kz.placer.hotels.hotels.dao;

import kz.placer.hotels.hotels.models.HotelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HotelRepository extends JpaRepository<HotelModel, Long>{

	HotelModel findByTitle (String name);

}
