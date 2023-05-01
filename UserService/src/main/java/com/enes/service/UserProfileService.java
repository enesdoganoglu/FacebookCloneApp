package com.enes.service;

import com.enes.dto.request.UserProfileSaveRequestDto;
import com.enes.mapper.IUserProfileMapper;
import com.enes.repository.IUserProfileRepository;
import com.enes.repository.entity.UserProfile;
import com.enes.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    private final IUserProfileRepository repository;

    public UserProfileService(IUserProfileRepository repository) {
        super(repository);
        this.repository=repository;

    }

    public void save(UserProfileSaveRequestDto dto){
        save(IUserProfileMapper.INSTANCE.toUserProfile(dto));
    }

}
