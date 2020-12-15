package kz.placer.hotels.report.controllers;

import kz.placer.hotels.report.models.Report;
import kz.placer.hotels.report.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;

@RestController
@RequestMapping("/report")
public class ReportController{

	@Autowired
	private ReportRepository reportRepository;


	@GetMapping("/id")
	public ResponseEntity<?> getReport (@RequestParam int id){
		try {
			return new ResponseEntity<>(reportRepository.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<?> getReports(){
		try {
			return new ResponseEntity<>(reportRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/hotelId")
	public ResponseEntity<?> getReportOfHotel (@RequestParam int id){
		try {
			return new ResponseEntity<>(reportRepository.findAllByHotelId(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/roomId")
	public ResponseEntity<?> getReportOfRoom (@RequestParam int roomId){
		try {
			return new ResponseEntity<>(reportRepository.findAllByRoomId(roomId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@DeleteMapping
	public ResponseEntity<?> deleteReport(@RequestBody Report request){
		try {
			reportRepository.delete(request);
			return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/hotel")
	public ResponseEntity<?> createReport (@Valid @RequestBody Report request){
		try {
			Report report = new Report();

			report.setDescription(request.getDescription());
			report.setTitle(request.getTitle());
			report.setUserId(request.getUserId());
			report.setRoomId(request.getRoomId());
			report.setHotelId(request.getHotelId());
			Instant instant = Instant.now();
			Long timeStampMillis = instant.toEpochMilli();
			report.setTimestamp(timeStampMillis.intValue());
			reportRepository.save(report);

			return new ResponseEntity<>(report, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
