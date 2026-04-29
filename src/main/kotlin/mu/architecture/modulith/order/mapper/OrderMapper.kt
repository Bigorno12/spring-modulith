package mu.architecture.modulith.order.mapper

import mu.architecture.modulith.order.dto.OrderView
import mu.architecture.modulith.order.event.OrderEvent
import mu.architecture.modulith.order.model.Order
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderNumber", source = "orderNumber")
    fun toEntity(source: OrderView?, orderNumber: String?): Order

    fun toEvent(source: Order?): OrderEvent
}