package dispatcher;

import controller.Controller;
import peopleService.PeopleService;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Dispatcher {
    public static void start() {
        new Thread(() -> {
            while (true) {
               while (!(Controller.queue.size() == 0)) {
                   Map<String, Object> attributes = Controller.queue.poll();
                   String action = (String) attributes.get("action");
                   String type = (String) attributes.get("type");
                   if (action.equals("create")) {
                       if (type.equals("teacher")) {
                           try {
                               PeopleService.createTeacher(attributes);
                           } catch (IOException e) {System.out.println("Error in dispatcher create teacher");}
                       } else {
                           try {
                               PeopleService.createStudent(attributes);
                           } catch (IOException e) {System.out.println("Error in dispatcher create student");}
                       }
                   } else if (action.equals("edit")) {
                       try {
                           PeopleService.edit(attributes);
                       } catch (IOException e) {System.out.println("Error in dispatcher edit");}
                   } else {
                       PeopleService.delete((int) attributes.get("id"));
                   }
               }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }
}

