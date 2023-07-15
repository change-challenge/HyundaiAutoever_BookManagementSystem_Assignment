package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.RentResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.RentRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service.RentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RentApiController {

    private final RentService rentService;
    @PostMapping("/api/rent/{copyId}")
    public ResponseEntity<String> rent(@RequestBody RentRequestDTO dto) {
        try {
            String result = rentService.rent(dto);
            if ("Success".equals(result)) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/return/{copyId}")
    public ResponseEntity<String> returnBook(@RequestBody RentRequestDTO dto) {
        try {
            String result = rentService.returnBook(dto);
            if ("Success".equals(result)) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/extend/{copyId}")
    public ResponseEntity<String> extendBook(@RequestBody RentRequestDTO dto) {
        try {
            String result = rentService.extendBook(dto);
            if ("Success".equals(result)) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/rent/current")
    public ResponseEntity<List<RentResponseDTO>> getCurrentRents(@RequestParam String userEmail) {
        List<RentResponseDTO> rent = rentService.getCurrentRents(userEmail);
        log.info("!!rent!! " + rent);
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }

    @GetMapping("/api/rent/history")
    public ResponseEntity<List<RentResponseDTO>> getHistoryRents(@RequestParam String userEmail) {
        List<RentResponseDTO> rent = rentService.getHistoryRents(userEmail);
        log.info("!!rent!! " + rent);
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }
}
