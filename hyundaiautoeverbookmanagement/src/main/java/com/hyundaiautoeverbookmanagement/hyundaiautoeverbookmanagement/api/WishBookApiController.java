package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishBookDto;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.WishBook;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.WishBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WishBookApiController {

    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결!
    private WishBookRepository wishBookRepository;

    @PostMapping("/api/wishbook/create")
    public String createWishBook(@RequestBody WishBookDto form) {
        System.out.println(form.toString());

        // 1. Dto를 변환! Entity!
        WishBook wishbook = form.toEntity();

        // 2. Repository에게 Entity를 DB안에 저장하게 함!
        WishBook saved = wishBookRepository.save(wishbook);
        System.out.println(saved.toString());
        return "Success";
    }

}
