package oceanview.observer;

import oceanview.model.Reservation;

/**
 * Concrete Observer: Updates availability when reservations change.
 * (In a full implementation this could invalidate caches or update room counts.)
 */
public class AvailabilityObserver implements ReservationObserver {

    @Override
    public void onReservationCreated(Reservation reservation) {
        // Room availability is recalculated on demand from DB; here we could log or invalidate cache
    }

    @Override
    public void onReservationCancelled(Reservation reservation) {
        // Room becomes available again; DB state is source of truth
    }
}
