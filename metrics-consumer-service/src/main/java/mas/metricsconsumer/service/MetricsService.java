package mas.metricsconsumer.service;


import mas.metricsconsumer.model.dto.MetricDto;

import java.util.List;

public interface MetricsService {

    /**
     * Method to get all categories
     *
     * @param fromLine first category in list
     * @param size     page size
     * @return required category list
     */
    List<MetricDto> getAllMetrics(int fromLine, int size, String sort);

}
