package mas.metricsproducer.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mas.metricsproducer.model.metrics.MetricsOptions;
import mas.metricsproducer.service.MetricsService;
import mas.metricsproducer.web.dto.MetricsOptionsDto;
import mas.metricsproducer.web.mapper.MetricsOptionsMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
@RequiredArgsConstructor
@Slf4j
public class MetricController {

    private final MetricsService metricsService;
    private final MetricsOptionsMapper metricsOptionsMapper;

    @Operation(summary = "Start producing metrics with delay provided in request body ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Production started"),
            @ApiResponse(responseCode = "500", description = "Invalid id supplied")})
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void testSend(@RequestBody MetricsOptionsDto metricsOptionsDto) {
        MetricsOptions metricsOptions = metricsOptionsMapper.toEntity(metricsOptionsDto);
        metricsService.sendMessages(metricsOptions);
    }

}
