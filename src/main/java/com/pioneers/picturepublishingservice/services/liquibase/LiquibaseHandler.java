package com.pioneers.picturepublishingservice.services.liquibase;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Provides functionality to rollback a specified number of changesets
 * applied to the database using Liquibase.
 */
@Service
@RequiredArgsConstructor
public class LiquibaseHandler {

    private final DataSource dataSource;

    /**
     * Rolls back the last applied Liquibase changesets.
     *
     * @param changes is the number of changesets to rollback.
     * @throws SQLException       if a database access error occurs.
     * @throws LiquibaseException if Liquibase fails to perform the rollback.
     */
    public void rollback(final int changes) throws SQLException, LiquibaseException {

        final Liquibase liquibase;

        try {
            final Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));

            liquibase
                    = new Liquibase("db/liquibase/changelog-master.xml", new ClassLoaderResourceAccessor(), database);
        } catch (SQLException e) {
            throw new LiquibaseException("Failed to initialize Liquibase database connection", e);
        }

        try {
            liquibase.rollback(changes, String.valueOf(new Contexts()));
        } catch (LiquibaseException e) {
            throw new LiquibaseException("Rollback failed for " + changes + " changes", e);
        }
    }
}