package mas.metricsconsumer.service;

import mas.metricsconsumer.model.Metric;
import mas.metricsconsumer.repository.MetricRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMetricsServiceImpl implements KafkaMetricsService {

    private final MetricRepository metricRepository;

    @Override
    public void handle(Metric metric) {
        log.info("Metric object {} was saved", metric);
        metricRepository.save(metric);
    }

}
