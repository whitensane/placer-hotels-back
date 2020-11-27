package kz.placer.hotels.hotels;

import kz.placer.hotels.hotels.models.HotelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface HotelRepository extends JpaRepository<HotelModel, Integer> {
	@Query("SELECT u FROM HotelModel u WHERE u.id = :id")
	HotelModel findByIdFetchParent(@Param("id") Integer id);
}