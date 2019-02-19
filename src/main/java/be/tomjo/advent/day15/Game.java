package be.tomjo.advent.day15;

import be.tomjo.advent.day15.pathfinding.*;
import be.tomjo.advent.math.V2;
import lombok.Value;

import java.util.*;

import static be.tomjo.advent.day15.EventLog.publishEvent;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

public class Game {

    private final GameMap gameMap;
    private int round = 0;

    public Game(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    private boolean isFinished() {
        return gameMap.getUnits().stream()
                .map(Unit::getType)
                .distinct()
                .count() < 2;
    }

    public void play() {
        while (!isFinished()) {
            if (round()) {
                round++;
            }
        }
    }

    public int getScore() {
        int sumHitPoints = gameMap.getUnits().stream()
                .mapToInt(Unit::getHitPoints)
                .sum();
        return round * sumHitPoints;
    }


    public boolean round() {
        TreeSet<Unit> tmp = new TreeSet<>(new UnitTurnOrderComparator());
        tmp.addAll(gameMap.getUnits());
        List<Unit> unitsInTurnOrder = new ArrayList<>(tmp);
        for (Unit unit : unitsInTurnOrder) {
            if (isFinished()) {
                return false;
            }
            if (unit.isDead()) {
                continue;
            }
            if (attack(unit)) {
                continue;
            }

            doMove(unit);
            attack(unit);
        }
        return true;
    }

    private void doMove(Unit unit) {
        Collection<Unit> possibleTargets = gameMap.getUnitsOfType(unit.getAttackableTypes());
        List<V2> moveableLocations = possibleTargets.stream()
                .map(Unit::getLocation)
                .map(gameMap::getMoveableLocationsInRangeOfLocation).flatMap(Collection::stream)
                .collect(toList());
        if (moveableLocations.isEmpty()) {
            return;
        }
        move(unit, moveableLocations);
    }

    private boolean attack(Unit unit) {
        Collection<Unit> possibleTargets = gameMap.getUnitsOfType(unit.getAttackableTypes());
        List<Unit> adjacentTargets = possibleTargets.stream()
                .filter(target -> gameMap.getLocationsInRange(unit.getLocation())
                        .stream()
                        .anyMatch(p -> target.getLocation().equals(p)))
                .collect(toList());
        if (adjacentTargets.isEmpty()) {
            return false;
        }

        Unit target = Collections.min(adjacentTargets,
                comparingInt(Unit::getHitPoints).thenComparing(new UnitTurnOrderComparator()));
        if (target.getHitPoints() - unit.getAttackPower() > 0) {
            target.damage(unit.getAttackPower());
        } else {
            target.damage(unit.getAttackPower());
            gameMap.removeUnit(target);
        }
        return true;
    }

    private boolean move(Unit unit, List<V2> moveableLocations) {
//        PathFinder pathFinder = new BFSPathFinder(gameMap);
//        PathFinder pathFinder = new YenPathFinder(gameMap, unit.getLocation());
        DijkstraPathFinder dijkstraPathFinder = new DijkstraPathFinder(gameMap, unit.getLocation());
        Optional<V2> v2 = dijkstraPathFinder.calculateMove(moveableLocations);
        if(v2.isPresent()){
            gameMap.moveUnit(unit, v2.get());
            publishEvent("MoveActualLocation " + v2.get());
            return true;
        }
        return false;
//
//        Map<V2, Collection<Path>> pathMap = new HashMap<>();
//        for (V2 moveableLocation : moveableLocations) {
//            Collection<Path> bestPaths = pathFinder.findPaths(unit.getLocation(), moveableLocation, Game::manhattanDistanceToPoint);
//            if (!bestPaths.isEmpty()) {
//                pathMap.put(moveableLocation, bestPaths);
//            }
//        }
//        if (pathMap.isEmpty()) {
//            return false;
//        }
//        int minCost = pathMap.values().stream()
//                .map(c -> c.iterator().next())
//                .mapToInt(Path::getCost)
//                .min()
//                .orElseThrow(IllegalStateException::new);
//        V2 targetLocation = pathMap.entrySet().stream()
//                .filter(e -> e.getValue().iterator().next().getCost() == minCost)
//                .map(Map.Entry::getKey)
//                .min(new LocationComparator())
//                .orElseThrow(IllegalStateException::new);
//        publishEvent("MoveTargetLocation " + unit + " " + targetLocation);
//
//        Path path = pathMap.get(targetLocation).stream()
//                .min((o1, o2) -> new LocationComparator().compare(o1.getSteps().get(1), o2.getSteps().get(1)))
//                .orElseThrow(IllegalStateException::new);
//        gameMap.moveUnit(unit, path.getSteps().get(1));
//        publishEvent("MoveActualLocation " + path.getSteps().get(1));
//        return true;
    }

    private static int manhattanDistanceToPoint(V2 p, V2 q) {
        return (Math.abs(p.getXAsInt() - q.getXAsInt()) + Math.abs(p.getYAsInt() - q.getYAsInt()));
    }

    @Value
    public static class Tuple<A, B> {
        private A a;
        private B b;
    }
}
