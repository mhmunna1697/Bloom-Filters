import java.io.*;
import java.util.*;
import java.util.Random;

public class CountingBloomFilter {
    public static final int n = 1000;   // number of elements to be encoded initially
    public static final int n_to_remove = 500;  // number of elements to be removed
    public static final int n_to_add = 500; // number of elements to be added
    public static final int s = 10000;   // number of counters in the filter
    public static final int h = 7;    // number of hashes 
    public int[] counters = new int[s];
    public int[] hashSeeds = new int[h];
    public static Random rand = new Random();

    public CountingBloomFilter() {
        for (int i = 0; i < h; i++) {
            hashSeeds[i] = rand.nextInt(Integer.MAX_VALUE);
        }
    }

    public int hash(int element, int seed) {
        int hash = element ^ seed;
        hash ^= (hash >>> 16);
        return Math.abs(hash % s);
    }

    public void add(int element) {
        for (int seed : hashSeeds) {
            counters[hash(element, seed)]++;
        }
    }

    public void remove(int element) {
        for (int seed : hashSeeds) {
            int index = hash(element, seed);
            if (counters[index] > 0) {
                counters[index]--;
            }
        }
    }

    public boolean contains(int element) {
        for (int seed : hashSeeds) {
            if (counters[hash(element, seed)] == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        CountingBloomFilter cbf = new CountingBloomFilter();
        List<Integer> setA = new ArrayList<>();

        // Generate and add initial elements (Set A)
        while (setA.size() < n) {
            int element = rand.nextInt(Integer.MAX_VALUE);
            setA.add(element);
            cbf.add(element);
        }

        // Remove 500 elements from Set A
        for (int i = 0; i < n_to_remove; i++) {
            cbf.remove(setA.get(i));
        }

        // Add 500 new elements
        for (int i = 0; i < n_to_add; i++) {
            int element = rand.nextInt(Integer.MAX_VALUE);
            cbf.add(element);
        }

        // Check how many original elements from A are still in the filter
        int foundInFilter = 0;
        for (int element : setA) {
            if (cbf.contains(element)) {
                foundInFilter++;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("counting_bloomFilter_output.txt"))) {
            writer.write(foundInFilter + "\n");
            System.out.println("Output written to counting_bloomFilter_output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
