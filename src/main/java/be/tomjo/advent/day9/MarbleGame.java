package be.tomjo.advent.day9;

import static java.util.Arrays.stream;

public class MarbleGame {

    private final int playerCount;
    private final int highestMarble;
    private final int magicMarbleValue;
    private final int magicMarbleRemovalOffset;

    private MarbleCircle marbleCircle;
    private long[] scores;
    private int lastMarblePlayed;

    public MarbleGame(MarbleGameInput gameInput, MarbleCircle marbleCircle, int marbleModifier) {
        this.playerCount = gameInput.getPlayerCount();
        this.highestMarble = gameInput.getHighestMarbleValue()*marbleModifier;
        this.magicMarbleValue = gameInput.getMagicMarbleValue();
        this.magicMarbleRemovalOffset = gameInput.getMagicMarbleRemovalOffset();
        this.marbleCircle = marbleCircle;
        this.scores = new long[playerCount];
    }

    public void playRound(){
        if(++lastMarblePlayed % magicMarbleValue == 0){
            int removedMarble = marbleCircle.removeMarbleAtOffsetOfCurrentMarble(magicMarbleRemovalOffset);
            addScoreToCurrentPlayer(lastMarblePlayed+removedMarble);
        }else {
            marbleCircle.addMarble(lastMarblePlayed);
        }
    }

    public boolean isFinished() {
        return lastMarblePlayed == highestMarble;
    }

    private void addScoreToCurrentPlayer(int marbleToPlay) {
        this.scores[lastMarblePlayed % playerCount] += marbleToPlay;
    }

    public long getHighestScore(){
        return stream(scores)
                .max()
                .orElseThrow(IllegalStateException::new);
    }
}
