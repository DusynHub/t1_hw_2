package mas.metricsproducer.service;

import mas.metricsproducer.model.metrics.MetricsOptions;

public interface MetricsService {

    void sendMessages(MetricsOptions testOptions);

}
