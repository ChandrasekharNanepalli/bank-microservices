package com.chandu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table
@Data @AllArgsConstructor @NoArgsConstructor
public class Accounts extends BaseEntity {
	
	private Long customerId;
	@Id
	private Long accountsNumber;
	private String accountType;
	private String branchAddress;

}
