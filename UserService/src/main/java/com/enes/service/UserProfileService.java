package com.enes.service;

import com.enes.dto.request.UserProfileSaveRequestDto;
import com.enes.dto.request.UserProfileUpdateRequestDto;
import com.enes.exception.ErrorType;
import com.enes.exception.UserException;
import com.enes.mapper.IUserProfileMapper;
import com.enes.repository.IUserProfileRepository;
import com.enes.repository.entity.UserProfile;
import com.enes.utility.JwtTokenManager;
import com.enes.utility.ServiceManager;
import com.enes.utility.TokenCreator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    private final IUserProfileRepository repository;
    private final JwtTokenManager jwtTokenManager;
    public UserProfileService(IUserProfileRepository repository,
                              JwtTokenManager jwtTokenManager) {
        super(repository);
        this.repository=repository;
        this.jwtTokenManager=jwtTokenManager;
    }

    public void save(UserProfileSaveRequestDto dto){
        save(IUserProfileMapper.INSTANCE.toUserProfile(dto));
    }


    public void update(UserProfileUpdateRequestDto dto){
        Optional<Long> authid = jwtTokenManager.getIdFromToken(dto.getToken());
        if(authid.isEmpty())
            throw new UserException(ErrorType.ERROR_INVALID_TOKEN);
        Optional<UserProfile> userProfile = repository.findOptionalByAuthid(authid.get());
        if(userProfile.isPresent()){
            UserProfile profile = userProfile.get();
            profile.setAddress(dto.getAddress());
            profile.setAvatar(dto.getAvatar());
            profile.setGender(dto.getGender());
            profile.setName(dto.getName());
            profile.setPhone(dto.getPhone());
            profile.setSurname(dto.getSurname());
            update(profile);
        }
    }

}
