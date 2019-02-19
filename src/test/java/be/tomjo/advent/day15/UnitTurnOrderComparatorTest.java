package be.tomjo.advent.day15;

import be.tomjo.advent.math.V2;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class UnitTurnOrderComparatorTest {

    @Test
    void unitsOrderedCorrectly() {
        Unit u1 = new Elf(new V2(2, 1), 3);
        Unit u2 = new Elf(new V2(4, 1), 3);
        Unit u3 = new Elf(new V2(1, 2), 3);
        Unit u4 = new Elf(new V2(3, 2), 3);
        Unit u5 = new Elf(new V2(5, 2), 3);
        Unit u6 = new Elf(new V2(2, 3), 3);
        Unit u7 = new Elf(new V2(4, 3), 3);
        List<Unit> units = asList(u1,u2,u3,u4,u5,u6,u7);

        Collections.sort(units, new UnitTurnOrderComparator());

        assertThat(units).containsExactly(u1,u2,u3,u4,u5,u6,u7);
    }
}