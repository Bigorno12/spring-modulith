package mu.architecture.modulith.order.event

import mu.architecture.modulith.config.RabbitMQConfig
import org.springframework.modulith.events.Externalized

@Externalized(target = RabbitMQConfig.EXCHANGE_NAME)
data class OrderEvent(
    val orderNumber: String?,
    val productCode: String?,
    val customerName: String?,
    val customerEmail: String?,
    val customerPhone: String?,
    val quantity: Int?
) {
    init {
        requireNotNull(orderNumber) { "orderNumber must not be null" }
        requireNotNull(productCode) { "product code cannot be null" }
        requireNotNull(customerName) { "customer name cannot be null" }
        requireNotNull(customerEmail) { "customer email cannot be null" }
        requireNotNull(customerPhone) { "customer phone cannot be null" }
        requireNotNull(quantity) { "quantity cannot be null" }
    }
}
