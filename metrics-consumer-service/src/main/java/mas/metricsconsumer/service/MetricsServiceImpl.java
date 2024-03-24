package mas.metricsconsumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mas.metricsconsumer.exception.ResourceNotFoundException;
import mas.metricsconsumer.model.MeasurementType;
import mas.metricsconsumer.model.dto.MetricDto;
import mas.metricsconsumer.model.dto.MetricValueDuringPeriodDto;
import mas.metricsconsumer.repository.MetricRepository;
import mas.metricsconsumer.web.mapper.MetricMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetricsServiceImpl implements MetricsService {
    private final MetricRepository metricRepository;
    private final MetricMapper metricMapper;

    @Override
    public List<MetricDto> getAllMetrics(int page, int size, String sort) {
        log.info("[Metric Service] received a public request to get all metrics");

        Sort byDate = Sort.by(Sort.Direction.ASC, "timestamp");
        if (sort.equalsIgnoreCase("DESC")) {
            byDate = Sort.by(Sort.Direction.DESC);
        }

        PageRequest pageRequest = PageRequest.of(page, size, byDate);
        return metricRepository.findAll(pageRequest)
                .stream()
                .map(metricMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MetricDto findById(long metricId) {
        log.info("[Metric Service] received a public request to metrics with id = {}", metricId);
        return metricMapper.toDto(metricRepository.findById(metricId).orElseThrow(
                () ->
                        new ResourceNotFoundException(
                                String.format("Metric with id = %s not found", metricId)
                        )
        ));
    }

    @Override
    public List<MetricDto> getAllMetricsByType(String metricType, int page, int size, String sort) {
        log.info("[Metric Service] received a public request to get metric by id");

        Sort byDate = Sort.by(Sort.Direction.ASC, "timestamp");
        if (sort.equalsIgnoreCase("DESC")) {
            byDate = Sort.by(Sort.Direction.DESC);
        }
        PageRequest pageRequest = PageRequest.of(page, size, byDate);

        return metricRepository.findAllByMeasurementEqualsIgnoreCase(metricType, pageRequest)
                .stream()
                .map(metricMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MetricDto> getMetricsByTypeByPeriod(LocalDateTime start, LocalDateTime end, MeasurementType type, int page, int size, String sort) {

        if (start.isAfter(end)) {
            throw new ResourceNotFoundException("Period start must be before period end");
        }

        Sort byDate = Sort.by(Sort.Direction.ASC, "timestamp");
        if (sort.equalsIgnoreCase("DESC")) {
            byDate = Sort.by(Sort.Direction.DESC);
        }
        PageRequest pageRequest = PageRequest.of(page, size, byDate);
        return metricRepository.getMetricsByTypeByPeriod(start, end, type, pageRequest).stream()
                .map(metricMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public MetricValueDuringPeriodDto getMaxValueMetricDuringPeriod(LocalDateTime start, LocalDateTime end, MeasurementType type) {
        log.info("[Metric Service] received a public request max value metric with type {} during period start = {} end = {}",
                type, start, end);
        return metricRepository.findMaxValueByPeriod(start, end, type);
    }

    @Override
    public MetricValueDuringPeriodDto getAvgValueMetricDuringPeriod(LocalDateTime start, LocalDateTime end, MeasurementType type) {
        log.info("[Metric Service] received a public request avg value metric with type {} during period start = {} end = {}",
                type, start, end);
        return metricRepository.findAvgValueByPeriod(start, end, type);
    }
}
