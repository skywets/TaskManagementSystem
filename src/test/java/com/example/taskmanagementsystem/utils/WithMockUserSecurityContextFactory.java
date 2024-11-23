package com.example.taskmanagementsystem.utils;

import com.example.taskmanagementsystem.models.entities.Role;
import com.example.taskmanagementsystem.models.entities.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Copy from org.springframework.security.test.context.support.WithMockUserSecurityContextFactory and customize.
 */
final class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser withUser) {
        String username = StringUtils.hasLength(withUser.username()) ? withUser.username() : withUser.value();
        Assert.notNull(username, () -> withUser + " cannot have null username on both username and value properties");
        com.example.taskmanagementsystem.models.entities.User user = new com.example.taskmanagementsystem.models.entities.User();
        user.setName(username);
        user.setRole(Role.ADMIN);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(),
                user.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }

}