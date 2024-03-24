package mas.metricsproducer.service;

import lombok.RequiredArgsConstructor;
import mas.metricsproducer.model.Metric;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@RequiredArgsConstructor
public class KafkaMetricServiceImpl implements KafkaMetricService {

    private final String topicName = "metrics-topic";

    private final KafkaSender<String, Object> sender;

    @Override
    public void send(Metric metric) {
        sender.send(
                        Mono.just(
                                SenderRecord.create(
                                        topicName,
                                        0,
                                        System.currentTimeMillis(),
                                        String.valueOf(metric.hashCode()),
                                        metric,
                                        null
                                )
                        )
                )
                .subscribe();
    }

}
