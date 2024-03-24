package mas.metricsconsumer.web.controller;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mas.metricsconsumer.model.dto.MetricDto;
import mas.metricsconsumer.service.MetricsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/metrics")
@RequiredArgsConstructor
@Slf4j
public class MetricController {

    private final MetricsService metricsService;
//    @PostMapping
//    public void testSend(@RequestBody MetricsOptionsDto metricsOptionsDto) {
//        MetricsOptions metricsOptions = metricsOptionsMapper.toEntity(metricsOptionsDto);
//        metricsService.sendMessages(metricsOptions);
//    }

    /**
     * Method to find products by criteria product
     *
     * @param page        required page
     * @param size        required page size
//     * @param byid        sort order by id
     * @return products
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MetricDto> findAll(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                   @RequestParam(defaultValue = "50") @PositiveOrZero int size,
                                   @RequestParam(defaultValue = "ASC") String byDate) {
        log.info("[Metric Controller Consumer] received a request GET /metrics with params");
        return metricsService.getAllMetrics(page, size, byDate);
    }


}
