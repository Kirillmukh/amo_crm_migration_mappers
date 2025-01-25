package com.example.dbeaver_migration_mappers.enums.contact;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum ContactEvent implements ValueEnum {
    KO24_2D(1783171, "KO24/2д"),
    KO24_2Y(1783173, "KO24/2у"),
    KO24_2(1783175, "KO24/2"),
    ES24_2D(1783177, "ES24/2д"),
    ES24_2Y(1783179, "ES24/2у"),
    ES24_2(1783181, "ES24/2"),
    MDA24_2D(1783183, "MDA24/2д"),
    MDA24_2Y(1783185, "MDA24/2у"),
    MDA24_2(1783187, "MDA24/2"),
    IT24_2D(1783189, "IT24/2д"),
    IT24_2Y(1783191, "IT24/2у"),
    IT24_2(1783193, "IT24/2"),
    PAF24_2D(1783195, "PAF24/2д"),
    PAF24_2Y(1783197, "PAF24/2у"),
    PAF24_2(1783199, "PAF24/2"),
    B2B24_2D(1783201, "B2B24/2д"),
    B2B24_2Y(1783203, "B2B24/2у"),
    B2B24_2(1783205, "B2B24/2"),
    UP24_1D(1793627, "UP24/1д"),
    fPM24_1Y(1793629, "fPM24/1у"),
    PM24_1Y(1793631, "PM24/1у"),
    PM24_1K(1793633, "PM24/1к"),
    UP24_1YK(1793635, "UP24/1ук"),
    UP24_1Y(1793637, "UP24/1у"),
    fPM24_1YC(1793639, "fPM24/1ус"),
    PM24_1D(1793641, "PM24/1д"),
    fTM24_1K(1793643, "fTM24/1к"),
    UZ24_1Y(1793645, "UZ24/1у"),
    fTM24_1Y(1793647, "fTM24/1у"),
    UZ24_1P(1793649, "UZ24/1п"),
    wUZ24_1Y(1793651, "wUZ24/1у"),
    wCS24_1YK(1793653, "wCS24/1ук"),
    fCS24_1YK(1793655, "fCS24/1ук"),
    PM24_1DS(1793657, "PM24/1дс"),
    UZ24_1K(1793659, "UZ24/1к"),
    TM24_1Y(1793661, "TM24/1у"),
    CS24_1Y(1793663, "CS24/1у"),
    UP24_1K(1793665, "UP24/1к"),
    UZ24_1D(1793667, "UZ24/1д"),
    UZ23_1D(1793669, "UZ23/1д"),
    CS24_1K(1793671, "CS24/1к"),
    UZ24_1YK(1793673, "UZ24/1ук"),
    PM24_1YK(1793675, "PM24/1ук"),
    TM24_1YK(1793677, "TM24/1ук"),
    CS24_1D(1793679, "CS24/1д"),
    wTM24_1YK(1793681, "wTM24/1ук"),
    wUZ24_1YK(1793683, "wUZ24/1ук"),
    MDA22_2D(1793685, "MDA22/2д"),
    EC21_2D(1793687, "EC21/2д"),
//    UZ24_1Y(1793689, "UZ24/1у"),
    CB19_2D(1793691, "CB19/2д"),
    CB14Y(1793693, "CB14у"),
    CG13_2Y(1793695, "CG13/2у"),
    CB14_2D(1793697, "CB14/2д"),
    CBG15D(1793699, "CBG15д"),
    wCS24_1K(1793701, "wCS24/1к"),
//    fCS24_1YK(1793703, "fCS24/1ук"),
    fUZ24_1YK(1793705, "fUZ24/1ук"),
    wCS24_1Y(1793707, "wCS24/1у"),
    CS24_1YK(1793709, "CS24/1ук"),
    fPM24_1YKC(1793711, "fPM24/1укс"),
    fCS24_1K(1793713, "fCS24/1к"),
    TM24_1K(1793715, "TM24/1к"),
    fTM24_1YK(1793717, "fTM24/1ук"),
    wPM24_1Y(1793719, "wPM24/1у"),
    fPM24_1YK(1793721, "fPM24/1ук"),
    PM21_1D(1793723, "PM21/1д"),
    fPM24_1K(1793725, "fPM24/1к"),
    fCS24_1Y(1793727, "fCS24/1у"),
    CS24_1G(1793729, "CS24/1г");
    private final int enumId;
    private final String value;
    public static final int fieldId = 3022555;
    public static ContactEvent of(String value) {
        for (ContactEvent ce : values()) {
            if (value.equals(ce.getValue())) {
                return ce;
            }
        }
        throw new IllegalArgumentException("Wrong value for ContactEvent: " + value);
    }
    private static final Set<String> values = Arrays.stream(values()).map(ContactEvent::getValue).collect(Collectors.toSet());
    public static boolean contains(String value) {
        return values.contains(value);
    }
}
