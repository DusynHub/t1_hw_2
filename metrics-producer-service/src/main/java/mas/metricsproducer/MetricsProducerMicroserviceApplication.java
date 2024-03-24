package mas.metricsproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
        @PropertySource("/application.properties"),
        @PropertySource("/.env.properties")
})
public class MetricsProducerMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetricsProducerMicroserviceApplication.class, args);
    }

}
