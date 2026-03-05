package oceanview.model;

import java.math.BigDecimal;

public class RoomType {
    private int roomTypeId;
    private String typeName;
    private BigDecimal ratePerNight;
    private int totalRooms;
    private String description;

    public int getRoomTypeId() { return roomTypeId; }
    public void setRoomTypeId(int roomTypeId) { this.roomTypeId = roomTypeId; }
    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public BigDecimal getRatePerNight() { return ratePerNight; }
    public void setRatePerNight(BigDecimal ratePerNight) { this.ratePerNight = ratePerNight; }
    public int getTotalRooms() { return totalRooms; }
    public void setTotalRooms(int totalRooms) { this.totalRooms = totalRooms; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
