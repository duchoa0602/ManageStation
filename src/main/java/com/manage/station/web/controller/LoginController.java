package com.manage.station.web.controller;

import com.manage.station.entity.UserEntity;
import com.manage.station.service.UserService;
import com.manage.station.service.impl.UserAuthenticatorServiceImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping
public class LoginController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final UserAuthenticatorServiceImpl userAuthenticatorService;

    public LoginController(UserService userService,
                           UserDetailsService userDetailsService,
                           UserAuthenticatorServiceImpl userAuthenticatorService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.userAuthenticatorService = userAuthenticatorService;
    }

    @GetMapping("/login")
    String login(Model model) {
        model.addAttribute("user", new UserEntity());
        return "login";
    }

    @RequestMapping(path = "/register-user", method = RequestMethod.POST)
    public String registerNewUser(@Valid @ModelAttribute("user") UserEntity userEntity) {
        userService.registerNewUser(userEntity);
        return "login";
    }

    @GetMapping("/home")
    String home(Model model) {
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        SimpleGrantedAuthority simpleGrantedAuthority = authorities.get(0);
        if ("ROLE_ADMIN".equalsIgnoreCase(simpleGrantedAuthority.getAuthority())) {

            return "admin/index";
        } else {

            return "user/index";
        }
    }

    @GetMapping("/403")
    String error() {
        return "error/403";
    }

}
