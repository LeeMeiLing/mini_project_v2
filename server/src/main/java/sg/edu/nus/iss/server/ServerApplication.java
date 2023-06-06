package sg.edu.nus.iss.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.iss.server.services.HospitalService;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner{

	@Autowired
	HospitalService hospSvc;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// hospSvc.checkUpdated();
	}

}
