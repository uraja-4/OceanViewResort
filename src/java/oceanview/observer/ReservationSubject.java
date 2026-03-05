package oceanview.observer;

import oceanview.model.Reservation;
import java.util.ArrayList;
import java.util.List;

/**
 * Subject in Observer pattern: Notifies observers when reservation is created or cancelled.
 */
public class ReservationSubject {
    private static final ReservationSubject instance = new ReservationSubject();
    private final List<ReservationObserver> observers = new ArrayList<>();

    private ReservationSubject() {}

    public static ReservationSubject getInstance() {
        return instance;
    }

    public void attach(ReservationObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void detach(ReservationObserver observer) {
        observers.remove(observer);
    }

    public void notifyReservationCreated(Reservation reservation) {
        for (ReservationObserver o : observers) {
            o.onReservationCreated(reservation);
        }
    }

    public void notifyReservationCancelled(Reservation reservation) {
        for (ReservationObserver o : observers) {
            o.onReservationCancelled(reservation);
        }
    }
}
