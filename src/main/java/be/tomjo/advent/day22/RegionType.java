package be.tomjo.advent.day22;

import lombok.Getter;

import java.util.Arrays;

public enum RegionType {
    ROCKY('.', 0, Tool.NEITHER),
    WET('=', 1, Tool.TORCH),
    NARROW('|', 2, Tool.CLIMBING_GEAR);

    private final char representation;
    private final int erosionLevel;
    private final Tool nonUseableTool;

    RegionType(char representation, int erosionLevel, Tool nonUseableTool) {
        this.representation = representation;
        this.erosionLevel = erosionLevel;
        this.nonUseableTool = nonUseableTool;
    }

    public static RegionType regionType(long erosionLevelOrRepresentation){
        return Arrays.stream(values())
                .filter(type -> type.getRepresentation() == erosionLevelOrRepresentation || type.getErosionLevel() == erosionLevelOrRepresentation)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    public char getRepresentation() {
        return representation;
    }

    public int getErosionLevel() {
        return erosionLevel;
    }

    public Tool getNonUseableTool() {
        return nonUseableTool;
    }

    public int getRiskLevel(){
        return getErosionLevel();
    }
}
