package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type;

import jakarta.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CategoryTypeConverter implements AttributeConverter<CategoryType, String> {

    @Override
    public String convertToDatabaseColumn(CategoryType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDescription();
    }

    @Override
    public CategoryType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return CategoryType.CALENDAR_ETC;
        }
        return CategoryType.ToEnglish(dbData);
    }
}

