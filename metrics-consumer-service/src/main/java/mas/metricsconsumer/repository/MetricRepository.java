package mas.metricsconsumer.repository;

import mas.metricsconsumer.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricRepository extends JpaRepository<Metric, Long> {


}
