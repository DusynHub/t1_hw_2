package mas.metricsproducer.web.mapper;

import mas.metricsproducer.model.Metric;
import mas.metricsproducer.web.dto.MetricDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MetricMapper extends Mappable<Metric, MetricDto> {
}
