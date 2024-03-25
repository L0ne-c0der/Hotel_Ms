import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
interface Room {
    static ArrayList<ArrayList<Integer>> rooms = initializeRooms();
    static ArrayList<ArrayList<Integer>> initializeRooms() {
        ArrayList<ArrayList<Integer>> rooms = new ArrayList<>();
        ArrayList<Integer> initialRow = new ArrayList<>();
        initialRow.add(0);
        initialRow.add(0);
        initialRow.add(0);
        initialRow.add(20);
        initialRow.add(15);
        initialRow.add(6);
        rooms.add(initialRow);
        return rooms;
    }
    static void addSlots(int dt, int mon, int year){
        ArrayList<Integer> newSlot = new ArrayList<>(); 
        newSlot.add(dt);
        newSlot.add(mon);
        newSlot.add(year);
        newSlot.add(20);
        newSlot.add(15);
        newSlot.add(6);
        rooms.add(newSlot);
    }

    ArrayList<ArrayList<Integer>> getRoomNames();
}


//Dates to be made into an abstract or superclass
abstract class Dates implements Room{
    // Dates class implementation
    int startDate;
    int endDate;
    int startMonth;
    int endMonth;
    int startYear;
    int endYear;
    static List<Integer> thirty1Days = new ArrayList<>(List.of(1, 3, 5, 7, 8, 10, 12));
    static List<Integer> thirtyDays = new ArrayList<>(List.of(4,6,9,11));
    static int leapOrnot;
    Dates(){
        startDate=0;
        startMonth=0;
        endDate=0;
        endMonth=0;
        startYear=0;
        endYear=0;
    }
    Dates(String start, String end){
        String[] startParts = start.split("/");
        String[] endParts = end.split("/");
        startDate = Integer.parseInt(startParts[0]);
        endDate = Integer.parseInt(endParts[0]);
        startMonth = Integer.parseInt(startParts[1]);
        endMonth = Integer.parseInt(endParts[1]);
        startYear = Integer.parseInt(startParts[2]);
        endYear = Integer.parseInt(endParts[2]);
    }
    int checkArray(int date, int mon, int year){
        for (int i = 1; i < rooms.size(); i++) {
            if (rooms.get(i).get(0)==date && rooms.get(i).get(1)==mon && rooms.get(i).get(2)==year) {
                return i;
            }
        }
        return 0;
    }
    int sumRooms(int row){
        int sum=0;
        for (int i = 3; i < 6; i++) {
            sum+=rooms.get(row).get(i);
        }
        return sum;
    }
    int maxRooms(){
        int temp=99,min=99, index=0;
            for (int i = 0; i < rooms.size(); i++) {
                if(rooms.get(i).get(1)>=startMonth && rooms.get(i).get(1)<=endMonth){
                    if (rooms.get(i).get(0)>=startDate && rooms.get(i).get(0)<=endDate) {
                        temp = sumRooms(i);
                    }
                }
                if(temp<min){
                    min = temp;
                    index=i;
                }
            }
        return index;
    }
    void displayRoom(){
        int row = maxRooms();
        System.out.println();
        System.out.println("\t\tRoom types:");
        System.out.println("");
        System.out.println("| S.No | Room Type       | Description                                 |");
        System.out.println("|++|");
        System.out.printf("|  1   | Single Rooms    | %d available                                |\n", rooms.get(row).get(3));
        System.out.println("|      |                 | - Solo traveler's cozy workspace            |");
        System.out.println("|      |                 | - Maximum occupancy: 1 adult                |");
        System.out.println("|------+-----------------+---------------------------------------------|");
        System.out.printf("|  2   | Double Rooms    | %d available                                |\n", rooms.get(row).get(4));
        System.out.println("|      |                 | - Spacious, Elegant, Tranquil, Luxurious    |");
        System.out.println("|      |                 | - Maximum occupancy: 2 adults + 1 child     |");
        System.out.println("|------+-----------------+---------------------------------------------|");
        System.out.printf("|  3   | Luxury Rooms    | %d available                                 |\n", rooms.get(row).get(5));
        System.out.println("|      |                 | - Luxurious, breathtaking, comfortable      |");
        System.out.println("|      |                 | - Maximum occupancy: 2 adults + 2 children  |");
        System.out.println("");

    }
    long calcDates(){
        LocalDate date2 = LocalDate.of(endYear, endMonth, endDate);
        LocalDate date1 = LocalDate.of(startYear, startMonth, startDate);
        long daysBetween = ChronoUnit.DAYS.between(date1, date2);
        return daysBetween;
    }
    static int leapChecker(int year){
        if ((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)) {
            return 29;
        }
        else{
            return 28;
        }
    }

}


