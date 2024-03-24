package mas.metricsproducer.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mas.metricsproducer.model.metrics.MeasurementType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class MetricDto {

    private Long sensorId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private double measurement;
    private MeasurementType measurementType;

}
