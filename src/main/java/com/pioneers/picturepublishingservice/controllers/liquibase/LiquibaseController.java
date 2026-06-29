package com.pioneers.picturepublishingservice.controllers.liquibase;

import com.pioneers.picturepublishingservice.services.liquibase.LiquibaseHandler;
import liquibase.exception.LiquibaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * Contains APIs for managing Liquibase rollback operations.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("liquibase")
public class LiquibaseController {

    private static final String CLASS_NAME = LiquibaseController.class.getSimpleName();

    private final LiquibaseHandler liquibaseHandler;

    @PostMapping("rollback/{changes}")
    public void rollbackApi(@PathVariable final int changes) throws SQLException, LiquibaseException {
        final String methodName = CLASS_NAME + ".rollback()";
        liquibaseHandler.rollback(changes);
        log.info("{}, Rollback successfully finished for changes {}", methodName, changes);
    }
}
