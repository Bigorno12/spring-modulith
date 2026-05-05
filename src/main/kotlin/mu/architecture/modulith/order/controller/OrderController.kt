package mu.architecture.modulith.order.controller

import mu.architecture.modulith.order.dto.OrderSaveRequest
import mu.architecture.modulith.order.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(version = "1.0", value = ["/api/v1/order"], produces = ["application/json"])
class OrderController(private val orderService: OrderService) {

    @PostMapping
    fun save(@RequestBody orderSaveRequest: OrderSaveRequest) : ResponseEntity<Void> {
        orderService.save(orderSaveRequest)
        return ResponseEntity.ok().build()
    }
}