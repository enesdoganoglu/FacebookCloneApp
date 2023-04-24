package com.enes.service;

import com.enes.dto.request.LoginRequestDto;
import com.enes.dto.request.RegisterRequestDto;
import com.enes.dto.request.UserProfileSaveRequestDto;
import com.enes.exception.AuthException;
import com.enes.exception.ErrorType;
import com.enes.mapper.IAuthMapper;
import com.enes.repository.IAuthRepository;
import com.enes.repository.entity.Auth;
import com.enes.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IAuthRepository repository;

    public AuthService(IAuthRepository repository) {
        super(repository);
        this.repository = repository;

    }

    public boolean doLogin(LoginRequestDto dto){
        Optional<Auth> auth = repository.findOptionalByUsernameAndPassword(
                dto.getUsername(),dto.getPassword()
        );
        if(auth.isEmpty()) return false;
        return true;
    }

    public void register(RegisterRequestDto dto) {
        if (repository.existsByUsername(dto.getUsername()))
            throw new AuthException(ErrorType.ERROR_USERNAME);
        save(IAuthMapper.INSTANCE.toAuth(dto));

    }
}