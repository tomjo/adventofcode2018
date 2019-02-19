package be.tomjo.advent.day13;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class TrackSystem {
    private final char[][] tracks;
    private final List<MineCart> mineCarts;
    private final List<Crash> crashes;

    private static final Comparator<MineCart> MINE_CART_MOVE_ORDER_COMPARATOR =
            Comparator.<MineCart>comparingInt(m -> m.getLocation().getYAsInt())
                .thenComparing(m -> m.getLocation().getXAsInt());


    public TrackSystem(char[][] tracks, List<MineCart> mineCarts) {
        this.tracks = tracks;
        this.mineCarts = mineCarts;
        this.crashes = new ArrayList<>();
    }

    public Crash moveCartsUntilCrash() {
        crashes.clear();
        List<MineCart> carts = new ArrayList<>(mineCarts);
        while (crashes.isEmpty()) {
            for (MineCart cart : carts.stream().sorted(MINE_CART_MOVE_ORDER_COMPARATOR).collect(toList())) {
                if (crashes.size() == 1) {
                    return crashes.get(0);
                }
                moveCarts(carts, cart);
            }
        }
        return null;
    }

    public MineCart moveCartsUntilOneMineCartRemains() {
        crashes.clear();
        List<MineCart> carts = new ArrayList<>(mineCarts);
        while (carts.size() > 1) {
            for (MineCart cart : carts.stream().sorted(MINE_CART_MOVE_ORDER_COMPARATOR).collect(toList())) {
                moveCarts(carts, cart);
            }
        }
        return carts.get(0);
    }

    private void moveCarts(List<MineCart> carts, MineCart cart) {
        move(cart);
        for (List<MineCart> crashedCartSet : getCrashedMineCartGroups(carts)) {
            carts.removeAll(crashedCartSet);
            crashes.add(new Crash(crashedCartSet.get(0), crashedCartSet.get(1)));
        }
    }

    private List<List<MineCart>> getCrashedMineCartGroups(List<MineCart> carts) {
        return carts.stream()
                .collect(groupingBy(MineCart::getLocation))
                .entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .map(Map.Entry::getValue)
                .collect(toList());
    }

    private void move(MineCart cart) {
        char track = tracks[cart.getLocation().getXAsInt()][cart.getLocation().getYAsInt()];
        cart.moveOverTrack(track);
    }
}
