package com.gateway.sms.services.users.implementations;

import com.gateway.sms.domain.dtos.users.CompanyDto;
import com.gateway.sms.domain.dtos.users.GetUserDto;
import com.gateway.sms.services.sms.implementations.mappers.users.CompanyMapper;
import com.gateway.sms.services.sms.implementations.mappers.users.UserMapper;
import com.gateway.sms.domain.repositories.CompanyRepository;
import com.gateway.sms.models.AppUser;
import com.gateway.sms.models.Company;
import com.gateway.sms.models.Role;
import com.gateway.sms.domain.repositories.AppUserRepository;
import com.gateway.sms.domain.repositories.RoleRepository;
import com.gateway.sms.domain.dtos.users.PostUserDto;
import com.gateway.sms.domain.response.ApiResponse;
import com.gateway.sms.services.users.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;


@Service @Transactional @Slf4j
public class AppUserService implements UserService, UserDetailsService {

    private final RoleRepository roleRepository;
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ApiResponse response;

    private final UserMapper userMapper;

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    @Autowired
    public AppUserService(RoleRepository roleRepository,
                          AppUserRepository appUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          ApiResponse response, UserMapper userMapper,
                          CompanyRepository companyRepository,
                          CompanyMapper companyMapper) {

        this.roleRepository = roleRepository;
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.response = response;
        this.userMapper = userMapper;
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username);
        if(user == null){
            throw  new UsernameNotFoundException("User not found!");
        }else {
            log.info("User found!");
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public void saveRole(Role role){
        roleRepository.save(role);
    }

    @Override
    public ApiResponse signUpUser(PostUserDto appUser) {
        Optional<AppUser> user = Optional.ofNullable(appUserRepository.findByUsername(appUser.username()));
        if(user.isPresent()){
            response.setSuccess(false);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Username already exists");
            response.setData(null);
        }else {
            AppUser newUser = userMapper.userDtoToUser(appUser);
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
        Optional<AppUser> user = Optional.ofNullable(appUserRepository.findByUsername(username));
        Role role = roleRepository.findByName(roleName);
        user.map(appUser ->appUser.getRoles().add(role));
    }

    @Override
    public ApiResponse createCompany(AppUser appUser, CompanyDto companyDto){

        Optional<Company> company = Optional.ofNullable(companyRepository.findByAdmin(appUser));
        return company.map(value->{
            response.setSuccess(false);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("You already have a profile!");
            response.setData(null);
            return response;
        }).orElseGet(()->{
            companyDto.setAdmin(appUser);
            companyDto.setIsActive(false);
            companyDto.setRunningBalance(0.00);
            companyRepository.save(companyMapper.companyDtoToCompany(companyDto));
            companyDto.getAdmin().setPassword(null);
            response.setSuccess(true);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Profile Created!");
            response.setData(companyDto);
            return response;
        });
    }


    public ApiResponse getCompanyProfile(AppUser admin){
        Optional<Company> company = Optional.ofNullable(companyRepository.findByAdmin(admin));
        return company.map(value->{
            response.setSuccess(true);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Success!");
            CompanyDto companyDto = companyMapper.companyToCompanyDto(value);
            companyDto.getAdmin().setPassword(null);
            response.setData(companyDto);
            return response;
        }).orElseGet(()->{
            response.setSuccess(false);
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("No profile found");
            response.setData(null);
            return response;
        });

    }





}
