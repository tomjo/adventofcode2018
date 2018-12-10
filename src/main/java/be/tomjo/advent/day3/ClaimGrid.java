package be.tomjo.advent.day3;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ClaimGrid {

    private final Map<Integer, Set<Integer>> overlappingClaims;
    private final List<Integer>[][] claimGrid;

    public ClaimGrid(int width, int height, Collection<Claim> claims) {
        overlappingClaims = new HashMap<>(claims.size());
        claimGrid = new List[width][height];
        addClaims(claims);
    }

    public int calculateAreaInMoreThanOneClaim(){
        int count = 0;
        for (int x = 0; x < claimGrid.length; x++) {
            for (int y = 0; y < claimGrid[0].length; y++) {
                if(claimGrid[x][y] != null && claimGrid[x][y].size() > 1){
                    count++;
                }
            }
        }
        return count;
    }

    public Collection<Integer> getNonOverlappingClaims(){
        return overlappingClaims.entrySet().stream()
                .filter(e -> e.getValue().isEmpty())
                .map(Map.Entry::getKey)
                .collect(toList());
    }

    private void addClaims(Collection<Claim> claims){
        for (Claim claim : claims) {
            overlappingClaims.put(claim.getId(), new HashSet<>());
            for (int x = claim.getX(); x < claim.getX() + claim.getWidth(); x++) {
                for (int y = claim.getY(); y < claim.getY() + claim.getHeight(); y++) {
                    initGridLocation(x, y);
                    storeOverlappingClaims(claim, x, y);
                    addClaim(claim, x, y);
                }
            }
        }
    }

    private void addClaim(Claim claim, int x, int y) {
        claimGrid[x][y].add(claim.getId());
    }

    private void storeOverlappingClaims(Claim claim, int x, int y) {
        for (int claimOverlappingGrid : claimGrid[x][y]) {
            overlappingClaims.get(claimOverlappingGrid).add(claim.getId());
            overlappingClaims.get(claim.getId()).add(claimOverlappingGrid);
        }
    }

    private void initGridLocation(int x, int y) {
        if (claimGrid[x][y] == null) {
            claimGrid[x][y] = new ArrayList<>();
        }
    }
}
