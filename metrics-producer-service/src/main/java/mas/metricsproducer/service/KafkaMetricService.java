package mas.metricsproducer.service;

import mas.metricsproducer.model.Metric;

public interface KafkaMetricService {

    void send(Metric metric);

}
