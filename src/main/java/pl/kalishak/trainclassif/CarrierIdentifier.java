package pl.kalishak.trainclassif;

import com.google.common.collect.ImmutableMap;
import pl.kalishak.trainclassif.util.StringHelper;

import java.awt.*;
import java.util.Map;

public record CarrierIdentifier(String localizedName, int internalId, Color color) {
    private static final Map<Integer, Character> SPECIAL_AFIX = ImmutableMap.of(
            3, 'W',
            4, 'S'
    );

    public String symbol() {
        boolean flag = SPECIAL_AFIX.containsKey(internalId);

        char[] symbol = StringHelper.getInitials(localizedName);

        if (flag) {
            symbol[symbol.length - 2] = '-';
            symbol[symbol.length - 1] = SPECIAL_AFIX.get(internalId);
        } else {
            symbol[symbol.length - 2] = symbol[symbol.length - 1] = '\0';
        }

        return new String(symbol).trim();
    }
}
