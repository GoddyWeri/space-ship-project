package com.example.spaceship.app;

//import javax.sql.DataSource;
//
//import org.flywaydb.core.Flyway;
//import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
//import org.springframework.stereotype.Component;

//import jakarta.annotation.PostConstruct;


//@Component
public class DatabaseMigration {

//    private final DataSource dataSource;
//    private final FlywayProperties flywayProperties;
//
//    public DatabaseMigration(DataSource dataSource, FlywayProperties flywayProperties) {
//        this.dataSource = dataSource;
//        this.flywayProperties = flywayProperties;
//    }
//
//    @PostConstruct
//    public void migrateDatabase() {
//        Flyway flyway = Flyway.configure()
//                .dataSource(dataSource)
//                .locations(flywayProperties.getLocations().toArray(new String[0]))
//                .load();
//
//        // Start the migration process
//        flyway.migrate();
//    }
}
