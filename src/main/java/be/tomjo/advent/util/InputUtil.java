package be.tomjo.advent.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

@UtilityClass
public class InputUtil {

    public static String readInput(String name){
        try {
            return IOUtils.toString(InputUtil.class.getClassLoader().getResourceAsStream(name), "UTF8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
