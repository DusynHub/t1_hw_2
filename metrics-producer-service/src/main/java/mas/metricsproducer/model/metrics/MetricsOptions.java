package mas.metricsproducer.model.metrics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MetricsOptions {

    private int delayInSeconds;
    private MeasurementType[] measurementTypes;

}
