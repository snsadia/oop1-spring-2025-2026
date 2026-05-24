// Interface definition
interface IMemberOperation {
    double discountedFee();
}

// Abstract base class
abstract class Member {
    String memberID;
    double monthlyFee;

    // Default constructor
    Member() {
    }

    // Parameterized constructor
    Member(String memberID, double monthlyFee) {
        this.memberID = memberID;
        this.monthlyFee = monthlyFee;
    }

    // Abstract method to be overridden by subclasses
    abstract void showInfo();
}

// Subclass 1: PlatinumMember
class PlatinumMember extends Member implements IMemberOperation {
    int freeSessions;

    // Default constructor
    PlatinumMember() {
        super();
    }

    // Parameterized constructor
    PlatinumMember(String memberID, double monthlyFee, int freeSessions) {
        super(memberID, monthlyFee);
        this.freeSessions = freeSessions;
    }

    // Implementing the interface method
    @Override
    public double discountedFee() {
        if (this.monthlyFee > 8000) {
            return this.monthlyFee - (this.monthlyFee * 0.08); // 8% discount
        }
        return this.monthlyFee;
    }

    // Overriding the abstract method
    @Override
    void showInfo() {
        System.out.println("Platinum Member ID: " + memberID + 
                           " | Monthly Fee: " + monthlyFee + 
                           " | Free Sessions: " + freeSessions);
    }
}

// Subclass 2: StandardMember
class StandardMember extends Member implements IMemberOperation {
    boolean groupClassAccess;

    // Default constructor
    StandardMember() {
        super();
    }

    // Parameterized constructor
    StandardMember(String memberID, double monthlyFee, boolean groupClassAccess) {
        super(memberID, monthlyFee);
        this.groupClassAccess = groupClassAccess;
    }

    // Implementing the interface method
    @Override
    public double discountedFee() {
        if (this.monthlyFee > 8000) {
            return this.monthlyFee - (this.monthlyFee * 0.08); // 8% discount
        }
        return this.monthlyFee;
    }

    // Overriding the abstract method
    @Override
    void showInfo() {
        System.out.println("Standard Member ID: " + memberID + 
                           " | Monthly Fee: " + monthlyFee + 
                           " | Group Class Access: " + (groupClassAccess ? "Yes" : "No"));
    }
}

// Gym Class
class Gym {
    String name;
    Member[] mm;

    // Default constructor
    Gym() {
        mm = new Member[10]; // default capacity
    }

    // Parameterized constructor
    Gym(String name, int count) {
        this.name = name;
        this.mm = new Member[count];
    }

    // Adds a member to the first available slot in the array
    void addMember(Member m) {
        for (int i = 0; i < mm.length; i++) {
            if (mm[i] == null) {
                mm[i] = m;
                System.out.println("Successfully added member: " + m.memberID);
                return;
            }
        }
        System.out.println("Gym is at full capacity! Cannot add " + m.memberID);
    }

    // Removes a member by matching their memberID
    void removeMember(String memberID) {
        for (int i = 0; i < mm.length; i++) {
            if (mm[i] != null && mm[i].memberID.equals(memberID)) {
                System.out.println("Successfully removed member: " + mm[i].memberID);
                mm[i] = null;
                return;
            }
        }
        System.out.println("Member ID " + memberID + " not found.");
    }

    // Displays info for all non-null members
    void showMembers() {
        System.out.println("\n--- Members of " + name + " ---");
        boolean hasMembers = false;
        for (Member m : mm) {
            if (m != null) {
                m.showInfo();
                hasMembers = true;
            }
        }
        if (!hasMembers) {
            System.out.println("No members currently enrolled.");
        }
        System.out.println("---------------------------");
    }

    // Calculates and prints the total revenue applying polymorphic discounts
    void totalRevenue() {
        double total = 0;
        for (Member m : mm) {
            if (m != null) {
                // Check if the member implements the interface to safely apply the discount method
                if (m instanceof IMemberOperation) {
                    total += ((IMemberOperation) m).discountedFee();
                } else {
                    total += m.monthlyFee; // Fallback if no interface is implemented
                }
            }
        }
        System.out.println("Total Monthly Revenue for " + name + ": $" + total + "\n");
    }
}

// Driver Class (Start)
public class FitzoneGym {
     public static void main(String[] args) {
        System.out.println("--- FitZone Gym Management System ---\n");

        // 1. Create two sample objects of the Member class utilizing polymorphic behavior
        // Platinum member gets 8% discount since fee (10000) > 8000
        Member m1 = new PlatinumMember("P-1001", 10000, 5); 
        // Standard member gets no discount since fee (7000) is not > 8000
        Member m2 = new StandardMember("S-1002", 7000, true); 

        // 2. Create one sample object of the Gym class using parameterized constructor
        Gym fitZone = new Gym("FitZone Downtown", 5);

        // 3. Demonstrate the use of all relevant methods
        System.out.println("[Adding Members]");
        fitZone.addMember(m1);
        fitZone.addMember(m2);

        System.out.println("[Showing Members]");
        fitZone.showMembers();

        System.out.println("[Calculating Revenue]");
        // Total expected: 
        // m1 (10000 - 8% = 9200) + m2 (7000) = 16200
        fitZone.totalRevenue();

        System.out.println("[Removing a Member]");
        fitZone.removeMember("P-1001");

        System.out.println("[Showing Members after Removal]");
        fitZone.showMembers();

        System.out.println("[Calculating Revenue after Removal]");
        fitZone.totalRevenue();
    }
}