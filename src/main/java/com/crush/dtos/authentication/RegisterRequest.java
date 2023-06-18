package com.crush.dtos.authentication;

import com.crush.service.validation.ValidPassword;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotEmpty(message = "Phone number is required")
  private String phoneNumber;

  @NotEmpty(message = "Password is required")
  @ValidPassword
  private String password;

  @NotEmpty(message = "Confirm Password is required")
  @ValidPassword
  private String confirmPassword;

}
