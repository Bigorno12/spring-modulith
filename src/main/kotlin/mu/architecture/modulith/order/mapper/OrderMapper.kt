package mu.architecture.modulith.order.mapper

import mu.architecture.modulith.order.dto.OrderView
import mu.architecture.modulith.order.model.Order
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface OrderMapper {

    fun toEntity(orderView: OrderView) : Order
}