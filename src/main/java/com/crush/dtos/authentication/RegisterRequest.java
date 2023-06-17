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

  @ValidPassword
  @NotEmpty(message = "Password is required")
  private String password;

  @ValidPassword
  @NotEmpty(message = "Confirm Password is required")
  private String confirmPassword;

}
