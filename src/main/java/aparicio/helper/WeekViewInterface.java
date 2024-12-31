package aparicio.helper;

import aparicio.model.Appointment;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

/** This interface creates a multiple parameter abstract method for the week view. */
public interface WeekViewInterface {

    /**This method will create a lambda expression used to create a filtered list of appointments for the next 7 days.*/
    public abstract ObservableList<Appointment> next7Days (ObservableList<Appointment> allApList,
                                                           ObservableList<Appointment> list30D, LocalDateTime currenLdt,
                                                           LocalDateTime ldtIn30D);
}
