package com.example.scheduler21.security;

import com.example.scheduler21.entities.Gender;
import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.entities.Role;
import com.example.scheduler21.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = user.getRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role.toString()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (user instanceof Patient)
            return ((Patient) user).isAccountNonLocked();
        else
            return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (user instanceof Patient)
            return ((Patient) user).isAccountNonLocked();
        else
            return true;
    }


    public String getFirstName() {
        return this.user.getFirstName();
    }

    public String getLastName() {
        return this.user.getLastName();
    }

    public String getPhoneNumber() {
        return this.user.getPhoneNumber();
    }

    public LocalDate getBirthday() {
        return this.user.getBirthday();
    }

    public Gender getGender() {
        return this.user.getGender();
    }

    public long getAge() {
        return this.user.getAge();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
