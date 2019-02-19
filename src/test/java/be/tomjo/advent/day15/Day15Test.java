package be.tomjo.advent.day15;

import be.tomjo.advent.math.V2;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

class Day15Test {


    @Test
    void example_part1_1() {
        String input = "#######\n" +
                "#.G...#\n" +
                "#...EG#\n" +
                "#.#.#G#\n" +
                "#..G#E#\n" +
                "#.....#\n" +
                "#######";

        Integer result = new Day15().rawPart1(input);

        assertThat(result).isEqualTo(27730);
    }

    @Test
    void example_part1_1_parts() {
        String input = "#######\n" +
                "#.G...#\n" +
                "#...EG#\n" +
                "#.#.#G#\n" +
                "#..G#E#\n" +
                "#.....#\n" +
                "#######";

        GameMapImpl map = new GameMapImpl(input);
        Game game = new Game(map);
        map.initialize(3);

        game.round();
        assertThat(map.toString()).isEqualTo("#######\n" +
                "#..G..#\n" +
                "#...EG#\n" +
                "#.#G#G#\n" +
                "#...#E#\n" +
                "#.....#\n" +
                "#######\n");
        assertThat(map.getUnitAt(new V2(3, 1)).map(Unit::getHitPoints)).contains(200);
        assertThat(map.getUnitAt(new V2(4, 2)).map(Unit::getHitPoints)).contains(197);
        assertThat(map.getUnitAt(new V2(5, 2)).map(Unit::getHitPoints)).contains(197);
        assertThat(map.getUnitAt(new V2(3, 3)).map(Unit::getHitPoints)).contains(200);
        assertThat(map.getUnitAt(new V2(5, 3)).map(Unit::getHitPoints)).contains(197);
        assertThat(map.getUnitAt(new V2(5, 4)).map(Unit::getHitPoints)).contains(197);

        game.round();//2
        assertThat(map.toString()).isEqualTo("#######\n" +
                "#...G.#\n" +
                "#..GEG#\n" +
                "#.#.#G#\n" +
                "#...#E#\n" +
                "#.....#\n" +
                "#######\n");
        assertThat(map.getUnitAt(new V2(4, 1)).map(Unit::getHitPoints)).contains(200);
        assertThat(map.getUnitAt(new V2(3, 2)).map(Unit::getHitPoints)).contains(200);
        assertThat(map.getUnitAt(new V2(4, 2)).map(Unit::getHitPoints)).contains(188);
        assertThat(map.getUnitAt(new V2(5, 2)).map(Unit::getHitPoints)).contains(194);
        assertThat(map.getUnitAt(new V2(5, 3)).map(Unit::getHitPoints)).contains(194);
        assertThat(map.getUnitAt(new V2(5, 4)).map(Unit::getHitPoints)).contains(194);

        for (int i = 0; i < 21; i++) {
            game.round();
        }

        assertThat(map.toString()).isEqualTo("#######\n" +
                "#...G.#\n" +
                "#..G.G#\n" +
                "#.#.#G#\n" +
                "#...#E#\n" +
                "#.....#\n" +
                "#######\n");
        assertThat(map.getUnitAt(new V2(4, 1)).map(Unit::getHitPoints)).contains(200);
        assertThat(map.getUnitAt(new V2(3, 2)).map(Unit::getHitPoints)).contains(200);
        assertThat(map.getUnitAt(new V2(5, 2)).map(Unit::getHitPoints)).contains(131);
        assertThat(map.getUnitAt(new V2(5, 3)).map(Unit::getHitPoints)).contains(131);
        assertThat(map.getUnitAt(new V2(5, 4)).map(Unit::getHitPoints)).contains(131);

        game.round();//24
        assertThat(map.toString()).isEqualTo("#######\n" +
                "#..G..#\n" +
                "#...G.#\n" +
                "#.#G#G#\n" +
                "#...#E#\n" +
                "#.....#\n" +
                "#######\n");
        assertThat(map.getUnitAt(new V2(3, 1)).map(Unit::getHitPoints)).contains(200);
        assertThat(map.getUnitAt(new V2(4, 2)).map(Unit::getHitPoints)).contains(131);
        assertThat(map.getUnitAt(new V2(3, 3)).map(Unit::getHitPoints)).contains(200);
        assertThat(map.getUnitAt(new V2(5, 3)).map(Unit::getHitPoints)).contains(128);
        assertThat(map.getUnitAt(new V2(5, 4)).map(Unit::getHitPoints)).contains(128);

        game.round();//25
        assertThat(map.toString()).isEqualTo("#######\n" +
                "#.G...#\n" +
                "#..G..#\n" +
                "#.#.#G#\n" +
                "#..G#E#\n" +
                "#.....#\n" +
                "#######\n");
        assertThat(map.getUnitAt(new V2(2, 1)).map(Unit::getHitPoints)).contains(200);
        assertThat(map.getUnitAt(new V2(3, 2)).map(Unit::getHitPoints)).contains(131);
        assertThat(map.getUnitAt(new V2(5, 3)).map(Unit::getHitPoints)).contains(125);
        assertThat(map.getUnitAt(new V2(3, 4)).map(Unit::getHitPoints)).contains(200);
        assertThat(map.getUnitAt(new V2(5, 4)).map(Unit::getHitPoints)).contains(125);

        game.round();//26
        assertThat(map.toString()).isEqualTo("#######\n" +
                "#G....#\n" +
                "#.G...#\n" +
                "#.#.#G#\n" +
                "#...#E#\n" +
                "#..G..#\n" +
                "#######\n");
        assertThat(map.getUnitAt(new V2(1, 1)).map(Unit::getHitPoints)).contains(200);
        assertThat(map.getUnitAt(new V2(2, 2)).map(Unit::getHitPoints)).contains(131);
        assertThat(map.getUnitAt(new V2(5, 3)).map(Unit::getHitPoints)).contains(122);
        assertThat(map.getUnitAt(new V2(5, 4)).map(Unit::getHitPoints)).contains(122);
        assertThat(map.getUnitAt(new V2(3, 5)).map(Unit::getHitPoints)).contains(200);

        game.round();//27
        assertThat(map.toString()).isEqualTo("#######\n" +
                "#G....#\n" +
                "#.G...#\n" +
                "#.#.#G#\n" +
                "#...#E#\n" +
                "#...G.#\n" +
                "#######\n");
        assertThat(map.getUnitAt(new V2(1, 1)).map(Unit::getHitPoints)).contains(200);
        assertThat(map.getUnitAt(new V2(2, 2)).map(Unit::getHitPoints)).contains(131);
        assertThat(map.getUnitAt(new V2(5, 3)).map(Unit::getHitPoints)).contains(119);
        assertThat(map.getUnitAt(new V2(5, 4)).map(Unit::getHitPoints)).contains(119);
        assertThat(map.getUnitAt(new V2(4, 5)).map(Unit::getHitPoints)).contains(200);

        game.round();//28
        assertThat(map.toString()).isEqualTo("#######\n" +
                "#G....#\n" +
                "#.G...#\n" +
                "#.#.#G#\n" +
                "#...#E#\n" +
                "#....G#\n" +
                "#######\n");
        assertThat(map.getUnitAt(new V2(1, 1)).map(Unit::getHitPoints)).contains(200);
        assertThat(map.getUnitAt(new V2(2, 2)).map(Unit::getHitPoints)).contains(131);
        assertThat(map.getUnitAt(new V2(5, 3)).map(Unit::getHitPoints)).contains(116);
        assertThat(map.getUnitAt(new V2(5, 4)).map(Unit::getHitPoints)).contains(113);
        assertThat(map.getUnitAt(new V2(5, 5)).map(Unit::getHitPoints)).contains(200);

        for (int i = 0; i < (47 - 28); i++) {
            game.round();
        }

        assertThat(map.toString()).isEqualTo("#######\n" +
                "#G....#\n" +
                "#.G...#\n" +
                "#.#.#G#\n" +
                "#...#.#\n" +
                "#....G#\n" +
                "#######\n");
        assertThat(map.getUnitAt(new V2(1, 1)).map(Unit::getHitPoints)).contains(200);
        assertThat(map.getUnitAt(new V2(2, 2)).map(Unit::getHitPoints)).contains(131);
        assertThat(map.getUnitAt(new V2(5, 3)).map(Unit::getHitPoints)).contains(59);
        assertThat(map.getUnitAt(new V2(5, 5)).map(Unit::getHitPoints)).contains(200);
    }

