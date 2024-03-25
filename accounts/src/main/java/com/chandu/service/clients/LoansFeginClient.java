package com.chandu.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chandu.dto.LoansVo;

@FeignClient("loans")
public interface LoansFeginClient {
	
	@GetMapping("/api/fetch")
	public ResponseEntity<LoansVo> fetchLoanDetails(@RequestParam("mobileNumber")
    																	String mobileNumber); 

}
