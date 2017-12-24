package regex;

import java.util.regex.*;

public class RegexPracticeExercise {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("([A-Z])([\\d]{2,4})(\\s)?");
        String s = "A25 K150 Z228 D4679 J67";
        Matcher matcher = p.matcher(s);
        StringBuffer res = new StringBuffer();
        while (matcher.find()) {
            res.append(matcher.group(2));
            res.append(matcher.group(1));
            if (matcher.group(3) != null)
                res.append(matcher.group(3));
        }
        System.out.println(res.toString());
    }
}
