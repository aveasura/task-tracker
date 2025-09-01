package org.tracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tracker.cli.Cli;

@SpringBootApplication
public class TrackerApplication implements CommandLineRunner {

    private final Cli cli;

    public TrackerApplication(Cli cli) {
        this.cli = cli;
    }

    public static void main(String[] args) {
        SpringApplication.run(TrackerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        cli.start();
    }
}
