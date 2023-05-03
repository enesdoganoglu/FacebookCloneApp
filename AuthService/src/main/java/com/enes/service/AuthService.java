package com.enes.service;

import com.enes.dto.request.LoginRequestDto;
import com.enes.dto.request.RegisterRequestDto;
import com.enes.dto.request.UserProfileSaveRequestDto;
import com.enes.exception.AuthException;
import com.enes.exception.ErrorType;
import com.enes.manager.IUserProfileManager;
import com.enes.mapper.IAuthMapper;
import com.enes.repository.IAuthRepository;
import com.enes.repository.entity.Auth;
import com.enes.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IAuthRepository repository;

    private final IUserProfileManager userProfileManager;

    public AuthService(IAuthRepository repository,
                       IUserProfileManager userProfileManager) {
        super(repository);
        this.repository = repository;
        this.userProfileManager = userProfileManager;
    }

    public Optional<Auth> doLogin(LoginRequestDto dto){
        return repository.findOptionalByUsernameAndPassword(
                dto.getUsername(),dto.getPassword()
        );
    }

    public void register(RegisterRequestDto dto) {
        if (repository.existsByUsername(dto.getUsername()))
            throw new AuthException(ErrorType.ERROR_USERNAME);
        /**
         * save methodu kayit işleminden sonra bize auth nesnesini dönmektedir.
         */
        Auth auth = save(IAuthMapper.INSTANCE.toAuth(dto));
        /**
         * Bir kullanıcı uygulamamızda üyelik açtıktan sonra bu kullanıcıya ait
         * bilgiler ile userprofil bilgisinin de oluşturulması gerekiyor. Bunu sağlamak
         * için UserProfile servisine istek atmak üzere FeignClient kullanıyoruz.
         * Kaydetme işlemi için, manager bizden DTO istemektedir. bu nedenle
         * auth için yapılan kayıt bilgilerini dto nun içine koyarak istek atıyoruz.
         */
        UserProfileSaveRequestDto requestDto = UserProfileSaveRequestDto.builder()
                .username(auth.getUsername())
                .email(auth.getEmail())
                .authid(auth.getId())
                .build();
        /**
         * Bu kısımda, DTO içindeki alanlara gerekli olan datalar girilir. FeignClient bizim için
         * verdiğimiz parameterleri iletişime geçeceğimiz UserProfile servisinin save mthoduna
         * jsonObject olarak gönderir ve böylece o save methosunun çalışmasınu sağlar.
         */
         userProfileManager.save(requestDto);

    }
}