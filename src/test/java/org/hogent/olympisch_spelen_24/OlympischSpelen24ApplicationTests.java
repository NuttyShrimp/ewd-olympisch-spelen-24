package org.hogent.olympisch_spelen_24;

import org.hogent.olympisch_spelen_24.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class OlympischSpelen24ApplicationTests {

    @Test
    void contextLoads() {
    }

}
