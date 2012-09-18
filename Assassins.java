import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class Assassin {

  String name;
  String victim;
  static LinkedList<Assassin> aliveList = new LinkedList<Assassin>();
  static HashMap<Assassin, LinkedList<Assassin>> deadMap = new HashMap<Assassin, LinkedList<Assassin>>(); 
  static Boolean gameOver = false;

  public Assassin (String name, String victim) {
    this.name = name;
    this.victim = victim;
    aliveList.add(this);
  }

  public void killed (Assassin person) {
    // remove name from linkedlist
    // add killed dude to list of people this person killed
    // check if in alive list
    if (!aliveList.contains(this)) {
      System.out.println("Stop cheating, you're already dead!");
    } else {
      if (aliveList.get(aliveList.indexOf(this)).victim != person.name) { 
        System.out.println("Error! You have entered the incorrect name");
      } else {
        if (this.name == person.victim) { //if only 2 people left
          this.finish();
        } else {
          System.out.println("Congratulations! " + this.name + " You have killed " + person.name);
          aliveList.remove(person);
          this.victim = person.victim;
          if (!deadMap.containsKey(this)) {
            deadMap.put(this, new LinkedList<Assassin>());
          }
          deadMap.get(this).add(person);
          System.out.println("Your next victim is " + this.victim);
        }
      }
    }
  }

  public void finish() {
    System.out.println("Congratulations " + this.name + " you have won!");
    printSummary();
    gameOver = true;
  }

  public void printSummary() {
    System.out.println("Hashtable has " + deadMap.size());
    for (Assassin killer: deadMap.keySet()) {
      for (Assassin killed: deadMap.get(killer)) {
        System.out.println(killer.name + " has killed " + killed.name);
      }
    }
  }

  public static void setUp(ArrayList<String> input) {
    //generate random numbers to set up game
    Random generator = new Random();
    String firstPerson = input.get(generator.nextInt(input.size()));
    while (!input.isEmpty()) {
      String nextPerson = input.get(generator.nextInt(input.size()));
      if (input.size() == 1) {
        new Assassin(nextPerson, aliveList.get(0).name);
        input.remove(nextPerson);
      }
      if (!firstPerson.equals(nextPerson)) { //if does not choose self
        new Assassin (firstPerson, nextPerson); //create new assassin (added to aliveList)
        input.remove(firstPerson); //remove from input
        firstPerson = nextPerson; //look at next person
        //System.out.println("inside inner loop");
      }
      //System.out.println("current input size is " + input.size());
    }
    // System.out.println(input.size());
    // System.out.println(input);
    /*
       int[] randomList = new int[input.size()];
       Random generator = new Random();
       for (int x=0; x < input.size(); x++) {
       randomList[x] = generator.nextInt(input.size()*input.size());
       }
     */
    /* ANOTHER BAD IDEA
       Hashtable<String, Integer> assignment = new Hashtable<String, Integer>();
       Random generator = new Random();
       for (int x = 0; x < input.size(); x++) {
       assignment.put(input.get(x), generator.nextInt(input.size()*input.size()));
       }
       for (int x = 0;  x < input.size(); x++) {
       }
     */
  }

  public String findVictim () {
    return this.victim;
    //OR do i want to do a System.out.println??
  }

  public static void printMasterList() {
    for (int i=0; i<aliveList.size(); i++) {
      System.out.println(aliveList.get(i).name + " kills " + aliveList.get(i).victim);
    }
  }

  public static Assassin getAssassin (String name) {
    for (int i = 0; i < aliveList.size(); i++) {
      if (aliveList.get(i).name.equals(name)) {
        return aliveList.get(i);
      }
    }
    return null;
  }

  public static int printMenu() {
    System.out.println("");
    System.out.println("Enter 1 if you would like to see the master list");
    System.out.println("Enter 2 if you would like to see who your victim is");
    System.out.println("Enter 3 if you have killed someone");
    InputStreamReader in = new InputStreamReader (System.in);
    BufferedReader reader = new BufferedReader (in);
    String selection;
    try {
      selection = reader.readLine();
      if (selection.equals("1")) {
        printMasterList();
      }
      if (selection.equals("2")) {
        System.out.println("Please enter your name");
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
        String name = reader2.readLine();
        if (aliveList.contains(getAssassin(name))) {
          for (int i = 0; i < aliveList.size(); i++) {
            if (aliveList.get(i).name.equals(name)) {
              System.out.println("Your victim is " + aliveList.get(i).findVictim());
              break;
            }
          }
        } else System.out.println("Sorry, that name is not in our system.");
      }
      if (selection.equals("3")) {
        System.out.println("Please enter your name");
        BufferedReader reader3 = new BufferedReader(new InputStreamReader(System.in));
        String killer = reader3.readLine();
        System.out.println("Please enter the name of the person you killed");
        BufferedReader reader4 = new BufferedReader(new InputStreamReader(System.in));
        String killed = reader4.readLine();
        Assassin aKiller = getAssassin(killer);
        Assassin aKilled = getAssassin(killed);
        System.out.println(aKiller.name);
        System.out.println(aKilled.name);
        aKiller.killed(aKilled);
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return 1;
  }

  public static void main(String[] args) {
    ArrayList<String> inputList = new ArrayList<String>();
    BufferedReader reader;
    System.out.println("Please enter your name, and type 'done' when you have finished");
    try {
      InputStreamReader in = new InputStreamReader (System.in);
      reader = new BufferedReader(in);
      String name = reader.readLine();
      while (!name.equals("done")) {
        if (name.equals("")) { //if enter blank
          System.out.println("Please enter a valid name");
        } else {
          inputList.add(name);
        }
        System.out.println("Please enter your name, and type 'done' when you have finished");
        reader = new BufferedReader(in);
        name = reader.readLine();
      }
    } catch (IOException e) {
      System.out.println("IOException " + e);
      //e.printStackTrace(); not sure what this does??
    }
    if (inputList.size() <= 4) {
      System.out.println("It's more fun with more people! Please have more than 4 players to play!");
      System.exit(1);
    }
    System.out.println("Our assassins are: " + inputList);
    System.out.println("Thank you. We will now begin the game.");
    System.out.println("");
    System.out.println("Now setting up game...");
    setUp(inputList);
    /* PREVIOUSLY
       System.out.println("The game has been setup!");
       System.out.println("Would you like to see the master list?");
       try {
       InputStreamReader in = new InputStreamReader (System.in);
       reader = new BufferedReader(in);
       String yesOrNo = reader.readLine();
       if (yesOrNo.equals("yes")) {
       printMasterList();
       }
       } catch (IOException e) {
       System.out.println("IOException " + e);
    //e.printStackTrace(); not sure what this does??
    }
     */
    System.out.println("");
    while (gameOver == false) {
      printMenu();
    }
    /* TEST CODE
       Assassin a1 = new Assassin ("a1", "a2");
       Assassin a2 = new Assassin ("a2", "a3");
       Assassin a3 = new Assassin ("a3", "a4");
       Assassin a4 = new Assassin ("a4", "a1");
       a1.killed(a3); //should return error
       a1.killed(a2);
       a2.killed(a3); // should return error
       a3.killed(a4);
       a1.killed(a3); //should output a1 as winner!
     */ 
  }
}

