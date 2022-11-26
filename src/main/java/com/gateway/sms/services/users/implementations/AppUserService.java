package com.gateway.sms.services.users.implementations;

import com.gateway.sms.domain.dtos.users.GetUserDto;
import com.gateway.sms.models.AppUser;
import com.gateway.sms.models.Role;
import com.gateway.sms.domain.repositories.AppUserRepository;
import com.gateway.sms.domain.repositories.RoleRepository;
import com.gateway.sms.domain.dtos.users.PostUserDto;
import com.gateway.sms.domain.response.Response;
import com.gateway.sms.services.users.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;


@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppUserService implements UserService, UserDetailsService {

    private final RoleRepository roleRepository;
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final Response response;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username);
        if(user == null){
            throw  new UsernameNotFoundException("User not found!");
        }else {
            log.info("User found!");
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public void saveRole(Role role){
        roleRepository.save(role);
    }

    @Override
    public Response signUpUser(PostUserDto appUser) {
        AppUser user = appUserRepository.findByUsername(appUser.username());
        if(user != null){
            response.setSuccess(false);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Username already exists");
            response.setData(null);
        }else {
            AppUser newUser = new AppUser(appUser.name(), appUser.username(), appUser.password());
            String encodedPassword = bCryptPasswordEncoder.encode(appUser.password());
            newUser.setPassword(encodedPassword);
            appUserRepository.save(newUser);
            addRoleToUser(newUser.getUsername(), "ROLE_USER");
            response.setSuccess(true);
            response.setStatus(HttpStatus.CREATED);
            response.setMessage("User Created!");
            response.setData((new GetUserDto(newUser.getId(), appUser.name(), appUser.username())));
        }
        return response;

    }

    @Override
    public void addRoleToUser(String username, String roleName){
        AppUser user = appUserRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }



}
