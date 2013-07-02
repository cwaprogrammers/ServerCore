package com.cwa.qiji;

public class ActiveUserManagerConfig {
    
    private int minLevel = 1;
    private int maxLevel = 60;
    private int levelsPerGroup = 5;
    private int usersPerGroup = 1000;
    private String dataFile = "activeUsers.data";

    public String getDataFile() {
        return dataFile;
    }

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public int getLevelsPerGroup() {
        return levelsPerGroup;
    }

    public void setLevelsPerGroup(int levelsPerGroup) {
        this.levelsPerGroup = levelsPerGroup;
    }

    public int getUsersPerGroup() {
        return usersPerGroup;
    }

    public void setUsersPerGroup(int usersPerGroup) {
        this.usersPerGroup = usersPerGroup;
    }
    
}
