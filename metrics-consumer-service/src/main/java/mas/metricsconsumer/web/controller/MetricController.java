package mas.metricsconsumer.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mas.metricsconsumer.model.MeasurementType;
import mas.metricsconsumer.model.dto.MetricDto;
import mas.metricsconsumer.model.dto.MetricValueDuringPeriodDto;
import mas.metricsconsumer.service.MetricsService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/metrics")
@RequiredArgsConstructor
@Slf4j
@Validated
public class MetricController {

    private final MetricsService metricsService;

    /**
     * Method to find metrics
     *
     * @param page   required page
     * @param size   required page size
     * @param byDate sort order by Date
     * @return products
     */
    @Operation(summary = "Get all metrics by page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page with metrics has been returned",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MetricDto.class)))})
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MetricDto> findAll(@Parameter(description = "page number to be shown") @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                   @Parameter(description = "page size to be shown") @RequestParam(defaultValue = "50") @PositiveOrZero int size,
                                   @Parameter(description = "sorting order by timestamp") @RequestParam(defaultValue = "ASC") String byDate) {
        log.info("[Metric Controller Consumer] received a request GET /metrics with params");
        return metricsService.getAllMetrics(page, size, byDate);
    }

    /**
     * Method to find metric by id
     *
     * @return metric
     */
    @Operation(summary = "Get metric by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The metric has been found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MetricDto.class)))}),
            @ApiResponse(responseCode = "400", description = "Wrong id format"),
            @ApiResponse(responseCode = "404", description = "The metric has not been found")
    })
    @GetMapping("/{metricId}")
    @ResponseStatus(HttpStatus.OK)
    public MetricDto findById(@Parameter(description = "id of metric to be searched") @PathVariable @Positive long metricId) {
        log.info("[Metric Controller Consumer] received a request GET /metrics/{}", metricId);
        return metricsService.findById(metricId);
    }

    /**
     * Method to find metrics
     *
     * @param page   required page
     * @param size   required page size
     * @param byDate sort order by Date
     * @return products
     */
    @Operation(summary = "Get all metrics by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page with metrics with required type has been returned",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MetricDto.class)))}),
            @ApiResponse(responseCode = "400", description = "Wrong request")
    })
    @GetMapping("/type/{metricType}")
    @ResponseStatus(HttpStatus.OK)
    public List<MetricDto> findAllByType(@Parameter(description = "metrics type") @PathVariable String metricType,
                                         @Parameter(description = "page number to be shown") @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                         @Parameter(description = "page size to be shown") @RequestParam(defaultValue = "50") @PositiveOrZero int size,
                                         @Parameter(description = "sorting order by timestamp") @RequestParam(defaultValue = "ASC") String byDate) {
        log.info("[Metric Controller Consumer] received a request GET /metrics with params");
        return metricsService.getAllMetricsByType(metricType, page, size, byDate);
    }

    /**
     * Method to find avg value metric by type during last day
     *
     * @param metricType metric type
     * @return period, metric type and avg value
     */
    @Operation(summary = "Get all metrics by type and period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page with metrics with required type during required period has been returned",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MetricDto.class)))}),
            @ApiResponse(responseCode = "400", description = "Wrong request")
    })
    @GetMapping("by-period/{metricType}")
    @ResponseStatus(HttpStatus.OK)
    public List<MetricDto> findMetricsByTypeByPeriod(@PathVariable String metricType,
                                                     @Parameter(description = "period start") @RequestParam LocalDateTime start,
                                                     @Parameter(description = "period end") @RequestParam LocalDateTime end,
                                                     @Parameter(description = "page number to be shown") @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                                     @Parameter(description = "page size to be shown") @RequestParam(defaultValue = "50") @PositiveOrZero int size,
                                                     @Parameter(description = "sorting order by timestamp") @RequestParam(defaultValue = "ASC") String sort) {
        log.info("[Metric Controller Consumer] received a request GET /metrics/{}/by-period", metricType);

        if (start == null) {
            start = LocalDateTime.now().minusDays(1);
        }

        if (end == null) {
            end = LocalDateTime.now();
        }
        MeasurementType type = MeasurementType.valueOf(metricType.toUpperCase());

        return metricsService.getMetricsByTypeByPeriod(start, end, type, page, size, sort);
    }


    /**
     * Method to find max value metric by type during last minute
     *
     * @param metricType metric type
     * @return period, metric type and max value
     */
    @Operation(summary = "Get maximum value for required metric type for last minute")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Required max value for last minute have been returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MetricValueDuringPeriodDto.class))}),
            @ApiResponse(responseCode = "400", description = "Wrong request")
    })
    @GetMapping("/minute-max/{metricType}")
    @ResponseStatus(HttpStatus.OK)
    public MetricValueDuringPeriodDto findMaxMetricValueLastMinute(@PathVariable String metricType) {
        log.info("[Metric Controller Consumer] received a request GET /metrics/{}/minute-max", metricType);
        MeasurementType type = MeasurementType.valueOf(metricType.toUpperCase());
        return metricsService.getMaxValueMetricDuringPeriod(LocalDateTime.now().minusMinutes(1), LocalDateTime.now(), type);
    }

    /**
     * Method to find max value metric by type during last hour
     *
     * @param metricType metric type
     * @return period, metric type and max value
     */
    @Operation(summary = "Get maximum value for required metric type for last hour")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Required max value for last hour have been returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MetricValueDuringPeriodDto.class))}),
            @ApiResponse(responseCode = "400", description = "Wrong request")
    })
    @GetMapping("/hour-max/{metricType}")
    @ResponseStatus(HttpStatus.OK)
    public MetricValueDuringPeriodDto findMaxMetricValueLastHour(@PathVariable String metricType) {
        log.info("[Metric Controller Consumer] received a request GET /metrics/{}/hour-max", metricType);
        MeasurementType type = MeasurementType.valueOf(metricType.toUpperCase());
        return metricsService.getMaxValueMetricDuringPeriod(LocalDateTime.now().minusHours(1), LocalDateTime.now(), type);
    }

    /**
     * Method to find max value metric by type during last day
     *
     * @param metricType metric type
     * @return period, metric type and max value
     */
    @Operation(summary = "Get maximum value for required metric type for last day")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Required max value for last day have been returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MetricValueDuringPeriodDto.class))}),
            @ApiResponse(responseCode = "400", description = "Wrong request")
    })
    @GetMapping("/day-max/{metricType}")
    @ResponseStatus(HttpStatus.OK)
    public MetricValueDuringPeriodDto findMaxMetricValueLastDay(@PathVariable String metricType) {
        log.info("[Metric Controller Consumer] received a request GET /metrics/{}/day-max", metricType);
        MeasurementType type = MeasurementType.valueOf(metricType.toUpperCase());
        return metricsService.getMaxValueMetricDuringPeriod(LocalDateTime.now().minusDays(1), LocalDateTime.now(), type);
    }


    /**
     * Method to find avg value metric by type during last minute
     *
     * @param metricType metric type
     * @return period, metric type and avg value
     */
    @Operation(summary = "Get avg value for required metric type for last minute")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Required avg value for last minute have been returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MetricValueDuringPeriodDto.class))}),
            @ApiResponse(responseCode = "400", description = "Wrong request")
    })
    @GetMapping("/minute-avg/{metricType}")
    @ResponseStatus(HttpStatus.OK)
    public MetricValueDuringPeriodDto findAvgMetricValueLastMinute(@PathVariable String metricType) {
        log.info("[Metric Controller Consumer] received a request GET /metrics/{}/minute-avg", metricType);
        MeasurementType type = MeasurementType.valueOf(metricType.toUpperCase());
        return metricsService.getAvgValueMetricDuringPeriod(LocalDateTime.now().minusMinutes(1), LocalDateTime.now(), type);
    }

    /**
     * Method to find avg value metric by type during last hour
     *
     * @param metricType metric type
     * @return period, metric type and avg value
     */
    @Operation(summary = "Get avg value for required metric type for last hour")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Required avg value for last hour have been returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MetricValueDuringPeriodDto.class))}),
            @ApiResponse(responseCode = "400", description = "Wrong request")
    })
    @GetMapping("/hour-avg/{metricType}")
    @ResponseStatus(HttpStatus.OK)
    public MetricValueDuringPeriodDto findAvgMetricValueLastHour(@PathVariable String metricType) {
        log.info("[Metric Controller Consumer] received a request GET /metrics/{}/hour-avg", metricType);
        MeasurementType type = MeasurementType.valueOf(metricType.toUpperCase());
        return metricsService.getAvgValueMetricDuringPeriod(LocalDateTime.now().minusHours(1), LocalDateTime.now(), type);
    }

    /**
     * Method to find avg value metric by type during last day
     *
     * @param metricType metric type
     * @return period, metric type and avg value
     */
    @Operation(summary = "Get avg value for required metric type for last day")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Required avg value for last day have been returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MetricValueDuringPeriodDto.class))}),
            @ApiResponse(responseCode = "400", description = "Wrong request")
    })
    @GetMapping("/day-avg/{metricType}")
    @ResponseStatus(HttpStatus.OK)
    public MetricValueDuringPeriodDto findAvgMetricValueLastDay(@PathVariable String metricType) {
        log.info("[Metric Controller Consumer] received a request GET /metrics/{}/day-avg", metricType);
        MeasurementType type = MeasurementType.valueOf(metricType.toUpperCase());
        return metricsService.getAvgValueMetricDuringPeriod(LocalDateTime.now().minusDays(1), LocalDateTime.now(), type);
    }
}
