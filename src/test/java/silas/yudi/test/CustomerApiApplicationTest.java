package silas.yudi.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import silas.yudi.CustomerApiApplication;

@SpringBootTest
class CustomerApiApplicationTest {

    @Test
    void testMain() {
        CustomerApiApplication.main(new String[]{});
    }

}
