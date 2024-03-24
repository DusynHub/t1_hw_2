package mas.metricsproducer.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mas.metricsproducer.model.metrics.MetricsOptions;
import mas.metricsproducer.service.MetricsService;
import mas.metricsproducer.web.dto.MetricsOptionsDto;
import mas.metricsproducer.web.mapper.MetricsOptionsMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
@RequiredArgsConstructor
@Slf4j
public class MetricController {

    private final MetricsService metricsService;
    private final MetricsOptionsMapper metricsOptionsMapper;

    @PostMapping
    public void testSend(@RequestBody MetricsOptionsDto metricsOptionsDto) {
        MetricsOptions metricsOptions = metricsOptionsMapper.toEntity(metricsOptionsDto);
        metricsService.sendMessages(metricsOptions);
    }

}
