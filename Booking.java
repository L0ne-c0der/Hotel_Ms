import java.util.Scanner;
import java.util.ArrayList;
class Guest{
    int children, adults;
    Guest(){
        adults = 0;
        children = 0;
    }
    Guest(int ad, int ch){
        adults = ad;
        children = ch;
    }

}
class Room extends Guest{
    // Your code here
    Scanner sc = new Scanner(System.in);
    int singleRooms = 20;
    int deluxeRooms = 15;
    // int[] luxuryRooms = new int[1*5];
    // Room(int choice, )
    void display(){
        System.out.println("1. Single Room ("+singleRooms+" available) ");
        System.out.println("2. Deluxe Room ("+deluxeRooms+" available) ");
    }
}

class Reservation extends Room{
    Scanner sc = new Scanner(System.in);

}

public class Booking {
    public static void main(String[] args) {
        System.out.println("--------------------- Welcome to Dream Hotel ------------------------");
        System.out.println("Choose one of the given options:");
        System.out.println("(1) Make a reservation                   (2) Manage reservation(s)");
        System.out.println("Choose your option: ");
        Scanner sc = new Scanner(System.in);
        int mainChoice = sc.nextInt();
        String checkin;
        String checkout;
        int adults, children;
        if (mainChoice==1) {
            System.out.println(" Enter check-in date from 12:00 PM (DD/MM/YYYY):  ");
            checkin = sc.next();
            System.out.println("Enter check-out date from 12:00 PM (DD/MM/YYYY):  ");
            checkout = sc.next();
            System.out.println("Enter the guest details:");
            System.out.println("Enter number of adults: ");
            adults = sc.nextInt();
            System.out.println("Enter number of children");
            children = sc.nextInt();
            System.out.println("Choose from different room types:");
            Room rooms = new Room();
            rooms.display();
            System.out.println("Enter your choice: ");
            int roomChoice = sc.nextInt();
            if(roomChoice == 1){
            }  
        }
        else if (mainChoice==2) {
            //enter your code here
        }
    }
}
