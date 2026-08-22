package com.pioneers.picturepublishingservice.services.liquibase;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import com.pioneers.picturepublishingservice.errors.exceptions.LiquibaseRollbackException;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Provides functionality to rollback a specified number of changesets
 * applied to the database using Liquibase.
 *
 * @author esraa
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RollbackHandler {

    private final DataSource dataSource;

    /**
     * Rolls back the last applied Liquibase changesets.
     *
     * @param changes is the number of changesets to rollback
     * @throws LiquibaseException if Liquibase fails to perform the rollback
     * @throws LiquibaseRollbackException if rollback fails
     */
    public void rollback(final int changes) throws LiquibaseException {
        final String methodName = "rollback()";
        log.debug("{} - Starting rollback for changes: [{}]", methodName, changes);

        final Liquibase liquibase;

        try {
            final Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));

            liquibase = new Liquibase("db/liquibase/changelog-master.xml", new ClassLoaderResourceAccessor(), database);
            log.debug("{} - Liquibase initialized successfully", methodName);
        } catch (SQLException e) {
            throw new LiquibaseException("Failed to initialize Liquibase database connection", e);
        }

        try {
            liquibase.rollback(changes, String.valueOf(new Contexts()));
            log.info("{} - Rollback successfully finished for changes: [{}]", methodName, changes);
        } catch (LiquibaseException e) {
            throw new LiquibaseRollbackException("Rollback failed for " + changes + " changes");
        }
    }
}
