package kz.placer.hotels.rooms;

import kz.placer.hotels.rooms.models.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface RoomRepository extends JpaRepository<RoomModel, Integer>{

	List<RoomModel> findAllByHotelId (int hotelId);
}
