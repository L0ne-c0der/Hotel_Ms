import java.util.Scanner;
import java.util.ArrayList;

abstract class Guest{
    String name;
    String email;
    // String CountryCode;
    long phoneNo;
    int adults;
    int children;
    Guest(){
        name = "";
        email = "";
        phoneNo = 0;
        children =0;
        adults = 0;
    }
    Guest(String n, String em, long ph, int child, int adult){
        name = n;
        email = em;
        phoneNo = ph;
        adults = adult;
        children = child;
    }
}

class Reservation extends Guest{
    // Scanner sc = new Scanner(System.in);
    String StartDate;
    String EndDate;
    int noDays;
    int start=0;
    int start_month =0;
    int end_month = 0;
    int end=0;
    Reservation(){
        StartDate= "";
        EndDate= "";
        noDays = 0;
    }
    Reservation(String n, String em, long ph, String ad, String ch, String st, String en){
        super();
        StartDate = st;
        EndDate= en;
        noDays = CalcDate(StartDate, EndDate);
        start = 0;
        end = 0;
        start_month =0;
        end_month=0;
    }
    public int CalcDate(String StartDate, String EndDate){
        String[] startParts = StartDate.split("/");
        String[] endParts = EndDate.split("/");
        start=0;
        start_month =Integer.parseInt(startParts[2]);
        end_month = Integer.parseInt(endParts[2]);
        end=0;
        if (startParts[2]==endParts[2]) {
            start = Integer.parseInt(startParts[0]);
            end = Integer.parseInt(endParts[0]);
            if (startParts[1]!=endParts[1]) {
                end = end+31;
            }
        }
        return end-start;
    }
}

class Room extends Reservation{
    // Your code here
    // Scanner sc = new Scanner(System.in);
    // int singleRooms;
    // int deluxeRooms;
    // int luxuryRooms;
    ArrayList <ArrayList<Integer>> rooms = new ArrayList<ArrayList<Integer>>();

    String roomType;
    int start_row;
    double price;
    Room(){
        super();
        // singleRooms = 20;
        // deluxeRooms = 15;
        // luxuryRooms = 6;
        rooms.add(new ArrayList<Integer>());
        rooms.get(0).add(0,0);
        rooms.get(0).add(1,0);
        rooms.get(0).add(2,20);
        rooms.get(0).add(3,15);
        rooms.get(0).add(4,6);
        price = 0;
    }
    Room(String n, String em, long ph, String ad, String ch, String st, String en,int choice){
        super();
        int i=0;
        while (i<=noDays) {
            rooms.get(rooms.size()-1).add(0,start+i);
            rooms.get(rooms.size()-1).add(1,start_month);
            rooms.get(rooms.size()-1).add(2,20);
            rooms.get(rooms.size()-1).add(3,15);
            rooms.get(rooms.size()-1).add(4,6);
        }
        rooms.add(new ArrayList<Integer>());
        
        switch (choice) {
            case 1:
                // singleRooms--;
                roomType = "Single Room";
                price = 2500;
                break;
            
            case 2:
                // deluxeRooms--;
                roomType = "Deluxe Room ";
                price = 5250;
                break;
            case 3:
                // luxuryRooms--;
                roomType = "Luxury Room";
                price = 10000;
                break;
            default:
                System.out.println("Wrong choice, please try again");
                break;
        }
    }
    void displayRoom(){
        // System.out.println("1. Single Room ("+singleRooms+" available) ");
        // System.out.println("2. Deluxe Room ("+deluxeRooms+" available) ");
        // System.out.println("3. Luxury Room ("+luxuryRooms+" available) ");
    }
}



class Billing extends Room{
    double gst;
    double totalPrice;
    Billing(){
        gst = 0;
    }
    Billing(String n, String em, long ph, String st, String en, int choice){
        super();
        Scanner sc = new Scanner(System.in);
        gst = 0.2*price;
        totalPrice = price + gst;
        displayBill();
        int paymentChoice = sc.nextInt();
        switch (paymentChoice) {
            case 1:
                System.out.println("Enter UPI ID:");
                long uPi = sc.nextLong();
                System.out.println("A request has been sent to your UPI ID. Please proceed to your respective UPI app to continue payment");
                break;
            case 2:
                System.out.println("Enter Debit/ Credit Number:");
                long debNo = sc.nextLong();
                System.out.println("Enter the Expiry Details: (MM/YY)");
                String expString = sc.next();
                System.out.println("Enter CVV:");
                int cvv = sc.nextInt();
                break;
        }
    }
    public void displayBill(){
        System.out.println("Price:");
        System.out.println(roomType+"\t\t"+adults+"adults"+children+"children");
        System.out.println("Basic Room Price \t\tRs"+(noDays*price));
        System.out.println("Taxes, service fees, GST\t\t\t"+"+Rs"+(noDays*gst));
        System.out.println("Total Price\t\t\tRs"+totalPrice);
        System.out.println("Please choose the payment method:");
        System.out.println("1.UPI/BHIM");
        System.out.println("2. Credit/Debit/ATM Card");
        System.out.println("Press any other number to go back home (exit)");
    }
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
            System.out.println("Enter the name to book under:");
            String name = sc.next();
            System.out.println("Enter number of adults: ");
            adults = sc.nextInt();
            System.out.println("Enter number of children");
            children = sc.nextInt();
            System.out.println("Choose from different room types:");
            Room rooms = new Room();
            rooms.displayRoom();
            System.out.println("Enter your choice: ");
            int roomChoice = sc.nextInt();
            System.out.println("");
            if(roomChoice == 1){
                Billing book = new Billing();
                book.displayRoom();
            }  
        }
        else if (mainChoice==2) {
            //enter your code here

        }
    }
}


// create an arraylist that keeps entries on the dates where reservations are done
// also an array or arraylist of booking elements to dislay for reserved rooms
// once a room is cancelled, we will remove one entry from the booking elements and dates respectively

