package com.example.accounts.service.impl;

import com.example.accounts.constants.AccountConstants;
import com.example.accounts.dto.AccountsDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Accounts;
import com.example.accounts.entity.Customer;
import com.example.accounts.exceptions.CustomerAlreadyExistException;
import com.example.accounts.exceptions.ResourceNotFoundException;
import com.example.accounts.mapper.AccountsMapper;
import com.example.accounts.mapper.CustomerMapper;
import com.example.accounts.repository.AccountsRepository;
import com.example.accounts.repository.CustomerRepository;
import com.example.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    //@Autowired
    AccountsRepository accountsRepository;

    //@Autowired
    CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (customerOptional.isPresent()) {
            throw new CustomerAlreadyExistException(String.format("Customer mobile Number %s already exist in DB", customerDto.getMobileNumber()));
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("AnonymusUser");
        Customer savedCustomver = customerRepository.save(customer);
        accountsRepository.save(createNewAccounts(savedCustomver));
    }
    private Accounts createNewAccounts(Customer customer) {
        Accounts newAccouts = new Accounts();
        newAccouts.setCustomerId(customer.getCustomerId());
        long accountNumber = 1000000000L + new Random().nextInt(900000000);
        newAccouts.setAccountNumber(accountNumber);
        newAccouts.setAccountType(AccountConstants.SAVINGS);
        newAccouts.setBranchAddress(AccountConstants.ADDRESS);
        newAccouts.setCreatedAt(LocalDateTime.now());
        newAccouts.setCreatedBy("AnonymusUser");

        return newAccouts;
    }

    @Override
    public CustomerDto getByMobileNumber(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()-> new ResourceNotFoundException("Customer", "MobileNumber", mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(()-> new ResourceNotFoundException("Accounts", "CustomerId", customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));


        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto!= null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(()-> new ResourceNotFoundException("Account", "AccountId", accountsDto.getAccountNumber().toString()));
            AccountsMapper.mapToAccounts(accountsDto, accounts);


            Customer customer = customerRepository.findByMobileNumber(accounts.getMobileNumber()).orElseThrow(()-> new ResourceNotFoundException("Customer", "MobileNumber", customerDto.getMobileNumber()));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            accountsRepository.save(accounts);
            return true;
        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()-> new ResourceNotFoundException("Customer", "Mobile", mobileNumber));
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }

}
