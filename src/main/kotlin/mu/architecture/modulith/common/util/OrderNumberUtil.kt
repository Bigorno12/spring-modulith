package mu.architecture.modulith.common.util

import java.security.SecureRandom
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object OrderNumberUtil {

    val random: SecureRandom = SecureRandom()
    val CHARSET = (('A'..'Z') + ('0'..'9')).joinToString("")

    fun generateOrderNumber(): String {
        val date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val suffix = (1..5)
            .map { CHARSET[random.nextInt(CHARSET.length)] }
            .joinToString("")
        return "ORD-$date-$suffix"
    }
}