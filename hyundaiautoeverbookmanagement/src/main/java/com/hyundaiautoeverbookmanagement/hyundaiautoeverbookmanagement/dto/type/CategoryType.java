package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type;

public enum CategoryType {
    HOME_COOKING_BEAUTY("가정/요리/뷰티"),
    HEALTH_HOBBY_LEISURE("건강/취미/레저"),
    ECONOMICS_MANAGEMENT("경제경영"),
    HIGH_SCHOOL_REFERENCE_BOOK("고등학교참고서"),
    CLASSIC("고전"),
    SCIENCE("과학"),
    CALENDAR_ETC("달력/기타"),
    UNIVERSITY_TEXTBOOK_PROFESSIONAL_BOOK("대학교재/전문서적"),
    COMICS("만화"),
    SOCIAL_SCIENCE("사회과학"),
    NOVEL_POETRY_PLAY("소설/시/희곡"),
    CANDIDATES_CERTIFICATION("수험생/자격증"),
    CHILDREN("어린이"),
    ESSAY("에세이"),
    TRAVEL("여행"),
    HISTORY("역사"),
    ART_POP_CULTURE("예술/대중문화"),
    FOREIGN_LANGUAGE("외국어"),
    INFANTS("유아"),
    HUMANITIES("인문학"),
    SELF_DEVELOPMENT("자기계발"),
    MAGAZINE("잡지"),
    COLLECTION_USED_COLLECTION("전집/중고전집"),
    MIDDLE_SCHOOL_STUDY("중교/역학"),
    MIDDLE_SCHOOL_REFERENCE_BOOK("중학생참고서"),
    ADOLESCENTS("청소년"),
    ELEMENTARY_SCHOOL_REFERENCE_BOOK("초등학교참고서"),
    COMPUTER_MOBILE("컴퓨터/모바일"),
    GIFT("Gift");

    private final String description;

    CategoryType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static CategoryType ToEnglish(String description) {
        for (CategoryType category : CategoryType.values()) {
            if (category.getDescription().equals(description)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + description + " found");
    }

    public static String ToKorean(String name) {
        for (CategoryType category : values()) {
            if (category.name().equals(name)) {
                return category.getDescription();
            }
        }
        throw new IllegalArgumentException("No enum constant with name " + name + " found");
    }

}
