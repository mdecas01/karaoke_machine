import com.rodrigocastro.model.Song;
import com.rodrigocastro.model.SongBook;
import com.rodrigocastro.KaraokeMachine;

public class Karaoke {

  public static void main(String[]args) {
	SongBook songbook = new SongBook();
	songbook.importFrom("songs.txt");
    KaraokeMachine machine = new KaraokeMachine(songbook);
	machine.run();
	System.out.println("Saving songs...");
	songbook.exportTo("songs.txt");
  }
}