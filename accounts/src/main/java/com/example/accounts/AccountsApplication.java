package com.example.accounts;

import com.example.accounts.dto.AccountsContactInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableJpaAuditing(auditorAwareRef = AuditAwareI)
@EnableConfigurationProperties(value = {AccountsContactInfo.class})
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
