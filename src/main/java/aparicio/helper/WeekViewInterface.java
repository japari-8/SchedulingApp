package aparicio.helper;

import aparicio.model.Appointment;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public interface WeekViewInterface {

    //Multiple parameter abstract method
    public abstract ObservableList<Appointment> next7Days (ObservableList<Appointment> allApList,
                                                           ObservableList<Appointment> list30D, LocalDateTime currenLdt,
                                                           LocalDateTime ldtIn30D);
}
