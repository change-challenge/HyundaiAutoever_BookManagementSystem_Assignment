package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminApiController {

//    private final AdminService adminService;

    @GetMapping("/user")
    public ResponseEntity<String> readUsers() {
        return new ResponseEntity<String>("WHAT'S UP", HttpStatus.OK);
    }
}
