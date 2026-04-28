package mu.architecture.modulith.order.service

import mu.architecture.modulith.order.dto.OrderView

interface OrderService {
    fun save(orderView: OrderView)
}