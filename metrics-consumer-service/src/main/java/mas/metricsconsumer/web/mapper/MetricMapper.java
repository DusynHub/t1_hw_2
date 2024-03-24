package mas.metricsconsumer.web.mapper;

import mas.metricsconsumer.model.Metric;
import mas.metricsconsumer.model.dto.MetricDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MetricMapper extends Mappable<Metric, MetricDto> {
}
