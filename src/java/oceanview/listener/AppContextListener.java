package oceanview.listener;

import oceanview.observer.AvailabilityObserver;
import oceanview.observer.ReservationSubject;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 * Registers Observer with Subject on application startup.
 */
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ReservationSubject.getInstance().attach(new AvailabilityObserver());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
