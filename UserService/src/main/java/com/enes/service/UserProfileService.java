package com.enes.service;

import com.enes.dto.request.UserProfileSaveRequestDto;
import com.enes.dto.request.UserProfileUpdateRequestDto;
import com.enes.exception.ErrorType;
import com.enes.exception.UserException;
import com.enes.mapper.IUserProfileMapper;
import com.enes.repository.IUserProfileRepository;
import com.enes.repository.entity.UserProfile;
import com.enes.utility.ServiceManager;
import com.enes.utility.TokenCreator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    private final IUserProfileRepository repository;

    private final TokenCreator tokenCreator;

    public UserProfileService(IUserProfileRepository repository, TokenCreator tokenCreator) {
        super(repository);
        this.repository=repository;

        this.tokenCreator = tokenCreator;
    }

    public void save(UserProfileSaveRequestDto dto){
        save(IUserProfileMapper.INSTANCE.toUserProfile(dto));
    }


    public void update(UserProfileUpdateRequestDto dto){
        Optional<Long> authid = tokenCreator.getAuthId(dto.getToken());
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
