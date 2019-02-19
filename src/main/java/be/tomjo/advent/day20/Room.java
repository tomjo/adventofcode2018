package be.tomjo.advent.day20;

import be.tomjo.advent.math.V2;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Room {

    @ToString.Include
    @EqualsAndHashCode.Include
    private final V2 location;
    private Room north;
    private Room east;
    private Room south;
    private Room west;
    private boolean northAccessible;
    private boolean eastAccessible;
    private boolean southAccessible;
    private boolean westAccessible;

    public Room(V2 location){
        this.location = location;
    }

    public Collection<Room> getAdjacentConnectedRooms(){
        List<Room> rooms = new ArrayList<>();
        if(northAccessible){
            rooms.add(north);
        }
        if(eastAccessible){
            rooms.add(east);
        }
        if(westAccessible){
            rooms.add(west);
        }
        if(southAccessible){
            rooms.add(south);
        }
        return rooms;
    }

    public Room addWestRoom(){
        this.west = new Room(this.location.withOffsetX(-1));
        this.westAccessible = true;
        this.west.east = this;
        this.west.eastAccessible = true;
        return this.west;
    }

    public Room addEastRoom(){
        this.east = new Room(this.location.withOffsetX(1));
        this.eastAccessible = true;
        this.east.west = this;
        this.east.westAccessible = true;
        return this.east;
    }

    public Room addNorthRoom(){
        this.north = new Room(this.location.withOffsetY(-1));
        this.northAccessible = true;
        this.north.south = this;
        this.north.southAccessible = true;
        return this.north;
    }

    public Room addSouthRoom(){
        this.south = new Room(this.location.withOffsetY(1));
        this.southAccessible = true;
        this.south.north = this;
        this.south.northAccessible = true;
        return this.south;
    }
}
