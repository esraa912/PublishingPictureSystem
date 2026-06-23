package com.pioneers.picturepublishingservice.auth;

import java.util.UUID;

public interface CurrentUserProvider {

    UUID getCurrentUserId();
}
