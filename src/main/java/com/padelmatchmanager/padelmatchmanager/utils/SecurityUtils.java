package com.padelmatchmanager.padelmatchmanager.utils;

import com.padelmatchmanager.padelmatchmanager.model.Player;
import com.padelmatchmanager.padelmatchmanager.security.CustomUserDetails;
import com.padelmatchmanager.padelmatchmanager.service.PlayerService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    public static Player getCurrentPlayer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return null;
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        PlayerService playerService = getPlayerServiceFromApplicationContext();
        return playerService.getPlayerByUsername(username);
    }

    private static PlayerService getPlayerServiceFromApplicationContext() {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        return context.getBean(PlayerService.class);
    }
}