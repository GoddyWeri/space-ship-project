package com.example.spaceship.app;

import org.flywaydb.core.Flyway;

public class DatabaseMigration {
	public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:h2:~/test", "sa", "") 
                .locations("classpath:db/migration")
                .load();
        flyway.migrate();
    }
}
