package com.pioneers.picturepublishingservice.auth;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CurrentUserProviderImpl implements CurrentUserProvider{

    private final HttpSession session;

    @Override
    public UUID getCurrentUserId() {
        return (UUID) session.getAttribute("user_id");
    }
}
