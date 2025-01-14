package my.socpms.api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import my.socpms.api.model.User;

@Component("userSecurity")
public class UserSecurity {
    
    public boolean isCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return userId.equals(((User) authentication.getPrincipal()).getId());
        }
        return false;
    }
}