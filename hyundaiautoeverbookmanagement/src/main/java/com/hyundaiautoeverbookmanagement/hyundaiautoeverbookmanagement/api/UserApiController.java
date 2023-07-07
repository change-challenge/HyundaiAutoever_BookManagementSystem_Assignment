package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.UserDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.User;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.UserRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.WishBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserApiController {


    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결!
    private UserRepository userRepository;

//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserApiController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @PostMapping("/api/user/create")
    public String createUser(@RequestBody UserDTO form) {
        log.info(form.toString());
        // 1. Dto를 변환! Entity!
        User user = form.toEntity();

        // 2. Repository에게 Entity를 DB안에 저장하게 함!
        User saved = userRepository.save(user);
        log.info(saved.toString());
        return "success";
    }

}
