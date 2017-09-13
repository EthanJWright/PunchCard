/**
 * Created by ethanwright on 9/8/17.
 */
public class Main {
   public void hello(String getter) {
       System.out.println(getter);
   }








   public void main(String args[]) {
       CreateSchedule sched = new CreateSchedule();
       String hey = sched.getHey();

       hello(hey);
    }

}
