package mas.metricsconsumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mas.metricsconsumer.repository.MetricRepository;
import mas.metricsconsumer.model.dto.MetricDto;
import mas.metricsconsumer.web.mapper.MetricMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetricsServiceImpl implements MetricsService {
    private final MetricRepository metricRepository;
    private final MetricMapper metricMapper;

    @Override
    public List<MetricDto> getAllMetrics(int pageNumber, int size, String sort) {
        log.info("[Metric Service] received a public request to get all metrics");

        Sort byDate = Sort.by(Sort.Direction.ASC, "timestamp");
        if (sort.equalsIgnoreCase("DESC")) {
            byDate = Sort.by(Sort.Direction.DESC);
        }

        PageRequest pageRequest = PageRequest.of(pageNumber, size, byDate);
        return metricRepository.findAll(pageRequest)
                .stream()
                .map(metricMapper::toDto)
                .collect(Collectors.toList());
    }

}
