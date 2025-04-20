package com.example.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Customer", description = "Schema to hold customer and Account information")
public class CustomerDto {
    @NotEmpty
    @Size(min = 5, max = 30, message = "The length of customer between 5 to 30")
    @Schema(description = "Schema to hold customer name")
    private String name;

    @NotEmpty
    @Email(message = "EmailId should be a valid Value")
    @Schema(description = "Schema to hold Customer email")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 Digits")
    @Schema(description = "Schema to hold customer Mobile number")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
