package com.example.tokopedia.scrapper;

import com.example.tokopedia.scrapper.service.ScrapperService;
import com.example.tokopedia.scrapper.util.exception.ScrappingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScrapperApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void downloadCsv() throws ScrappingException {
        ScrapperService service = new ScrapperService();
        String file = service.downloadProductListCsv();
        Assertions.assertNotNull(file);
    }

}
