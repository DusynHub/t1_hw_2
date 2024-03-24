package mas.metricsconsumer.repository;

import mas.metricsconsumer.model.MeasurementType;
import mas.metricsconsumer.model.Metric;
import mas.metricsconsumer.model.dto.MetricValueDuringPeriodDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MetricRepository extends JpaRepository<Metric, Long> {

    List<Metric> findAllByMeasurementEqualsIgnoreCase(String metricId, Pageable pageable);

    @Query("SELECT new mas.metricsconsumer.model.dto.MetricValueDuringPeriodDto( :start, :end, :type, max(m.measurement) ) " +
            "FROM Metric m " +
            "WHERE m.timestamp >= :start " +
            "   and m.timestamp <= :end " +
            "   and m.measurementType = :type ")
    MetricValueDuringPeriodDto findMaxValueByPeriod(@Param("start") LocalDateTime start,
                                                    @Param("end") LocalDateTime end,
                                                    @Param("type") MeasurementType type);

    @Query("SELECT new mas.metricsconsumer.model.dto.MetricValueDuringPeriodDto( :start, :end, :type, avg (m.measurement) ) " +
            "FROM Metric m " +
            "WHERE m.timestamp >= :start " +
            "   and m.timestamp <= :end " +
            "   and m.measurementType = :type ")
    MetricValueDuringPeriodDto findAvgValueByPeriod(@Param("start") LocalDateTime start,
                                                    @Param("end") LocalDateTime end,
                                                    @Param("type") MeasurementType type);

    @Query("SELECT m  " +
            "FROM Metric m " +
            "WHERE m.timestamp >= :start " +
            "   and m.timestamp <= :end " +
            "   and m.measurementType = :type ")
    Page<Metric> getMetricsByTypeByPeriod(@Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end,
                                          @Param("type") MeasurementType type,
                                          Pageable pageable);

}
