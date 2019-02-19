package be.tomjo.advent.day15;

import be.tomjo.advent.math.V2;
import be.tomjo.advent.util.GridUtil;

import java.util.*;

import static be.tomjo.advent.util.CollectionUtil.not;
import static be.tomjo.advent.util.StringUtils.lines;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

public class GameMapImpl implements GameMap {

    private final char[][] map;
    private final Collection<Unit> units;

    public GameMapImpl(String serialized) {
        this.units = new ArrayList<>();
        List<String> rows = lines(serialized);
        int rowLength = rows.get(0).length();
        this.map = new char[rowLength][rows.size()];
        for (int y = 0; y < rows.size(); y++) {
            for (int x = 0; x < rowLength; x++) {
                this.map[x][y] = rows.get(y).charAt(x);
            }
        }
    }

    public GameMapImpl(GameMapImpl gameMap) {
        this.map = GridUtil.copyGrid(gameMap.map);
        this.units = new ArrayList<>(gameMap.units);
    }

    @Override
    public void initialize(int attackPower) {
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                if (this.map[x][y] == 'G') {
                    units.add(new Goblin(new V2(x, y)));
                } else if (this.map[x][y] == 'E') {
                    units.add(new Elf(new V2(x, y), attackPower));
                }
            }
        }
    }

    @Override
    public Optional<Unit> getUnitAt(V2 location) {
        return units.stream()
                .filter(u -> u.getLocation().equals(location))
                .findFirst();
    }

    @Override
    public boolean isObstructed(V2 location) {
        if (location.getXAsInt() < 0 || location.getXAsInt() >= map.length
                || location.getYAsInt() < 0 || location.getYAsInt() >= map[0].length) {
            return true;
        }
        return map[location.getXAsInt()][location.getYAsInt()] != '.';
    }

    @Override
    public List<Unit> getUnits() {
        return new ArrayList<>(this.units);
    }

    private static final Map<V2, Collection<V2>> locationInRangeCache = new HashMap<>();

    @Override
    public Collection<V2> getLocationsInRange(V2 location) {
        return locationInRangeCache.computeIfAbsent(location, this::_getLocationsInRange);
    }

    public Collection<V2> _getLocationsInRange(V2 location) {
        List<V2> locations = new ArrayList<>();
        locations.add(new V2(location.getXAsInt() - 1, location.getYAsInt()));
        locations.add(new V2(location.getXAsInt() + 1, location.getYAsInt()));
        locations.add(new V2(location.getXAsInt(), location.getYAsInt() - 1));
        locations.add(new V2(location.getXAsInt(), location.getYAsInt() + 1));
        return locations;
    }

    @Override
    public Collection<V2> getMoveableLocationsInRangeOfLocation(V2 location) {
        return getLocationsInRange(location).stream()
                .filter(not(this::isObstructed))
                .collect(toList());
    }

    @Override
    public int getWidth() {
        return map.length;
    }

    @Override
    public int getHeight() {
        return map[0].length;
    }

    @Override
    public void moveUnit(Unit unit, V2 location) {
        char c = map[location.getXAsInt()][location.getYAsInt()];
        checkArgument(c == '.');
        map[location.getXAsInt()][location.getYAsInt()] = unit.getType().getRepresentation();
        map[unit.getLocation().getXAsInt()][unit.getLocation().getYAsInt()] = '.';
        unit.move(location);
    }

    @Override
    public void removeUnit(Unit unit) {
        units.remove(unit);
        map[unit.getLocation().getXAsInt()][unit.getLocation().getYAsInt()] = '.';
    }

    @Override
    public GameMap copy() {
        return new GameMapImpl(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                sb.append(map[x][y]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }


}