    @Test
    void movementExample() {
        long time = System.currentTimeMillis();
        String input = "#########\n" +
                "#G..G..G#\n" +
                "#.......#\n" +
                "#.......#\n" +
                "#G..E..G#\n" +
                "#.......#\n" +
                "#.......#\n" +
                "#G..G..G#\n" +
                "#########";

        GameMapImpl map = new GameMapImpl(input);
        Game game = new Game(map);
        map.initialize(3);
        game.round();
        assertThat(map.toString()).isEqualTo("#########\n" +
                "#.G...G.#\n" +
                "#...G...#\n" +
                "#...E..G#\n" +
                "#.G.....#\n" +
                "#.......#\n" +
                "#G..G..G#\n" +
                "#.......#\n" +
                "#########\n");
        game.round();
        assertThat(map.toString()).isEqualTo("#########\n" +
                "#..G.G..#\n" +
                "#...G...#\n" +
                "#.G.E.G.#\n" +
                "#.......#\n" +
                "#G..G..G#\n" +
                "#.......#\n" +
                "#.......#\n" +
                "#########\n");
        game.round();
        assertThat(map.toString()).isEqualTo("#########\n" +
                "#.......#\n" +
                "#..GGG..#\n" +
                "#..GEG..#\n" +
                "#G..G...#\n" +
                "#......G#\n" +
                "#.......#\n" +
                "#.......#\n" +
                "#########\n");
        long end = System.currentTimeMillis();
        System.out.println("time: "+(end-time));
    }