class Guest {
    // Guest class implementation
    int adults;
    int children;
    int points;
    Guest(){
        adults = 0;
        children = 0;
        points = 0;
    }
    Guest(int ad, int ch){
        adults = ad;
        children = ch;
        points = returnPoints();
    }
    int returnPoints(){
        return adults*10+children*4;
    }
}

class Reservation extends Dates{
    //to store all reservations made
    int numRooms;
    int choice;
    static ArrayList<ArrayList<Integer>> reservations = new ArrayList<ArrayList<Integer>>();
    //int sd, int sm, int sy, int ed, int em, int ey, int ch, int num
    
    Reservation(){
        super();
    }
    Reservation(String start, String end){
        super(start,end);
        numRooms=0;
        choice = 0;
    }
    Reservation(String start, String end, Guest guests, int choice, int num){
        super(start,end);
        this.numRooms = num;
        this.choice = choice;
        switch (choice) {
            case 1:
                if (guests.points<=10*numRooms) {
                    addDates(3,numRooms);
                }
                else{
                    System.out.println("Single rooms can accomodate only one guest. Please try again");
                }
                break;
            case 2:
                if (guests.points<=24*numRooms) {
                    addDates(4, numRooms);
                }
                else{
                    System.out.println("Double rooms can accomodate only upto 2 adults and 1 children\n Please try again");
                }
                break;
            case 3:
                if (guests.points<=28*numRooms) {
                    addDates(5, numRooms);
                }
                break;
            default:
                System.out.println("Wrong choice. Please try again");
                break;
        }

    }
    public ArrayList<ArrayList<Integer>> getRoomNames() {
        return rooms;
    }
    // static void makeRes(int sd, int sm, int sy, int ed, int em, int ey, int ch, int num){
    //     ArrayList<Integer> initialRow = new ArrayList<>();
    //     initialRow.add(sd);
    //     initialRow.add(sm);
    //     initialRow.add(sy);
    //     initialRow.add(ed);
    //     initialRow.add(em);
    //     initialRow.add(ey);
    //     initialRow.add(ch);
    //     initialRow.add(num);
    //     reservations.add(initialRow);
    // }
    public void addDates(int index, int numRooms){
        int date = startDate;
        int mon = startMonth;
        int year = startYear;
        for (int i = 0; i < (calcDates()); i++) {
            if ((date==30 && thirtyDays.contains(mon)) || (date==31 && thirty1Days.contains(mon)) || (mon==2 && leapChecker(year)==date) ) {
                date = 1;
                mon++;
            }
            if(mon>12){
                mon = 1;
                year++;
            }
            decRooms(index, numRooms, date, mon, year);
            date++;
        }
    }
    public void decRooms(int roomIndex, int numDec, int dt, int mon, int yr){
        if(checkArray(dt, mon, yr)==0){
            Room.addSlots(dt, mon, yr);
            int numRooms = Room.rooms.get(rooms.size()-1).get(roomIndex)-numDec;
            Room.rooms.get(rooms.size()-1).set(roomIndex, numRooms);
        }
        else{
            int dateIndex = checkArray(dt, mon, yr);
            int numRooms = Room.rooms.get(dateIndex).get(roomIndex)-numDec;
            Room.rooms.get(dateIndex).set(roomIndex, numRooms);
        }
    }
}

