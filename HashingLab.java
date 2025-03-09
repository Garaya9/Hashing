/** @Author: Gebrella Araya
 * @since March 9, 2025
 * Version: 1.0
 * Hashing Lab5
*/
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class HashingLab {
    // Constants for Linear-Quotient Hashing
    static final int N_LQ = 13; // Table size for Linear-Quotient Hashing
    static final int PRIME = 19; // Prime number for offset
    static int[] hashTableLQ; // Hash table for Linear-Quotient Hashing
    static final int EMPTY = -1; // Placeholder for empty slots

    // Constants for Bucket Hashing
    static final int N_BUCKET = 10; // Table size for Bucket Hashing
    static LinkedList<Integer>[] hashTableBucket; // Hash table (linked lists)

    public static void main(String[] args) {
        int[] keys = {27, 53, 13, 10, 138, 109, 49, 174, 26, 24};

        // Initialize tables
        hashTableLQ = new int[N_LQ];
        Arrays.fill(hashTableLQ, EMPTY);
        hashTableBucket = new LinkedList[N_BUCKET];
        for (int i = 0; i < N_BUCKET; i++) {
            hashTableBucket[i] = new LinkedList<>();
        }

        for (int key : keys) {
            insertLinearQuotient(key);
            insertBucket(key);
        }

        System.out.println("\n===== Linear-Quotient Hash Table (N=13) =====");
        printTableLQ();
        System.out.println("\n===== Bucket Hashing Table (N=10) =====");
        printTableBucket();

      
        Scanner scanner = new Scanner(System.in);
        String action = "";

        while (true) {
            System.out.print("\nEnter 'search' to search, 'delete' to delete, or 'exit' to quit: ");
            action = scanner.nextLine().trim().toLowerCase();

            if (action.equals("exit")) {
                System.out.println("Exiting the program...");
                break; 
            }

            if (action.equals("search")) {
                searchLoop(scanner); 
            } else if (action.equals("delete")) {
                deleteLoop(scanner); 
            } else {
                System.out.println("Invalid option. Please enter 'search', 'delete', or 'exit'.");
            }
        }

        scanner.close();
    }

    // Linear-Quotient Hashing 
    static void insertLinearQuotient(int key) {
        int ip = key % N_LQ;
        int q = key / N_LQ;
        int offset = (q % N_LQ != 0) ? q : PRIME;

        int i = ip;
        while (hashTableLQ[i] != EMPTY) {
            i = (i + offset) % N_LQ; 
        }
        hashTableLQ[i] = key;
    }

    static int searchLQ(int key) {
        int ip = key % N_LQ;
        int q = key / N_LQ;
        int offset = (q % N_LQ != 0) ? q : PRIME;

        int i = ip;
        int comparisons = 0;
        while (hashTableLQ[i] != EMPTY) {
            comparisons++;
            if (hashTableLQ[i] == key) return comparisons;
            i = (i + offset) % N_LQ;
        }
        return comparisons;
    }

    static void deleteLQ(int key) {
        int ip = key % N_LQ;
        int q = key / N_LQ;
        int offset = (q % N_LQ != 0) ? q : PRIME;

        int i = ip;
        while (hashTableLQ[i] != EMPTY) {
            if (hashTableLQ[i] == key) {
                hashTableLQ[i] = EMPTY;
                System.out.println("Deleted " + key + " from Linear-Quotient Hashing.");
                return;
            }
            i = (i + offset) % N_LQ;
        }
        System.out.println("Key " + key + " not found in Linear-Quotient Hashing.");
    }

    static void printTableLQ() {
        for (int i = 0; i < N_LQ; i++) {
            System.out.println("Index " + i + ": " + (hashTableLQ[i] == EMPTY ? "EMPTY" : hashTableLQ[i]));
        }
    }

    //  Bucket Hashing 
    static void insertBucket(int key) {
        int index = key % N_BUCKET;
        if (!hashTableBucket[index].contains(key)) {
            hashTableBucket[index].add(key);
        }
    }

    static int searchBucket(int key) {
        int index = key % N_BUCKET;
        int comparisons = 0;
        for (Integer value : hashTableBucket[index]) {
            comparisons++;
            if (value.equals(key)) {
                return comparisons;
            }
        }
        return comparisons;
    }

    static void deleteBucket(int key) {
        int index = key % N_BUCKET;
        if (hashTableBucket[index].contains(key)) {
            hashTableBucket[index].remove(Integer.valueOf(key));
            System.out.println("Deleted " + key + " from Bucket Hashing.");
        } else {
            System.out.println("Key " + key + " not found in Bucket Hashing.");
        }
    }

    static void printTableBucket() {
        for (int i = 0; i < N_BUCKET; i++) {
            System.out.println("Bucket " + i + ": " + hashTableBucket[i]);
        }
    }

    // Loop for continuously searching
    static void searchLoop(Scanner scanner) {
        while (true) {
            System.out.print("Enter a key to search (or type 'exit' to stop searching): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break; // Exit search loop if 'exit' is entered
            }

            try {
                int searchKey = Integer.parseInt(input);
                int lqComparisons = searchLQ(searchKey);
                int bucketComparisons = searchBucket(searchKey);
                System.out.println("Linear-Quotient Comparisons: " + lqComparisons);
                System.out.println("Bucket Hashing Comparisons: " + bucketComparisons);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer key.");
            }
        }
    }

    // Loop for continuously deleting
    static void deleteLoop(Scanner scanner) {
        while (true) {
            System.out.print("Enter a key to delete (or type 'exit' to stop deleting): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break; // Exit delete loop if 'exit' is entered
            }

            try {
                int deleteKey = Integer.parseInt(input);
                deleteLQ(deleteKey);
                deleteBucket(deleteKey);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer key.");
            }
        }
    }
}
