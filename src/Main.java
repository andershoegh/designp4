public class Main {

    public static void main(String[] args) {
        Player andreas = new Player("Andreas Sigsgaard", "Søndergade 62, 2 Sal lejl 5, 9000 Aalborg",
                "asigsg16@student.aau.dk","Hans mor", 98989898,"Angriber", "25-02-1994",
                "injured",3, 5, 10, 0, 3, 10,
                6, 0);

        Player tummas = new Player("Tummas Jóhan Sigvardsen", "Godsbanen 17, 3., 22, 9000 Aalborg",
                "tsigva16@student.aau.dk","Din mor", 50695073,"Defence", "22-10-1992",
                "injured", 4, 5, 10, 0, 3, 10,
                6, 0);

        System.out.println(andreas.getAddress());
        System.out.println(tummas.getAddress());

        Match kamp = new Match(5, 1, 2, 2, "Andreas", "AaB");

        System.out.println(kamp.getGoalsFor() + " - " + kamp.getGoalsAgainst());
    }

}
