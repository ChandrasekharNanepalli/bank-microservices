package com.chandu.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component("aduit")
public class AduitAwareImpl  implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		System.out.println("hiii1");
		return Optional.of("Accounts_Ms");
	}

}
