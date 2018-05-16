package Training;

public class Exercise {
    //Fields for the exercise-class
    private String name;
    private String duration;
    private int id;

    //Constructor1
    public Exercise(String name, String duration) {
        this.name = name;
        this.duration = duration;
    }
    //Constructor2
    public Exercise(String name, String duration, int id) {
        this.name = name;
        this.duration = duration;
        this.id = id;
    }

    //Getters
    public int getId() { return id;}
    public String getDuration() {return duration;}
    public String getName() {return name;}
}
