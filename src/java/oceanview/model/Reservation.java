package oceanview.model;

import java.math.BigDecimal;
import java.util.Date;

public class Reservation {
    private int reservationId;
    private String reservationNumber;
    private int guestId;
    private String guestName;
    private String guestAddress;
    private String guestContact;
    private int roomTypeId;
    private String roomTypeName;
    private BigDecimal ratePerNight;
    private Date checkInDate;
    private Date checkOutDate;
    private int nights;
    private BigDecimal totalCost;
    private String status; // CONFIRMED, CANCELLED, PENDING
    private String cancelledBy;
    private Date cancelledAt;
    private String cancelReason;
    private Date createdAt;

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }
    public String getReservationNumber() { return reservationNumber; }
    public void setReservationNumber(String reservationNumber) { this.reservationNumber = reservationNumber; }
    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }
    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }
    public String getGuestAddress() { return guestAddress; }
    public void setGuestAddress(String guestAddress) { this.guestAddress = guestAddress; }
    public String getGuestContact() { return guestContact; }
    public void setGuestContact(String guestContact) { this.guestContact = guestContact; }
    public int getRoomTypeId() { return roomTypeId; }
    public void setRoomTypeId(int roomTypeId) { this.roomTypeId = roomTypeId; }
    public String getRoomTypeName() { return roomTypeName; }
    public void setRoomTypeName(String roomTypeName) { this.roomTypeName = roomTypeName; }
    public BigDecimal getRatePerNight() { return ratePerNight; }
    public void setRatePerNight(BigDecimal ratePerNight) { this.ratePerNight = ratePerNight; }
    public Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }
    public Date getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }
    public int getNights() { return nights; }
    public void setNights(int nights) { this.nights = nights; }
    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCancelledBy() { return cancelledBy; }
    public void setCancelledBy(String cancelledBy) { this.cancelledBy = cancelledBy; }
    public Date getCancelledAt() { return cancelledAt; }
    public void setCancelledAt(Date cancelledAt) { this.cancelledAt = cancelledAt; }
    public String getCancelReason() { return cancelReason; }
    public void setCancelReason(String cancelReason) { this.cancelReason = cancelReason; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
