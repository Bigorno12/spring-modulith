package mu.architecture.modulith.order.repository

import mu.architecture.modulith.common.repository.GenericRepository
import mu.architecture.modulith.order.model.Order
import mu.architecture.modulith.order.model.Status
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : GenericRepository<Order> {

    fun existsOrderByOrderNumberAndStatusIn(
        orderNumber: String,
        statuses: MutableCollection<Status>
    ): Boolean
}