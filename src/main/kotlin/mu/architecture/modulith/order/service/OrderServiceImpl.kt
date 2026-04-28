package mu.architecture.modulith.order.service

import mu.architecture.modulith.order.dto.OrderView
import mu.architecture.modulith.order.event.OrderEvent
import mu.architecture.modulith.order.mapper.OrderMapper
import mu.architecture.modulith.order.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val orderMapper: OrderMapper,
    private val eventPublisher: ApplicationEventPublisher
) :
    OrderService {

    companion object {
        private val log = LoggerFactory.getLogger(OrderServiceImpl::class.java)
    }

    override fun save(orderView: OrderView) {
        log.info("Saving order order")
        orderRepository.save(orderMapper.toEntity(orderView))
        publishOrderView(orderView)
    }

    private fun publishOrderView(orderView: OrderView) {
        log.info("Publishing order view")
        eventPublisher.publishEvent(
            OrderEvent(
                orderView.orderNumber,
                orderView.customerName,
                orderView.customerEmail,
                orderView.customerPhone,
                orderView.quantity
            )
        )
    }
}