package com.example.dbeaver_migration_mappers.enums.company;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FirstLetter implements ValueEnum {
    RUS1(1766251, 'а'),
    RUS2(1766253, 'б'),
    RUS3(1766255, 'в'),
    RUS4(1766257, 'г'),
    RUS5(1766259, 'д'),
    RUS6(1766261, 'е'),
    RUS7(1766263, 'ё'),
    RUS8(1766265, 'ж'),
    RUS9(1766267, 'з'),
    RUS10(1766269, 'и'),
    RUS11(1766271, 'й'),
    RUS12(1766273, 'к'),
    RUS13(1766275, 'л'),
    RUS14(1766277, 'м'),
    RUS15(1766279, 'н'),
    RUS16(1766281, 'о'),
    RUS17(1766283, 'п'),
    RUS18(1766285, 'р'),
    RUS19(1766287, 'с'),
    RUS20(1766289, 'т'),
    RUS21(1766291, 'у'),
    RUS22(1766293, 'ф'),
    RUS23(1766295, 'х'),
    RUS24(1766297, 'ц'),
    RUS25(1766299, 'ч'),
    RUS26(1766301, 'ш'),
    RUS27(1766303, 'щ'),
    RUS28(1766305, 'ъ'),
    RUS29(1766307, 'ы'),
    RUS30(1766309, 'ь'),
    RUS31(1766311, 'э'),
    RUS32(1766313, 'ю'),
    RUS33(1766315, 'я');
    private final int enumId;
    private final char letter;
    public static final int fieldId = 3011273;
    public static FirstLetter of(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("FirstLetter: title is null or blank");
        }
        char letter = title.toLowerCase().charAt(0);
        for (FirstLetter fl : values()) {
            if (letter == fl.getLetter()) {
                return fl;
            }
        }
        throw new IllegalArgumentException("Wrong value for FirstLetter: " + title + "\n" +
                "Может быть название компании на английском языке");
    }
    @Override
    public String getValue() {
        return String.valueOf(letter);
    }
}
