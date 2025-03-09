import java.io.*;
import java.util.*;

public class CodedBloomFilter {
    public static final int sets = 7;   // number of sets
    public static final int n = 1000;    // number of elements in each set
    public static final int f = 3;    // number of filters
    public static final int s = 30000;    // number of bits in each filter
    public static final int h = 7; // number of hashes
    public BitSet[] filters = new BitSet[f];
    public int[] hashSeeds = new int[h];
    public Map<Integer, String> elementCodes = new HashMap<>();
    public static Random rand = new Random();

    public CodedBloomFilter() { 
        for (int i = 0; i < f; i++) {
            filters[i] = new BitSet(s);
        }

        for (int i = 0; i < h; i++) {
            hashSeeds[i] = rand.nextInt(Integer.MAX_VALUE);
        }
    }

    public int hash(int element, int seed) {
        int hash = element ^ seed;
        hash ^= (hash >>> 16);
        return Math.abs(hash % s);
    }

    public void add(int element, String code) {
        elementCodes.put(element, code);
        for (int i = 0; i < f; i++) {
            if (code.charAt(i) == '1') {
                for (int seed : hashSeeds) {
                    filters[i].set(hash(element, seed));
                }
            }
        }
    }

    public String lookup(int element) {
        StringBuilder resultCode = new StringBuilder();
        for (int i = 0; i < f; i++) {
            boolean exists = true;
            for (int seed : hashSeeds) {
                if (!filters[i].get(hash(element, seed))) {
                    exists = false;
                    break;
                }
            }
            resultCode.append(exists ? '1' : '0');
        }
        return resultCode.toString();
    }

    public static void main(String[] args) {
        CodedBloomFilter cbf = new CodedBloomFilter();
        Set<Integer> elements = new HashSet<>();
        int correctLookups = 0;
        
        // Generate distinct elements and encode them
        for (int set = 0; set < sets; set++) {
            String code = String.format("%3s", Integer.toBinaryString(set + 1)).replace(' ', '0');
            while (elements.size() < (set + 1) * n) {
                int element = rand.nextInt(Integer.MAX_VALUE);
                if (!elements.contains(element)) {
                    elements.add(element);
                    cbf.add(element, code);
                }
            }
        }
        
        // Perform lookup and count correct results
        for (int element : elements) {
            String actualCode = cbf.elementCodes.get(element);
            String predictedCode = cbf.lookup(element);
            if (actualCode.equals(predictedCode)) {
                correctLookups++;
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("coded_bloomFilter_output.txt"))) {
            writer.write(correctLookups + "\n");
            System.out.println("Output written to coded_bloomFilter_output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