class Billings{
    //to make a booking
    String roomType;
    int roomIndex;
    double basePrice;
    double gst;
    double beforeGst;
    double totalPrice; 
    int noDays;
    String stayDate;
    String leaveDate;
    Reservation r1;
    Guest g1;
    public Billings(Reservation r, Guest g){
        Scanner sc = new Scanner(System.in);
        r1 = r;
        g1 = g;
        roomIndex = r1.choice+2;
        stayDate = r1.startDate+"/"+r1.startMonth+"/"+r1.startYear;
        leaveDate = r1.endDate+"/"+r1.endMonth+"/"+r1.endYear;
        switch (roomIndex) {
            case 3:
                roomType = "Single Room";
                basePrice = r1.numRooms*2000;
                break;
            case 4:
                roomType = "Double Room";
                basePrice = r1.numRooms * 3500;
                break;
            case 5:
                roomType = "Luxury Room";
                basePrice = r1.numRooms * 6000;
                break;
        }
        noDays = (int) r1.calcDates();
        beforeGst = (basePrice*noDays);
        gst = 0.2*beforeGst;
        totalPrice = beforeGst + gst;
        displayBill(g1.adults, g1.children);
        System.out.println();
        System.out.println("Please choose the payment method:");
        System.out.println("1.UPI/BHIM");
        System.out.println("2. Credit/Debit/ATM Card");
        int payChoice = sc.nextInt();
        switch (payChoice) {
            case 1:
                while (true) {   
                    System.out.println("Enter UPI ID:");
                    String uPi = sc.next();
                    if (uPi.contains("@")){
                        System.out.println("A request has been sent to your UPI ID. Please proceed to your respective UPI app to continue payment");
                        break;
                    }
                    else{
                        System.out.println("Invalid upi Id ! Please enter an upi Id containing '@' and a bank name. ");
                    }
                }
                break;
            case 2:
                    LocalDate currentDate = LocalDate.now();

                    
                    int currentMonth = currentDate.getMonthValue();
                    int currentYear = currentDate.getYear() % 100;
                        while (true) {
                            try{
            
                                System.out.println("Enter Debit/ Credit Number:");
                                String debNo = sc.nextLine().trim();
                                if (debNo.length() != 16) {
                                    System.out.println("card number invalid! please enter a 16 digit number:  ");
                                    continue;
                                }
            
                                sc.nextLine();
            
                                
                                System.out.println("Enter the Expiry Details (MM/YY):");
                                String expString = sc.nextLine();
                                
                                if (!expString.matches("\\d{2}/\\d{2}")) {
                                    System.out.println("Invalid expiry date format! Please enter in MM/YY format.");
                                    continue;
                                }
            
                                int expMonth = Integer.parseInt(expString.substring(0, 2));
                                int expYear = Integer.parseInt(expString.substring(3));
                
                                if (expYear < currentYear || (expYear == currentYear && expMonth <= currentMonth)) {
                                    System.out.println("Error: Expiry date must be after the current date.");
                                    continue;
                                }
                                System.out.println("Enter CVV (4 digits):");
                                int cvv = sc.nextInt();
                
                                
                                if (String.valueOf(cvv).length() != 4) {
                                    System.out.println("Error: CVV must be 4 digits.");
                                    continue;
                                }
                
                                
                                break;

                            } 
                            catch (InputMismatchException e) 
                            {
                                System.out.println("Invalid input! Please enter a valid value.");
                                sc.nextLine(); // Consume the invalid input
                            } 
                            catch (NumberFormatException e) 
                            {
                                System.out.println("Error: Invalid input format.");
                            }
                        }
                    break;
        }
        System.out.println("Enter payment ID:");
        String pymentID = sc.next();
        System.out.println("Thank you. Your booking has been confirmed");
    }
    public void displayBill(int adults, int children){
        System.out.println("Price:");
        System.out.println("("+noDays+") x"+roomType+"\t\t"+adults+"adults"+children+"children");
        System.out.println("Basic Room Price \t\tRs"+(beforeGst));
        System.out.println("Taxes, service fees, GST\t"+"+Rs"+(gst));
        System.out.println("Total Price\t\t\tRs"+totalPrice);
    }
}
class Bookings{
    static ArrayList<Billings> bookedRooms = new ArrayList<Billings>();
    static void AddBooking(Billings b1){
        bookedRooms.add(b1);
    }
    void DisplayRooms(){
        for (int i = 0; i < bookedRooms.size(); i++) {
            System.out.println("\n___________\n");
            System.out.println("Booking number:"+(i+1));
            Billings b1 = bookedRooms.get(i);
            System.out.println("Check-in: "+b1.stayDate+"\t\t Check-out: "+b1.leaveDate);
            System.out.println(b1.r1.numRooms+" x "+b1.roomType+"(s)\t\t Total price: "+b1.totalPrice);
        }
    }
    void CancelBooking(int billIndex){
        Billings bill1 = bookedRooms.get(billIndex);
        int roomIndex = bill1.roomIndex;
        int year = bill1.r1.startYear;
        int mon = bill1.r1.startMonth;
        int date = bill1.r1.startDate;
        int numRooms = bill1.r1.numRooms;
        for (int i = 0; i < bill1.r1.calcDates(); i++) {
            if ((date==30 && Dates.thirtyDays.contains(mon)) || (date==31 && Dates.thirty1Days.contains(mon)) || (mon==2 && Dates.leapChecker(year)==date) ) {
                date = 1;
                mon++;
            }
            if(mon>12){
                mon = 1;
                year++;
            }
            emptyRoom(bill1.r1,roomIndex, numRooms, date, mon, year);
            date++;
        }
        removeBooking(billIndex);
    }
    void emptyRoom(Reservation r1, int roomIndex, int numRooms, int date, int mon, int yr){
        int dateIndex = r1.checkArray(date,mon,yr);
        int newRooms = Room.rooms.get(dateIndex).get(roomIndex)+numRooms;
        Room.rooms.get(dateIndex).set(roomIndex,newRooms);
    }
    void removeRedundant(){
        ArrayList<Integer> refRow = Room.rooms.get(0);
        for (int i = Room.rooms.size()-1; i > 0; i--) {
            if (Room.rooms.get(i).get(3)==refRow.get(3) && Room.rooms.get(i).get(4)==refRow.get(4) && Room.rooms.get(i).get(5)==refRow.get(5)) {
                Room.rooms.remove(i);
            }
        }
    }
    void removeBooking(int billIndex){
        bookedRooms.remove(billIndex);
        removeRedundant();
    }
}

