package aparicio.helper;

import java.time.*;
import java.util.TimeZone;

public class TimeConversion {
    private static ZoneId locZoneId = TimeZone.getDefault().toZoneId();


    public static LocalDateTime utcToLdt(LocalDateTime localdtInUtc) {

        //LocalDate dbLocalDAte = localdtInUtc.toLocalDate();
        //LocalTime dbLocalTime = localdtInUtc.toLocalTime();

        //FIXME:
        ZonedDateTime localToZdt = ZonedDateTime.of(localdtInUtc, ?);
        Instant insOfLocalZdt = localToZdt.toInstant();
        ZonedDateTime zdtCon = insOfLocalZdt.atZone(locZoneId);
        LocalDateTime zdtToLdt = zdtCon.toLocalDateTime();

        return zdtToLdt;
    }




    //public static () {

    //}


}


