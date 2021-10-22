package dispatcher;

import controller.Controller;
import peopleService.PeopleService;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class Dispatcher {
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
                           } catch (IOException e) {e.printStackTrace();}
                       } else {
                           try {
                               PeopleService.createStudent(attributes);
                           } catch (IOException e) {e.printStackTrace();}
                       }
                   } else if (action.equals("edit")) {
                       try {
                           PeopleService.edit(attributes);
                       } catch (IOException e) {e.printStackTrace();}
                   } else {
                       try {
                           PeopleService.delete((int) attributes.get("id"));
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
               }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }
}