public class Hotel {
    // Placeholder code for Room, Guest, and Reservation classes
    
    public static void main(String[] args) {
        // Your code here
        Scanner sc = new Scanner(System.in);
        String email;
        System.out.println("--------------------- Welcome to Dream Hotel ------------------------");
        while (true) {
            System.out.println("Please enter your name:");
            String name = sc.nextLine();
            
            if (name.matches("[a-zA-Z\\s]+")) {
                break;
            }
            else{
                System.out.println("Name invalid! Please enter only alphabets.");
            }
        }
        while (true) {
            System.out.println("Please enter your email:");
            email = sc.nextLine();
            
            if (email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                // Check if "@" comes before "."
                int atIndex = email.indexOf('@');
                int dotIndex = email.lastIndexOf('.');

                if (atIndex > dotIndex) {
                    System.out.println("Invalid email address! Please ensure '@' comes before '.'.");
                    continue;
                }

                // Check if there is only one "@" symbol
                if (email.indexOf('@', atIndex + 1) != -1) {
                    System.out.println("Invalid email address! Please enter only one '@' symbol.");
                    continue;
                }

                // Check if there is only one "." symbol
                if (email.indexOf('.', dotIndex + 1) != -1) {
                    System.out.println("Invalid email address! Please enter only one '.' symbol.");
                    continue;
                }

                break; // Exit the loop if the email is valid
            } else {
                System.out.println("Invalid email address format! Please enter a valid email address.");
            }
        }

        while (true) {
            System.out.print("Enter your mobile number: +91 ");
            String mno = sc.nextLine();

            if (mno.length() == 10 && mno.matches("\\d+")) {
                break;
            } else {
                System.out.println("Invalid number! Please enter a 10-digit number.");
            }
            
        }
        
        while (true){
            System.out.println("\t\t\t\t HOME \t\t\t\t");
            System.out.println("Choose one of the given options:");
            System.out.println("(1) Check for rooms                   (2) Manage reservation(s)");
            System.out.println("To contact us:\nPhone number: +91 8555095546\nE-mail:nkolanu@gitam.in");
            System.out.println("Choose your option: ");
            int mainChoice = sc.nextInt();
            String checkin;
            String checkout ;
            if(mainChoice==1){
                while (true) {
                    try{
                        System.out.println(" Enter check-in date from 12:00 PM (DD/MM/YYYY):  ");
                        checkin = sc.next();
                        
                        if (!checkin.matches("\\d{2}/\\d{2}/\\d{4}")) {
                            System.out.println("Error: Invalid check-in date format. Please enter in DD/MM/YYYY format.");
                            continue; // Continue to the next iteration of the loop
                        }
                        System.out.println("Enter check-out date before 12:00 PM (DD/MM/YYYY):  ");
                        checkout = sc.next();
    
                        if (!checkout.matches("\\d{2}/\\d{2}/\\d{4}")) {
                            System.out.println("Error: Invalid check-out date format. Please enter in DD/MM/YYYY format.");
                            continue; // Continue to the next iteration of the loop
                        }
                        LocalDate checkinDate = LocalDate.parse(checkin, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));LocalDate checkoutDate = LocalDate.parse(checkout, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    
                        if (checkinDate.isAfter(checkoutDate)) {
                            System.out.println("Check-in date cannot be after the checkout date. Please enter again.");
                            continue; // Continue to the next iteration of the loop
                        }

                        if (checkinDate.equals(checkoutDate)) {
                            System.out.println("Error: Check-in and check-out dates cannot be the same. Please enter again.");
                            continue; // Continue to the next iteration of the loop
                        }
    
                        break;
                    }

                    catch(DateTimeParseException e){
                        System.out.println("Invalid date format. Please enter the date in DD/MM/YYYY format.");
                        sc.nextLine(); 
                    }
            
                }
                
                Reservation checkdates = new Reservation(checkin,checkout);
                checkdates.displayRoom();
                System.out.println("Do you wish to make a reservation(Type 1 for yes):");
                int resChoice = sc.nextInt();
                int adults = 0 ;
                int children= 0;
                int roomType = 0;
                int num = 0;
                if(resChoice==1){
                    while (true) {
                        try {
                            
                            System.out.println("Enter number of adults: ");
                            adults = sc.nextInt();
            
                          
                            if (adults <= 0) {
                                System.out.println("Number of adults must be greater than 0. Please enter again.");
                                continue; 
                            }
            
                            
                            System.out.println("Enter number of children (0 to 12 years old): ");
                            children = sc.nextInt();
            
                            
                            if (children < 0) {
                                System.out.println("Number of children must be greater than or equal to 0. Please enter again.");
                                continue;
                            }
            
                            System.out.println("Select the room type you wish to choose:");
                            System.out.println("\n1)Single Rooms\n2)Double Rooms\n3Luxury Rooms");
                            System.out.println("\nEnter your choice:");
                            roomType = sc.nextInt();

                            if (roomType < 1 || roomType > 3) {
                                System.out.println("Invalid room type. Please enter a valid option.");
                                continue;
                            }
            
                            System.out.println("Enter the number of rooms you want to book:");
                            num = sc.nextInt();
                            

                            if ((roomType == 1 && adults > 1) ||
                            (roomType == 2 && adults + children > 3) ||
                            (roomType == 3 && adults + children > 4)) {
                            System.out.println("Error: Maximum occupancy exceeded for the selected room type.");
                            continue;
                        }

                            break;
            
                        } catch (InputMismatchException e) {
                            System.out.println("Error: Invalid input. Please enter a valid integer.");
                            sc.next(); // Consume the invalid input
                        }
                  }
                    
                    Guest guests = new Guest(adults,children);
                    Reservation res1 = new Reservation(checkin,checkout,guests,roomType,num);
                    Billings b1 = new Billings(res1, guests);
                    Bookings.AddBooking(b1);
                    System.out.println();
                }
                else{
                    break;
                }
            }
            else if(mainChoice==2){
                System.out.println("Your Reservations:");
                Bookings b1 = new Bookings();
                if (b1.bookedRooms.size()!=0) {
                    b1.DisplayRooms();
                    System.out.println("Do you wish to cancel any booking(Type yes to proceed):");
                    String cancelStatus = sc.next();
                    if (cancelStatus.equalsIgnoreCase("yes")) {
                    //cancelling
                        System.out.println("Enter the booking number to cancel: ");
                        int cancelNo = sc.nextInt();
                        b1.CancelBooking(cancelNo-1);
                        System.out.println("The booking has been cancelled successfully.");
                    }
                }
                
            }
            else{
                System.out.println("Thank you for choosing Dream Hotel. We look forward to hosting you again soon!");
                break;
            }

        }
        
    }
}

