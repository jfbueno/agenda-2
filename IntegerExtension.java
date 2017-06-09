package agenda;

import java.lang.NumberFormatException;

public class IntegerExtension {
    public static boolean isSafeToParse(String value) {
        try {
            Integer.parseInt(value);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
}