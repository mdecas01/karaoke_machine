package com.rodrigocastro;
import com.rodrigocastro.model.SongBook;
import com.rodrigocastro.model.Song;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;



public class KaraokeMachine {
  private SongBook mSongBook;
  private BufferedReader mReader;
  private Queue<Song> mSongQueue;
  private Map<String, String> mMenu;
  
  public KaraokeMachine(SongBook songbook) {
    mSongBook = songbook;
	mReader = new BufferedReader(new InputStreamReader(System.in));
	mSongQueue = new ArrayDeque<Song>();
	mMenu = new HashMap<String, String>();
	mMenu.put("add", "Add a new song to the list of songs");
    mMenu.put("play", "Play next song in the queue.");
	mMenu.put("choose", "Choose a song to sing.");
	mMenu.put("quit", "Exit the program");
  }
  
  private String promptAction() throws IOException{
    System.out.printf("There are %d songs available and %d in the queue. Your options are: \n", 
	                  mSongBook.getSongCount(),
					  mSongQueue.size());
	for (Map.Entry<String,String> option: mMenu.entrySet()) {
	  System.out.printf("%s - %s \n", option.getKey(), option.getValue());
	}
	System.out.print("What do you want to do: ");
	String choice = mReader.readLine();
	return choice.trim().toLowerCase();
  }
  
  public void run() {
    String choice = " ";
	do {
	  try {
	    choice = promptAction();
		switch(choice) {
		  case "add":
		    Song song = promptNewSong();
			mSongBook.addSong(song);
			System.out.printf("%s was successfully added.\n", song);
		    break;
		  case "choose":
		    String artist = promptArtist();
			Song artistSong = promptSongForArtist(artist);
			mSongQueue.add(artistSong);
			System.out.printf("You chose %s \n", artistSong);
		    break;
		  case "play":
		    playNext();
			break;
		  case "quit":
		    System.out.println("Thanks for playing");
			break;
	      default:
            System.out.printf("Unknown command: %s. Try again...", choice);		  
		}
	  }catch(IOException ioe) {
	    System.out.println("Problem with input");
		ioe.printStackTrace();
	  }
	}while(!choice.equals("quit"));
 }
 
 private Song promptSongForArtist(String artist) throws IOException {
   List<Song> songs = mSongBook.getSongsForArtist(artist);
   List<String> songTitles = new ArrayList<>();
   for(Song song: songs) {
     songTitles.add(song.getTitle());
   }
   System.out.printf("Available songs for %s: \n", artist);
   int index = promptForIndex(songTitles);
   return songs.get(index);
 }
 
 private Song promptNewSong() throws IOException {
   System.out.print("Enter artist name: ");
   String artist = mReader.readLine();
   System.out.print("Enter song tile:");
   String title = mReader.readLine();
   System.out.print("Enter video URL:");
   String videoURL = mReader.readLine();
   return new Song(artist, title, videoURL);
 }
 
 private String promptArtist() throws IOException {
   System.out.println("Available artists:");
   List<String> artists = new ArrayList<>(mSongBook.getArtists());
   int index = promptForIndex(artists);
   return artists.get(index);
 }
 
 private int promptForIndex(List<String> options) throws IOException{
   int counter = 1;
   for(String option: options) {
     System.out.printf("%d.) %s \n", counter, option);
	 counter++;
   }
   System.out.print("Your choice: ");
   String optionAsString = mReader.readLine();
   int choice = Integer.parseInt(optionAsString.trim());
   return choice - 1;
 }
 
 public void playNext() {
   Song song = mSongQueue.poll();
   if(song == null) {
     System.out.println("Sorry, there are no songs in the queue" + " Use choose from menu to add a song");
   } else {
     System.out.printf("Open %s to hear %s by %s", song.getVideoUrl(), song.getTitle(), song.getArtist());
   }
 }
 
 }