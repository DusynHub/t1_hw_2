package mas.metricsproducer.web.mapper;

import mas.metricsproducer.model.metrics.MetricsOptions;
import mas.metricsproducer.web.dto.MetricsOptionsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MetricsOptionsMapper
        extends Mappable<MetricsOptions, MetricsOptionsDto> {
}
