package be.tomjo.advent.day13;

import be.tomjo.advent.math.V2;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MineCart {
    @EqualsAndHashCode.Include
    private final int id;
    private V2 location;
    private Direction direction;
    private TurnState turnState;

    public void moveOverTrack(char track) {
        switch (track) {
            case '|':
            case '-':
                location = location.add(direction.getRawVector());
                break;
            case '\\':
            case '/':
                direction = direction.turn(track);
                location = location.add(direction.getRawVector());
                break;
            case '+':
                direction = direction.turn(turnState);
                location = location.add(direction.getRawVector());
                turnState = turnState.nextTurnState();
                break;
        }
    }
}
