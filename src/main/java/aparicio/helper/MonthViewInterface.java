package aparicio.helper;

import aparicio.model.Appointment;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public interface MonthViewInterface {

    //Multiple parameter abstract method
    public abstract ObservableList<Appointment> next30Days(ObservableList<Appointment> allAList,
                                                           ObservableList<Appointment> list30, LocalDateTime currLdt,
                                                           LocalDateTime ldtIn30);
}
