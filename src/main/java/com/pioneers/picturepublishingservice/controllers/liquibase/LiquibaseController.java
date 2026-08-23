package com.pioneers.picturepublishingservice.controllers.liquibase;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pioneers.picturepublishingservice.services.liquibase.RollbackHandler;

import liquibase.exception.LiquibaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Contains APIs for managing Liquibase rollback operations.
 *
 * @author esraa
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("liquibase")
public class LiquibaseController {

    private final RollbackHandler liquibaseHandler;

    /**
     * Executes a Liquibase rollback operation for a given number of changes.
     *
     * @param changes: the number of changesets to rollback.
     * @throws LiquibaseException: if Liquibase fails to perform the rollback.
     */
    @PostMapping("rollback/{changes}")
    public void rollbackApi(@PathVariable final int changes) throws LiquibaseException {
        final String methodName = "rollbackApi()";

        liquibaseHandler.rollback(changes);

        log.info("{} - Rollback successfully finished for changes: {}", methodName, changes);
    }
}
