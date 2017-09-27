import java.util.List;

/**
 * Created by Bortnik on 27.09.2017.
 */
public class Tokenization {

    static String testString = "один ...  !? два,,   три,   (четыре). пять,,!& шесть";
    static String delimeters = "./&!?, ();";

    public static void main(String[] args) {

        Tokenizer tokenizer = new Tokenizer(testString, delimeters);
        List<String> tokens = tokenizer.getTokens();
        System.out.println(tokens);
    }
}
