# **Bloom Filters Implementations (Bloom Filter, Counting Bloom Filter, and Coded Bloom filter)**

## **Overview**
This repository contains Java implementations of three **Bloom Filter variants** used for probabilistic membership testing:

- **Standard Bloom Filter**: A space-efficient structure that supports fast insertions and lookups but has false positives.
- **Counting Bloom Filter**: Extends Bloom Filter with counters, allowing deletions.
- **Coded Bloom Filter**: Uses multiple filters to store sets with unique binary codes, enabling set identification.

Each implementation processes distinct elements and evaluates lookup accuracy.

---

## **How It Works**

### **1. Standard Bloom Filter**
- Generates **1,000 elements** (Set A) and encodes them.
- Performs lookup on **Set A** (expected 100% hit rate).
- Generates **1,000 new elements** (Set B) and performs lookup (measuring false positives).
- Outputs:
  - **Number of Set A elements found**.
  - **Number of false positives from Set B**.

### **2. Counting Bloom Filter**
- Generates **1,000 elements** (Set A) and encodes them.
- Removes **500 elements** from Set A.
- Adds **500 new elements**.
- Checks how many **original elements from Set A** are still found.
- Outputs:
  - **Number of Set A elements still present after modifications**.

### **3. Coded Bloom Filter**
- Generates **7 sets of 1,000 elements**, assigning them **unique binary codes (`001` to `111`)**.
- Encodes elements into **3 Bloom Filters**.
- Performs lookup on all elements to verify correct identification.
- Outputs:
  - **Number of elements correctly identified by their binary codes**.

---

## **Compilation & Execution**

### **Compile All Files**
```bash
javac BloomFilter.java CountingBloomFilter.java CodedBloomFilter.java
```

### **Run Standard Bloom Filter**
```bash
java BloomFilter
```

### **Run Counting Bloom Filter**
```bash
java CountingBloomFilter
```

### **Run Coded Bloom Filter**
```bash
java CodedBloomFilter
```

---

## **Output Format**
Each execution generates an output file:
- **`bloomFilter_output.txt`** → Standard Bloom Filter results
- **`counting_bloomFilter_output.txt`** → Counting Bloom Filter results
- **`coded_bloomFilter_output.txt`** → Coded Bloom Filter results

### **Example Standard Bloom Filter Output (`bloomFilter_output.txt`)**
```
1000  # Set A elements found in the filter
6    # False positives from Set B
```

### **Example Counting Bloom Filter Output (`counting_bloomFilter_output.txt`)**
```
505  # Number of original elements still found after modifications
```

### **Example Coded Bloom Filter Output (`coded_bloomFilter_output.txt`)**
```
6745  # Correctly identified elements from all sets
```

---

## **Performance Considerations**
| **Filter Type**        | **Memory Usage** | **False Positives** | **Deletions Supported** | **Set Identification** |
|----------------------|---------------|----------------|-------------------|-------------------|
| Standard Bloom Filter | ✅ Low          | 🔸 Yes            | ❌ No                | ❌ No                |
| Counting Bloom Filter | 🔸 Medium      | 🔸 Yes            | ✅ Yes               | ❌ No                |
| Coded Bloom Filter    | 🔸 Medium      | ✅ Reduced        | ❌ No                | ✅ Yes               |

---

## **Future Improvements**
- Optimize **hash functions** for better distribution.
- Reduce **false positive rates** through adaptive Bloom filters.
- Implement **parallelized encoding and lookup** for speed improvements.

---
