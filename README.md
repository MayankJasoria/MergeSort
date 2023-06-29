# Merge Sort
Implementation of parallel merge sort, and computation of speedup and efficiency against the sequential merge sort.

## Details
Parallel Merge Sort has been implemented using the ForkJoinPool to parallelize the divide and conquer strategy used in Merge Sort. 
The main method compares the average time taken for the number of runs of the sequential and parallel implementations and uses it
to compute the speedup and efficiency of the parallel implementation.

## Sample run
(Tested on a machine with 4 logical cores)
```
Generating random array int[1000000]...
Evaluating Sequential Implementation...
Evaluating Parallel Implementation...
Average Sequential Time: 271.4 ms
Average Parallel Time: 139.4 ms
Speedup: 1.95 
Efficiency: 48.67%
```

## Credits
The starter source code was taken from the course 
[Parallel and Concurrent Programming with Java 2](https://www.linkedin.com/learning/parallel-and-concurrent-programming-with-java-2)
by Barron Stone and Olivia Chiu Stone.
