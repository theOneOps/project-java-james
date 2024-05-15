package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class WorkHour implements Serializable {
    private static final long serialVersionUID = 1L;
    HashMap<LocalDate, ArrayList<LocalTime>> pointing = new HashMap<LocalDate, ArrayList<LocalTime>>();

    public WorkHour()
    {

    }

    /**
     * @Param LocalDate d
     * @Param LocalTime h
     */
    public void addWorkHour(LocalDate d, LocalTime h){
        if (pointing.containsKey(d))
        {
            pointing.get(d).add(h);
        }
        else
        {
            ArrayList<LocalTime> refPointing = new ArrayList<>();
            refPointing.add(h);
            pointing.put(d, refPointing);
        }
    }

    /**
     * @Param LocalDate d
     * @return the corresponding arraylist for the date
     */
    public ArrayList<LocalTime> getWorkHour(LocalDate d){ return pointing.get(d);}

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        for(LocalDate date : pointing.keySet())
        {
            res.append(String.format(date.toString() + " " +pointing.get(date).toString()));
            res.append("\n");
        }
        return res.toString();
    }

}
