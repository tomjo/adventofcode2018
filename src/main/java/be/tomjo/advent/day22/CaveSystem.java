package be.tomjo.advent.day22;

import be.tomjo.advent.math.V2;
import be.tomjo.advent.util.GridUtil;

import java.util.HashMap;
import java.util.Map;

import static be.tomjo.advent.day22.RegionType.regionType;
import static be.tomjo.advent.util.GridUtil.charGridToString;
import static be.tomjo.advent.util.GridUtil.initCharGrid;

public class CaveSystem {

    private final int depth;
    private final V2 target;
    private final char[][] grid;

    private final Map<V2,Long> erosionLevelCache;

    public CaveSystem(int depth, V2 target) {
        this.erosionLevelCache = new HashMap<>();
        this.depth = depth;
        this.target = target;
        this.grid = initCharGrid(target.getXAsInt() + 1,
                target.getYAsInt() + 1,
                (x,y) -> getRegionType(new V2(x,y)).getRepresentation());
    }

    public long getCaveRiskLevel() {
        return getAreaRiskLevel(new V2(0,0), target);
    }

    private long getAreaRiskLevel(V2 min, V2 max) {
        long riskLevel = 0;
        for (int x = min.getXAsInt(); x <= max.getXAsInt(); x++) {
            for (int y = min.getYAsInt(); y <= max.getYAsInt(); y++) {
                riskLevel += regionType(grid[x][y]).getRiskLevel();
            }
        }
        return riskLevel;
    }

    private long geologicIndex(V2 region) {
        if (region.equals(new V2(0, 0)) || region.equals(target)) {
            return 0;
        }
        if (region.getY() == 0) {
            return region.getX() * 16807L;
        }
        if (region.getX() == 0) {
            return region.getY() * 48271L;
        }
        return erosionLevel(region.withOffsetX(-1)) * erosionLevel(region.withOffsetY(-1));
    }

    private long erosionLevel(V2 region) {
        return erosionLevelCache.computeIfAbsent(region, this::_erosionLevel);
    }

    private long _erosionLevel(V2 region) {
        return (geologicIndex(region) + depth) % 20183;
    }


    public RegionType getRegionType(V2 region) {
        return regionType(erosionLevel(region) % 3);
    }

    @Override
    public String toString() {
        return charGridToString(grid);
    }

    public V2 getTargetLocation() {
        return target;
    }
}
