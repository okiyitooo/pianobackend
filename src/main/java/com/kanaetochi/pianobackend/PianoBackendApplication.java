package com.kanaetochi.pianobackend;

import io.github.cdimascio.dotenv.Dotenv;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PianoBackendApplication {

	// private static final Logger logger = LoggerFactory.getLogger(PianoBackendApplication.class);
	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach( entry ->
			System.setProperty(entry.getKey(), entry.getValue())
		);
		SpringApplication.run(PianoBackendApplication.class, args);
	}

}
