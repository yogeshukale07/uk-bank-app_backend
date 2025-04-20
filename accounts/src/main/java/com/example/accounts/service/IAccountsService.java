package com.example.accounts.service;

import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Accounts;

import java.util.Optional;

public interface IAccountsService {
    void createAccount(CustomerDto customerDto);
    CustomerDto getByMobileNumber(String mobileNumber);
    boolean updateAccount(CustomerDto customerDto);
    boolean deleteAccount(String mobileNumber);
}
