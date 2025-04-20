package com.example.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "Accounts", description = "Schema to hold Account info")
public class AccountsDto {
    @NotEmpty(message = "Account number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 Digits")
    @Schema(description = "Schema to add Account number")
    private Long accountNumber;

    @NotEmpty(message = "Accout type can not be null or Empty")
    @Schema(description = "Scema to add Account Type")
    private String accountType;

    @NotEmpty(message = "Accout type can not be null or Empty")
    @Schema(description = "Schema to add mobile number")
    private String mobileNumber;

    @NotEmpty(message = "Accout type can not be null or Empty")
    @Schema(description = "Schema to add branch Address")
    private String branchAddress;

}
