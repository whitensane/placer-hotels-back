package kz.placer.hotels.hotels;

import kz.placer.hotels.hotels.models.HotelModel;
import org.springframework.data.jpa.repository.JpaRepository;

interface HotelRepository extends JpaRepository<HotelModel, Integer> {

}