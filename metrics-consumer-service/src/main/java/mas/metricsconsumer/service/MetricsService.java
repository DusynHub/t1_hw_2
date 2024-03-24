package mas.metricsconsumer.service;


import mas.metricsconsumer.model.MeasurementType;
import mas.metricsconsumer.model.dto.MetricDto;
import mas.metricsconsumer.model.dto.MetricValueDuringPeriodDto;

import java.time.LocalDateTime;
import java.util.List;

public interface MetricsService {

    /**
     * Method to get all categories
     *
     * @param page page number starting from 0
     * @param size page size
     * @return required category list
     */
    List<MetricDto> getAllMetrics(int page, int size, String sort);

    /**
     * Method to get metric by id
     *
     * @param metricId metric id
     * @return required metric
     */
    MetricDto findById(long metricId);

    /**
     * Method to get all categories
     *
     * @param metricType metric type
     * @param page       page number starting from 0
     * @param size       page size
     * @return required category list
     */
    List<MetricDto> getAllMetricsByType(String metricType, int page, int size, String sort);


    /**
     * Method to get metrics by type during period
     *
     * @param type  measurement type
     * @param start period start
     * @param end   period end
     * @return required category list
     */
    List<MetricDto> getMetricsByTypeByPeriod(LocalDateTime start, LocalDateTime end, MeasurementType type, int page,
                                             int size,
                                             String sort);


    /**
     * Method to get max value by metric type during period
     *
     * @param type  measurement type
     * @param start period start
     * @param end   period end
     * @return required category list
     */
    MetricValueDuringPeriodDto getMaxValueMetricDuringPeriod(LocalDateTime start, LocalDateTime end, MeasurementType type);


    /**
     * Method to avg value by metric type during period
     *
     * @param type  measurement type
     * @param start period start
     * @param end   period end
     * @return required category list
     */
    MetricValueDuringPeriodDto getAvgValueMetricDuringPeriod(LocalDateTime start, LocalDateTime end, MeasurementType type);

}
