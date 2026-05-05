package mu.architecture.modulith.common.util

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class SoftDeleteConverter : AttributeConverter<Boolean, Char?> {

    override fun convertToDatabaseColumn(attribute: Boolean?): Char? {
        if (attribute == null) {
            return 'N'
        }
        return if (attribute) 'Y' else 'N'
    }

    override fun convertToEntityAttribute(dbData: Char?): Boolean {
        if (dbData == null) {
            return false
        }
        return dbData == 'Y'
    }
}