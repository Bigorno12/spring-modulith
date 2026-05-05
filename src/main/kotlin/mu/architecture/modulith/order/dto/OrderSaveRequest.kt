package mu.architecture.modulith.order.dto

data class OrderSaveRequest(
    val customerName: String?,
    val customerEmail: String?,
    val customerPhone: String?,
    val deliveryAddress: String?,
    val productCode: String?,
    val productName: String?,
    val productPrice: Double?,
    val quantity: Int?,
    val comments: String?
) {
    init {
        requireNotNull(customerName) { "customerName must not be null" }
        requireNotNull(customerEmail) { "customerEmail must not be null" }
        requireNotNull(customerPhone) { "customerPhone must not be null" }
        requireNotNull(productCode) { "productCode must not be null" }
        requireNotNull(productName) { "productName must not be null" }
        requireNotNull(productPrice) { "productPrice must not be null" }
        require(quantity != null) { "quantity must not be null" }
    }
}