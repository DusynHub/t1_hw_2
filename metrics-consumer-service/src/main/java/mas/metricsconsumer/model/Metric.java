package mas.metricsconsumer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "metric")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sensorId;
    private LocalDateTime timestamp;
    private double measurement;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private MeasurementType measurementType;
}
