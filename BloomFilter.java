import java.io.*;
import java.util.*;
import java.util.Random;

public class BloomFilter {
    public static final int n = 1000;    // number of elements to be encoded
    public static final int s = 10000;    // number of bits in the filter
    public static final int h = 7; // number of hashes
    public BitSet bitset = new BitSet(s);
    public int[] hashSeeds = new int[h];
    public static Random rand = new Random();

    public BloomFilter() {
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
            bitset.set(hash(element, seed));
        }
    }

    public boolean contains(int element) {
        for (int seed : hashSeeds) {
            if (!bitset.get(hash(element, seed))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        BloomFilter bloomFilter = new BloomFilter();
        Set<Integer> setA = new HashSet<>();
        Set<Integer> setB = new HashSet<>();
        
        // Generate and add elements to Bloom Filter (Set A)
        while (setA.size() < n) {
            int element = rand.nextInt(Integer.MAX_VALUE);
            setA.add(element);
            bloomFilter.add(element);
        }
        
        // Check elements from Set A
        int foundInA = 0;
        for (int element : setA) {
            if (bloomFilter.contains(element)) {
                foundInA++;
            }
        }
        
        // Generate a different set of elements (Set B)
        while (setB.size() < n) {
            int element = rand.nextInt(Integer.MAX_VALUE);
            if (!setA.contains(element)) {  // Ensure it's different from Set A
                setB.add(element);
            }
        }
        
        // Check elements from Set B
        int foundInB = 0;
        for (int element : setB) {
            if (bloomFilter.contains(element)) {
                foundInB++;
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bloomFilter_output.txt"))) {
            writer.write(foundInA + "\n");
            writer.write(foundInB + "\n");
            System.out.println("Output written to bloomFilter_output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}