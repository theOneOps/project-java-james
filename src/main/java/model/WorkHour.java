package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class WorkHour implements Serializable {
    private static final long serialVersionUID = 1L;
    HashMap<LocalDate, ArrayList<LocalTime>> pointing = new HashMap<LocalDate, ArrayList<LocalTime>>();

    WorkHour()
    {

    }

    public void addWorkHour(LocalDate d, LocalTime h){}

    public ArrayList<LocalTime> getWorkHour(LocalDate d){ return pointing.get(d);}

}
