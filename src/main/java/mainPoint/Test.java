package mainPoint;

import model.Employee;
import model.Enterprise;
import model.WorkHour;

import java.time.LocalDate;
import java.time.LocalTime;

public class Test {

    public static void main(String []args) {

        WorkHour wh1 = new WorkHour();
        wh1.addWorkHour(LocalDate.now(), LocalTime.parse("06:30"));

        WorkHour wh2 = new WorkHour();
        wh2.addWorkHour(LocalDate.now(), LocalTime.parse("10:30"));

        Employee empOne = new Employee("john", "doe", "13:00", "14:00");
        empOne.setWorkHour(wh1);

        Employee empTwo = new Employee("jane", "wayne", "10:00", "19:00");
        empTwo.setWorkHour(wh2);


        Enterprise ent = new Enterprise("Microsoft");
        ent.addEmployee(empOne);
        ent.addEmployee(empTwo);

        ent.removeEmployee(empOne.getUuid());

        ent.modifyEmpAttributs(empTwo.getUuid(), "midorya", "deku");

        System.out.println(ent);
    }

}