    @Test
    void example_part1_2() {
        String input = "#######\n" +
                "#G..#E#\n" +
                "#E#E.E#\n" +
                "#G.##.#\n" +
                "#...#E#\n" +
                "#...E.#\n" +
                "#######";

        Integer result = new Day15().rawPart1(input);

        assertThat(result).isEqualTo(36334);
    }

    @Test
    void example_part1_3() {
        String input = "#######\n" +
                "#E..EG#\n" +
                "#.#G.E#\n" +
                "#E.##E#\n" +
                "#G..#.#\n" +
                "#..E#.#\n" +
                "#######";

        Integer result = new Day15().rawPart1(input);

        assertThat(result).isEqualTo(39514);
    }

    @Test
    void example_part1_4() {
        String input = "#######\n" +
                "#E.G#.#\n" +
                "#.#G..#\n" +
                "#G.#.G#\n" +
                "#G..#.#\n" +
                "#...E.#\n" +
                "#######";

        Integer result = new Day15().rawPart1(input);

        assertThat(result).isEqualTo(27755);
    }

    @Test
    void example_part1_5() {
        String input = "#######\n" +
                "#.E...#\n" +
                "#.#..G#\n" +
                "#.###.#\n" +
                "#E#G#G#\n" +
                "#...#G#\n" +
                "#######";

        Integer result = new Day15().rawPart1(input);

        assertThat(result).isEqualTo(28944);
    }

    @Test
    void example_part1_6() {
        String input = "#########\n" +
                "#G......#\n" + "#.E.#...#\n" +
                "#..##..G#\n" +
                "#...##..#\n" +
                "#...#...#\n" +
                "#.G...G.#\n" +
                "#.....G.#\n" +
                "#########";

        Integer result = new Day15().rawPart1(input);

        assertThat(result).isEqualTo(18740);
    }

    @Test
    void hardMovementExample() {
        String input = "###########\n" +
                "#G..#....G#\n" +
                "###..E#####\n" +
                "###########";

        GameMap gameMap = new GameMapImpl(input);
        Game game = new Game(gameMap);
        gameMap.initialize(3);
        game.round();

        assertThat(gameMap.toString()).isEqualTo("###########\n" +
                "#.G.#...G.#\n" +
                "###.E.#####\n" +
                "###########\n");
    }
}