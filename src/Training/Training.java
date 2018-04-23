package Training;

public class Training {

    private int id;
    private String weekday;
    private String date;
    private String startTime;
    private String endTime;
    private int programID;

    public Training(int id, String weekday, String date, String startTime, String endTime, int programID) {
        this.id = id;
        this.weekday = weekday;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.programID = programID;
    }


    public int getId() {
        return id;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getProgramID() {
        return programID;
    }
}
