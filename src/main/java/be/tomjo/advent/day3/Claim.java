package be.tomjo.advent.day3;

import lombok.Value;

@Value
public class Claim {

    private final int id;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Claim(String serialized){
        String[] parts = serialized.split(" ");
        this.id = Integer.parseInt(parts[0].substring(1));
        String[] offsetParts = parts[2].split(",");
        this.x = Integer.parseInt(offsetParts[0]);
        this.y = Integer.parseInt(offsetParts[1].replace(":", ""));
        String[] sizeParts = parts[3].split("x");
        this.width = Integer.parseInt(sizeParts[0]);
        this.height = Integer.parseInt(sizeParts[1]);
    }
}
