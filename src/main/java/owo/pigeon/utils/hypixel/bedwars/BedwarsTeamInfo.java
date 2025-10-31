package owo.pigeon.utils.hypixel.bedwars;

public class BedwarsTeamInfo {
    public int getProtectionLevel() {
        return protectionLevel;
    }

    public void setProtectionLevel(int protectionLevel) {
        this.protectionLevel = protectionLevel;
    }

    public int getSharpnessLevel() {
        return sharpnessLevel;
    }

    public void setSharpnessLevel(int sharpnessLevel) {
        this.sharpnessLevel = sharpnessLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BedwarsTeams getTeam() {
        return team;
    }

    public void setTeam(BedwarsTeams team) {
        this.team = team;
    }

    private BedwarsTeams team;
    private String status;
    private int sharpnessLevel;
    private int protectionLevel;

    public BedwarsTeamInfo(BedwarsTeams team) {
        this.team = team;
        this.status = "✗";
        this.sharpnessLevel = 0;
        this.protectionLevel = 0;
    }


}
