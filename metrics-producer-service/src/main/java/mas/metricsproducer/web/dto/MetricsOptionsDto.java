package mas.metricsproducer.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mas.metricsproducer.model.metrics.MeasurementType;

@NoArgsConstructor
@Getter
@Setter
public class MetricsOptionsDto {

    private int delayInSeconds;
    private MeasurementType[] measurementTypes;

}
