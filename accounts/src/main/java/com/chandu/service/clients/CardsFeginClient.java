package com.chandu.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chandu.dto.CardsVo;

@FeignClient("cards")
public interface CardsFeginClient {
	
	@GetMapping("/api/fetch")
    public ResponseEntity<CardsVo> fetchCardDetails(@RequestParam("mobileNumber")
                                                               String mobileNumber);

}
