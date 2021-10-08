import controller.Controller;
import dispatcher.Dispatcher;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) throws IOException {
        Controller.start();
        Dispatcher.start();
    }
}
