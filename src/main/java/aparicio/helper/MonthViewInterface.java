package aparicio.helper;

import aparicio.model.Appointment;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;


/** This interface creates a multiple parameter abstract method for the month view. */
public interface MonthViewInterface {

    /**This method will create a lambda expression used to create a filtered list of appointments for the next 30 days.*/
    public abstract ObservableList<Appointment> next30Days(ObservableList<Appointment> allAList,
                                                           ObservableList<Appointment> list30, LocalDateTime currLdt,
                                                           LocalDateTime ldtIn30);
}
