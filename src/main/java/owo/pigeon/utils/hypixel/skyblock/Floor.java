package owo.pigeon.utils.hypixel.skyblock;

public enum Floor {
    E("catacombs_floor_entrance"),
    F1("catacombs_floor_one"),
    F2("catacombs_floor_two"),
    F3("catacombs_floor_three"),
    F4("catacombs_floor_four"),
    F5("catacombs_floor_five"),
    F6("catacombs_floor_six"),
    F7("catacombs_floor_seven"),
    M1("master_catacombs_floor_one"),
    M2("master_catacombs_floor_two"),
    M3("master_catacombs_floor_three"),
    M4("master_catacombs_floor_four"),
    M5("master_catacombs_floor_five"),
    M6("master_catacombs_floor_six"),
    M7("master_catacombs_floor_seven");

    private final String floorID;

    Floor(String floorID) {
        this.floorID = floorID;
    }

    public String getFloorID() {
        return floorID;
    }


}
