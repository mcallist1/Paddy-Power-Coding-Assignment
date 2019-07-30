import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterTest {

    private Converter converter;
    private static String INVALID = "INVALID";

    @BeforeEach
    void setUp(){
        converter = new Converter();
    }

    @Test
    void returnsPreMatch(){
        assertEquals("00:00 - PRE_MATCH", convertSingle("[PM] 0:00.000"));
    }

    @Test
    void returnsFirstHalf(){
        assertEquals("00:15 - FIRST_HALF", convertSingle("[H1] 0:15.000"));
    }

    @Test
    void returnsFirstHalfRounded(){
        assertEquals("03:08 - FIRST_HALF", convertSingle("[H1] 3:07.513"));
    }

    @Test
    void returnsFirstHalfExtraTimeRoundedDown(){
        assertEquals("45:00 +00:00 - FIRST_HALF", convertSingle("[H1] 45:00.001"));
    }

    @Test
    void returnsFirstHalfExtraTimeRoundedUp(){
        assertEquals("45:00 +01:16 - FIRST_HALF", convertSingle("[H1] 46:15.752"));
    }

    @Test
    void returnsHalfTime(){
        assertEquals("45:00 - HALF_TIME", convertSingle("[HT] 45:00.000"));
    }

    @Test
    void returnsSecondHalf(){
        assertEquals("45:01 - SECOND_HALF", convertSingle("[H2] 45:00.500"));
    }

    @Test
    void returnsSecondHalfExtraTime(){
        assertEquals("90:00 +00:01 - SECOND_HALF", convertSingle("[H2] 90:00.908"));
    }

    @Test
    void returnsFullTime(){
        assertEquals("90:00 - FULL_TIME", convertSingle("[FT] 90:00.000"));
    }

    @Test
    void returnsInvalidForNonZeroMillisPreMatchPeriod(){
        assertEquals(INVALID, convertSingle("[PM] 00:00.001"));
    }

    @Test
    void returnsInvalidForNonZeroSecondsPreMatchPeriod(){
        assertEquals(INVALID, convertSingle("[PM] 00:01.000"));
    }

    @Test
    void returnsInvalidForNonZeroMinutesPreMatchPeriod(){
        assertEquals(INVALID, convertSingle("[PM] 01:00.000"));
    }

    @Test
    void returnsInvalidForNon45HalfTime(){
        assertEquals(INVALID, convertSingle("[HT] 46:00.000"));
    }

    @Test
    void returnsInvalidForNon90FullTime(){
        assertEquals(INVALID, convertSingle("[FT] 91:00.000"));
    }

    @Test
    void returnsInvalidOnMalformedMilliseconds(){
        assertEquals(INVALID, convertSingle("[H1] 12:13.9999999999999999999999999999999999999999999"));
    }

    @Test
    void returnsInvalidOnMalformedPeriod(){
        assertEquals(INVALID, convertSingle("[H11] 12:13.000"));
    }

    @Test
    void returnsInvalidOnWrongFormat(){
        assertEquals(INVALID, convertSingle("H1] 12:13.000"));
    }

    @Test
    void returnsInValidOnTooManySpaces(){
        assertEquals(INVALID, convertSingle("[H1]      0:00.003"));
    }

    @Test
    void returnsInvalidOnMalformedTimeStamp(){
        assertEquals(INVALID, convertSingle("[H1] 0.00.003"));
    }

    @Test
    void returnsInvalidOnMalformedTimeStamp2(){
        assertEquals(INVALID, convertSingle("[H1] 0:00.0039"));
    }

    @Test
    void returnsInvalidOnMalformedTimeStamp3(){
        assertEquals(INVALID, convertSingle("[H1]0:00.003"));
    }

    @Test
    void returnsInvalidOnMalformedTimeStamp4(){
        assertEquals(INVALID, convertSingle("[H1]00:00.003"));
    }

    @Test
    void returnsInvalidOnMalformedTimeStamp5(){
        assertEquals(INVALID, convertSingle("[H1] 0:00.00"));
    }

    private String convertSingle(String shortForm){
        return converter.convert(Arrays.asList(shortForm)).get(0);
    }

}
