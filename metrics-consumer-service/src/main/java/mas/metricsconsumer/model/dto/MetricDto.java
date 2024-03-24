package mas.metricsconsumer.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mas.metricsconsumer.model.MeasurementType;

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
