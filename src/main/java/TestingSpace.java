import org.grouptwentyone.models.HabitatTile;
import org.grouptwentyone.models.HexCoordinate;
import org.grouptwentyone.models.Player;

public class TestingSpace {
    public static void main(String[] args) {
        Player p1 = new Player("bob");
//        HabitatTile habitatTile = new HabitatTile(2);
//        p1.setSelectedTile(habitatTile);
//        p1.addNewTile(new HexCoordinate(0,0));

        p1.printPlayerBoard();
    }
}
