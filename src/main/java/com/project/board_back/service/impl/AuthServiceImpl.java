package com.project.board_back.service.impl;

import com.project.board_back.dto.ResponseDto;
import com.project.board_back.dto.auth.request.UserSignInRequestDto;
import com.project.board_back.dto.auth.request.UserSignUpRequestDto;
import com.project.board_back.dto.auth.response.UserSignInResponseDto;
import com.project.board_back.dto.auth.response.UserSignUpResponseDto;
import com.project.board_back.repository.UserRepository;
import com.project.board_back.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public ResponseDto<UserSignUpResponseDto> signup(UserSignUpRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<UserSignInResponseDto> login(UserSignInRequestDto dto) {
        return null;
    }
}