package mas.metricsconsumer.service;

import mas.metricsconsumer.model.Metric;

public interface KafkaMetricsService {

    void handle(Metric metric);

}
