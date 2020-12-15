package kz.placer.hotels.report.repository;

import kz.placer.hotels.report.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReportRepository extends JpaRepository<Report, Integer>{

	List<Report> findAllByHotelId (int hotelId);

	List<Report> findAllByRoomId (int roomId);

}
