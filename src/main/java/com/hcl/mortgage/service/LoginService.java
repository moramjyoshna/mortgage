package com.hcl.mortgage.service;

import com.hcl.mortgage.dto.LoginRequestDto;
import com.hcl.mortgage.dto.LoginResponseDto;

public interface LoginService {

	LoginResponseDto login(LoginRequestDto loginRequestDto);
}
