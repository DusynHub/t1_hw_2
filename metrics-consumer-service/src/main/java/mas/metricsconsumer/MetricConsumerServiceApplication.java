package mas.metricsconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
        @PropertySource("/application.properties"),
        @PropertySource("/.env.properties")
})
public class MetricConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetricConsumerServiceApplication.class, args);
    }

}
