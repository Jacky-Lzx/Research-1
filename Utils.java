public class Utils {

    /**
     * @param a first index
     * @param b second index
     * @return 1 if exchange happens
     */
    public static int down(int[] arr, int a, int b) {
        if (arr[a] < arr[b]) {
            arr[a] = 1;
            arr[b] = 0;
            return 1;
        }
        return 0;
    }

    /**
     * @param a first index
     * @param b second index
     * @return 1 if exchange happens
     */
    public static int up(int[] arr, int a, int b) {
        if (arr[a] > arr[b]) {
            arr[a] = 0;
            arr[b] = 1;
            return 1;
        }
        return 0;
    }
}
