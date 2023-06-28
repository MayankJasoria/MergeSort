import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/* parallel implementation of merge sort */
class ParallelMergeSorter {

    private final int[] array;

    public ParallelMergeSorter(int[] array) {
        this.array = array;
    }

    /* returns sorted array */
    public int[] sort() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(new ParallelMergeSort(0, array.length - 1));
        return array;
    }

    /* helper method that gets called recursively */
    private void sort(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2; // find the middle point
            sort(left, mid); // sort the left half
            sort(mid + 1, right); // sort the right half
            merge(left, mid, right); // merge the two sorted halves
        }
    }

    /* helper method to merge two sorted subarrays array[l..m] and array[m+1..r] into array */
    private void merge(int left, int mid, int right) {
        // copy data to temp subarrays to be merged
        int[] leftTempArray = Arrays.copyOfRange(array, left, mid + 1);
        int[] rightTempArray = Arrays.copyOfRange(array, mid + 1, right + 1);

        // initial indexes for left, right, and merged subarrays
        int leftTempIndex = 0, rightTempIndex = 0, mergeIndex = left;

        // merge temp arrays into original
        while (leftTempIndex < mid - left + 1 || rightTempIndex < right - mid) {
            if (leftTempIndex < mid - left + 1 && rightTempIndex < right - mid) {
                if (leftTempArray[leftTempIndex] <= rightTempArray[rightTempIndex]) {
                    array[mergeIndex] = leftTempArray[leftTempIndex];
                    leftTempIndex++;
                } else {
                    array[mergeIndex] = rightTempArray[rightTempIndex];
                    rightTempIndex++;
                }
            } else if (leftTempIndex < mid - left + 1) { // copy any remaining on left side
                array[mergeIndex] = leftTempArray[leftTempIndex];
                leftTempIndex++;
            } else if (rightTempIndex < right - mid) { // copy any remaining on right side
                array[mergeIndex] = rightTempArray[rightTempIndex];
                rightTempIndex++;
            }
            mergeIndex++;
        }
    }

    private class ParallelMergeSort extends RecursiveAction {

        final int left;
        final int right;

        ParallelMergeSort(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if (right - left > 100) {
                int mid = left + (right - left) / 2;
                ParallelMergeSort leftPart = new ParallelMergeSort(this.left, mid);
                ParallelMergeSort rightPart = new ParallelMergeSort(mid + 1, this.right);
                rightPart.fork();
                leftPart.compute();
                rightPart.join();
                merge(left, mid, right);
            } else if (left < right) {
                int mid = left + (right - left) / 2;
                sort(left, mid);
                sort(mid + 1, right);
                merge(left, mid, right);
            }
        }
    }
}
