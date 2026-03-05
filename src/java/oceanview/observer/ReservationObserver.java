package oceanview.observer;

import oceanview.model.Reservation;

/**
 * Observer interface: Notified when reservation state changes.
 */
public interface ReservationObserver {
    void onReservationCreated(Reservation reservation);
    void onReservationCancelled(Reservation reservation);
}
