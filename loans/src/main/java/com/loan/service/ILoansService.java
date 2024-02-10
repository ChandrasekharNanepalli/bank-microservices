package com.loan.service;

import com.loan.dto.LoansVo;

public interface ILoansService {

    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    void createLoan(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input mobile Number
     *  @return Loan Details based on a given mobileNumber
     */
    LoansVo fetchLoan(String mobileNumber);

    /**
     *
     * @param loansDto - LoansVo Object
     * @return boolean indicating if the update of card details is successful or not
     */
    boolean updateLoan(LoansVo loansVo);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of loan details is successful or not
     */
    boolean deleteLoan(String mobileNumber);

}