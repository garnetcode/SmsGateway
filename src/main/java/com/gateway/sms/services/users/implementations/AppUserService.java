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
        AppUser user = appUserRepository.findFirstByUsername(username);
        if(user == null){
            throw  new UsernameNotFoundException("User not found!");
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
        Optional<AppUser> user = Optional.ofNullable(appUserRepository.findFirstByUsername(appUser.username()));
        if(user.isPresent()){
            response.failed();
            response.setMessage("Username already exists");
            response.setData(null);
        }else {
            AppUser newUser = userMapper.userDtoToUser(appUser);
            String encodedPassword = bCryptPasswordEncoder.encode(appUser.password());
            newUser.setPassword(encodedPassword);
            appUserRepository.save(newUser);
            addRoleToUser(newUser.getUsername(), "ROLE_ADMIN");
            response.isSuccessful();
            response.setStatus(HttpStatus.CREATED);
            response.setData((new GetUserDto(newUser.getId(), appUser.name(), appUser.username())));
        }
        return response;

    }

    @Override
    public void addRoleToUser(String username, String roleName){
        AppUser user = appUserRepository.findFirstByUsername(username);
        Role role = roleRepository.findFirstByName(roleName);
        user.getRoles().add(role);
        appUserRepository.save(user);
    }

    @Override
    public ApiResponse createCompany(AppUser appUser, CompanyDto companyDto){
        Optional<Company> company = Optional.ofNullable(companyRepository.findByAdmin(appUser));
        if(company.isPresent()) {
            response.failed();
            response.setMessage("You already have a profile!");

        }else{
            companyDto.setAdmin(appUser);
            companyDto.setIsActive(false);
            companyDto.setRunningBalance(0.00);

            companyRepository.save(companyMapper.companyDtoToCompany(companyDto));

            companyDto.getAdmin().setPassword(null);
            response.isSuccessful();
            response.setData(companyDto);
        }
        return response;
    }


    public ApiResponse getCompanyProfile(AppUser admin){
        Optional<Company> company = Optional.ofNullable(companyRepository.findByAdmin(admin));
        return company.map(value->{
            CompanyDto companyDto = companyMapper.companyToCompanyDto(value);
            companyDto.getAdmin().setPassword(null);
            response.isSuccessful();
            response.setData(companyDto);
            return response;
        }).orElseGet(()->{
            response.failed();
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("No profile found");
            return response;
        });

    }


    @Override
    public ApiResponse updateCompanyProfile(String companyName, Boolean isActive) {
        Company company = companyRepository.findFirstByCompanyNameEquals(companyName);
        if(company !=null){
            CompanyDto companyDto = companyMapper.companyToCompanyDto(company);
            companyDto.setIsActive(isActive);

            companyMapper.updateCompanyFromCompanyDto(companyDto, company);

            response.isSuccessful();
            response.setStatus(HttpStatus.OK);
            response.setData(companyDto);
        }else {
            response.failed();
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ApiResponse updateCompanyBalance(String companyName, Double amount) {
        Company company = companyRepository.findFirstByCompanyNameEquals(companyName);
        if(company !=null){
            CompanyDto companyDto = companyMapper.companyToCompanyDto(company);
            companyDto.setRunningBalance(company.getRunningBalance()+amount);
            companyMapper.updateCompanyFromCompanyDto(companyDto, company);
            response.isSuccessful();
            response.setMessage("Successfully funded "+companyName+" with "+amount);
            response.setStatus(HttpStatus.OK);
            response.setData(companyDto);
        }else {
            response.failed();
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
