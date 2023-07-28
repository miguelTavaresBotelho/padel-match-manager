package com.padelmatchmanager.padelmatchmanager.security;

import com.padelmatchmanager.padelmatchmanager.model.Player;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final Player player;

    public CustomUserDetails(Player player) {
        this.player = player;
    }

    // Implement the UserDetails interface methods here

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Implement the logic to retrieve the user's authorities
        return null;
    }

    @Override
    public String getPassword() {
        return player.getPassword();
    }

    @Override
    public String getUsername() {
        return player.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
