import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {


    static final String[] option = {"add", "remove", "list", "exit"};
   public static  String task = "tasks.csv";
    static String[][] tasks;

    public static void main(String[] str) {
        tasks = loadData(task);
        optionsWrite(option);

        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            switch (input) {
                case "add":
                    addTask();
                case "remove":
                    remove(tasks,getNumber());
                    System.out.println("Deleted");
                    break;
                case "list":
                    printTab(tasks);
                    break;
                case "exit":
                    save(task,tasks);
                    System.out.println(ConsoleColors.RED + "Bye, bye");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select a correct option");
            }
            optionsWrite(option);
        }
    }

    public static void optionsWrite(String[] ars) {
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Please select an option:");
        System.out.print(ConsoleColors.RESET);
        for (String option : ars) {
            System.out.println(option);
        }
    }

    public static String[][] loadData(String args) {
        Path plik = Paths.get(args);
        if (!Files.exists(plik)) {
            System.out.println("File not exist");
            System.exit(0);

        }
        String[][] ars = null;
        try {
            List<String> stringList = Files.readAllLines(plik);
            ars = new String[stringList.size()][stringList.get(0).split(",").length];
            for (int i = 0; i < stringList.size(); i++) {
                String[] split = stringList.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    ars[i][j]=split[j];

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ars;
    }
    public static void remove(String[][] ars, int indeks){
        try {
            if (indeks< ars.length){
                tasks= ArrayUtils.remove(ars, indeks);
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Not exist");
        }
    }

    private static void addTask() {

    Scanner scan = new Scanner(System.in);
    System.out.println("Please add  description");
    String description = scan.nextLine();
    System.out.println("Please add duedate");
    String duedate = scan.nextLine();
    System.out.println("Is your task is important: true/false");
    String isImportant = scan.nextLine();
    String input = scan.nextLine();
    tasks= Arrays.copyOf(tasks,tasks.length+1);
    tasks[tasks.length-1]= new String[3];
    tasks[tasks.length-1][0]=description;
    tasks[tasks.length-1][1]=duedate;
    tasks[tasks.length-1][2]=isImportant;
}

public static void printTab(String[][] ars) {
    for (int i = 0; i < ars.length; i++) {
        System.out.print(i + " : ");
        for (int j = 0; j < ars[i].length; j++) {
            System.out.print(ars[i][j] + "");
        }
        System.out.println();
    }
}
        public static void save(String a, String[][] ars){
            Path file= Paths.get(a);

            String[] b = new String[tasks.length];
            for (int i = 0; i < ars.length; i++) {
                b[i] = String.join(",",ars[i]);
            }
            try {
                Files.write(file,Arrays.asList(b));
    } catch (IOException e) {
                e.printStackTrace();
            }

        }
        public static boolean number(String number){
        if (NumberUtils.isParsable(number)){
            return Integer.parseInt(number)>=0;
        }
        return false;
        }
        public static int getNumber(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Number to remove");


        String n = scan.nextLine();
        while(!number(n)){
            System.out.println("Bad argument");
            scan.nextLine();
        }
            return Integer.parseInt(n);
        }



    }



    
