package com.example.tokopedia.scrapper;

import com.example.tokopedia.scrapper.service.ScrapperService;
import com.example.tokopedia.scrapper.util.exception.ScrappingException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScrapperApplication {

	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(ScrapperApplication.class, args);
		ScrapperService service = new ScrapperService();
		try {
			String csv = service.downloadProductListCsv();
			System.out.println("Extraction is successfully saved to " + csv);
		} catch (ScrappingException e) {
			System.err.println("Failed. Please try again.");
		}
	}

}
