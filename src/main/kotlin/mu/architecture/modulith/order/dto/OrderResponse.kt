package mu.architecture.modulith.order.dto

import mu.architecture.modulith.order.model.Status

data class OrderResponse(
    val customerName: String?,
    val orderNumber: String?,
    val deliveryAddress: String?,
    val productPrice: Double?,
    val quantity: Int?,
    val status: Status?
)
