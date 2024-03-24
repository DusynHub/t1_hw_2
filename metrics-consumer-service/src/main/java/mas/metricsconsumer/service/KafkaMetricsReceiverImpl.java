package mas.metricsconsumer.service;

import mas.metricsconsumer.config.LocalDateTimeDeserializer;
import mas.metricsconsumer.model.Metric;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KafkaMetricsReceiverImpl implements KafkaMetricsReceiver {

    private final KafkaReceiver<String, Object> receiver;
    private final LocalDateTimeDeserializer localDateTimeDeserializer;
    private final KafkaMetricsService kafkaMetricsService;

    @PostConstruct
    private void init() {
        fetch();
    }

    @Override
    public void fetch() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class,
                        localDateTimeDeserializer)
                .create();
        receiver.receive()
                .subscribe(r -> {
                    Metric metric = gson
                            .fromJson(r.value().toString(), Metric.class);
                    kafkaMetricsService.handle(metric);
                    r.receiverOffset().acknowledge();
                });
    }

}
