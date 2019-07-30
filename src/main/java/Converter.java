import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Converter {

    private static Map<String, String> periodMapping;
    private static String preMatchConstraint = "0:00.000";
    private static String halfTimeConstraint = "45:00.000";
    private static String fullTimeConstraint = "90:00.000";
    private final int MIN_LENGTH = 13;
    private final int MAX_LENGTH = 14;
    private static Pattern TIME_PATTERN = Pattern.compile("\\s\\d{1,2}:\\d{2}.\\d{3}$");

    static {
        periodMapping = new HashMap<>();
        periodMapping.put("PM", "PRE_MATCH");
        periodMapping.put("H1", "FIRST_HALF");
        periodMapping.put("HT", "HALF_TIME");
        periodMapping.put("H2", "SECOND_HALF");
        periodMapping.put("FT", "FULL_TIME");
    }

    List<String> convert(List<String> lines){
        return process((markInvalid(lines)));
    }

    private List<String> markInvalid(List<String> lines){
        List<String> filteredLines = new ArrayList<>();
        for(String line : lines){
            filteredLines.add(filter(line));
        }
        return filteredLines;
    }

    private String filter(String line){
        if(isValid(line))
            return line;
        return "INVALID";
    }

    private boolean isValid(String line){
        int length = line.length();

        if(length < MIN_LENGTH || length > MAX_LENGTH)
            return false;

        if(line.charAt(0) != '[' || line.charAt(3) != ']')
            return false;

        String bracketedTerm = line.substring(1,3);
        if(!periodMapping.containsKey(bracketedTerm))
            return false;

        String timeStamp = line.substring(4, length);
        if(!matches(timeStamp))
            return false;

        if(!isDeadBallTimeCorrect(bracketedTerm, timeStamp.trim()))
            return false;

        return true;
    }

    private boolean isDeadBallTimeCorrect(String bracketedTerm, String timeStamp){
        if(bracketedTerm.equals("PM") && !timeStamp.equals(preMatchConstraint)){
            return false;
        } else if (bracketedTerm.equals("HT") && !timeStamp.equals(halfTimeConstraint)){
            return false;
        } else if (bracketedTerm.equals("FT") && !timeStamp.equals(fullTimeConstraint)){
            return false;
        }
        return true;
    }

    private boolean matches(String time) {
        return TIME_PATTERN.matcher(time).matches();
    }

    private List<String> process(List<String> lines) {
        List<String> processedList = new ArrayList<>();
        for (String line : lines) {
            if (line.equals("INVALID")) {
                processedList.add(line);
            } else {
                String periodName = line.substring(1, 3);
                String processedLine = formatTime(line.substring(5, line.length()), periodName) + " - " + periodMapping.get(periodName);
                processedList.add(processedLine);
            }
        }
        return processedList;
    }

    private String formatTime(String time, String period){
        if (time.indexOf(":") < 2)
            time = padWithZero(time);

        time = replaceDotWithColon(time);
        String[] parts = time.split(":");

        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);
        int millis = Integer.parseInt(parts[2]);

        return formatTime(minutes, seconds, minutesInPeriod(period, minutes), roundToMinute(millis), isExtraTime(period, minutes));
    }

    private String formatTime(int minutes, int seconds, int period, int offset, boolean appendPeriod){
        return (appendPeriod ? (period + ":00 +") : "") + formatNormalTime(minutes, seconds, period, offset);
    }

    private String formatNormalTime(int minutes, int seconds, int period, int offset){
        return String.format("%02d", (minutes - period)) + ":" + String.format("%02d", (seconds + offset));
    }

    private String padWithZero(String time){
        return "0" + time;
    }

    private String replaceDotWithColon(String time){
        return time.replace(".", ":");
    }

    private int roundToMinute(int millis){
        return millis >= 500?  1 : 0;
    }

    private int minutesInPeriod(String period, int minutes){
        if (period.equals("H1") && isExtraTime(period, minutes))
            return 45;
        else if (period.equals("H2") && isExtraTime(period, minutes))
            return 90;
        return 0;
    }

    private boolean isExtraTime(String periodName, int minutes){
        return (periodName.equals("H1") && minutes >= 45) || (periodName.equals("H2") && minutes >= 90);
    }
}
