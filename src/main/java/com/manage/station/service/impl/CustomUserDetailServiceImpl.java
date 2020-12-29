package com.manage.station.service.impl;

import com.manage.station.entity.RoleEntity;
import com.manage.station.entity.UserEntity;
import com.manage.station.repository.RoleRepository;
import com.manage.station.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CustomUserDetailServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
        List<RoleEntity> listActiveRoleEntities = roleRepository.findAllRoleByUser(userEntity.getId());
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (RoleEntity roleEntity : listActiveRoleEntities) {
            authorities.add(new SimpleGrantedAuthority(roleEntity.getCode()));
        }
        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }
}
