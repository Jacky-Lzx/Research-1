import jdk.jshell.spi.ExecutionControlProvider;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {

    private static final int N = 8;
    private int[] arr;

    public static void main(String[] args){
        Main m = new Main();

        m.arr = new int[]{0,1,1,1,0,0,1,1};

        StringBuilder optimized = new StringBuilder();
        StringBuilder unOptimized = new StringBuilder();


        List<String> list = new ArrayList<>();

        m.helper(new int[8], 4, 0);

        for (Map.Entry e : m.map.entrySet()) {
            System.out.println(e.getKey() + ":" + e.getValue());
        }

/*        for (int i = 0; i < 256; i++) {
            Arrays.fill(m.arr, 0);
            char[] t = Integer.toBinaryString(i).toCharArray();
            for (int a = 0; a < t.length; a++) {
                m.arr[N - 1 - a] = t[t.length - 1 - a] - '0';
            }


            System.out.print(Arrays.toString(m.arr) + " Sum: ");
            int[] sums = m.Bitonic_sorting();
            *//*try {
                m.check();
            } catch (Exception e) {
                System.out.println("Wrong sorting: ");
            }*//*
            System.out.println(Arrays.toString(sums));
//            optimized.append(",{" + i + "," + sums[0] + "}");
//            unOptimized.append(",{" + i + "," + sums[1] + "}");

//            if (sums[0] != sums[1])
//                list.add(i + ": " + "Optimized-" + sums[0] + " " + "Unoptimized-" + sums[1]);
        }*/

//        System.out.println(optimized.toString());
//        System.out.println(unOptimized.toString());


//        for (String a : list)
//            System.out.println(a);

    }

    Map<Integer, Integer> map = new HashMap<>();
    void combined_output(int[] arr1, int[] sums) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (arr1[i * 2] == 0 && arr1[i * 2 + 1] == 0)
//                Use 2 to represent -1
                sb.append(2);
            else if (arr1[i * 2] == 1 && arr1[i * 2 + 1] == 0)
                sb.append(0);
            else
                sb.append(1);
        }
        int[] nums = new int[3];
        for (char a : sb.toString().toCharArray()) {
            switch (a) {
                case '2' -> nums[0]++;
                case '0' -> nums[1]++;
                case '1' -> nums[2]++;
                default -> System.out.println("Wrong");
            }
        }

        int t = nums[0] * 100 + nums[1] * 10 + nums[2];
        map.put(t, map.getOrDefault(t, 0) + sums[1]);
        if (sums[1] != sums[0])
            System.out.println("Delete: " + Arrays.toString(nums) + ":" + (sums[1] - sums[0]));
    }

    void helper(int[] arr1, int num, int now) {
        if (now == num) {
//            System.out.print(Arrays.toString(arr1) + " Sum: ");
            arr = Arrays.copyOf(arr1, 8);
            int[] sums = Bitonic_sorting();
            /*try {
                m.check();
            } catch (Exception e) {
                System.out.println("Wrong sorting: ");
            }*/
//            System.out.println(Arrays.toString(sums));
            /*StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                if (arr1[i * 2] == 0 && arr1[i * 2 + 1] == 0)
                    sb.append(-1);
                else if (arr1[i * 2] == 1 && arr1[i * 2 + 1] == 0)
                    sb.append(0);
                else
                    sb.append(1);
                sb.append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            if (sums[1] != sums[0])
                System.out.println(Arrays.toString(sums) + "x(" + sb.toString() + ")");
            else
                System.out.println(sums[1] + "x(" + sb.toString() + ")");*/

            combined_output(arr1, sums);
            return;
        }

        for (int j = -1; j <= 1; j++) {
            switch (j) {
                case -1 -> {
                    arr1[now * 2] = 0;
                    arr1[now * 2 + 1] = 0;
                }
                case 0 -> {
                    arr1[now * 2] = 1;
                    arr1[now * 2 + 1] = 0;
                }
                case 1 -> {
                    arr1[now * 2] = 1;
                    arr1[now * 2 + 1] = 1;
                }
                default -> System.out.println("Wrong");
            }

            helper(arr1, num, now + 1);
        }
    }



    void check() throws Exception {
        int index = 0;
        while (index < N && arr[index] == 1)
            index++;
        while (index < N && arr[index] == 0)
            index++;
        if (index != N)
            throw new ArrayIndexOutOfBoundsException();
    }

    int[] Bitonic_sorting() {
        int sum = 0;

//        1
        sum += down(0, 1) + up(2, 3) + down(4, 5) + up(6, 7);
//        2
        sum += down(0, 2) + down(1, 3) + up(4, 6) + up(5, 7);
//        3
        sum += down(0, 1) + down(2, 3) + up(4, 5) + up(6, 7);

//        4
        sum += down(0, 4) + down(1, 5) + down(2, 6) + down(3, 7);
//        5
        sum += down(0, 2) + down(1, 3) + down(4, 6) + down(5, 7);
//        6
//        Optimized
        sum += down(2, 3) + down(4, 5);
//        Unoptimized
//        sum += down(0, 1) + down(2, 3) + down(4, 5) + down(6, 7);

        return new int[]{sum, sum + down(0, 1) + down(6, 7)};
    }

    /**
     * @param a first index
     * @param b second index
     * @return 1 if exchange happens
     */
    int down(int a, int b) {
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
    int up(int a, int b) {
        if (arr[a] > arr[b]) {
            arr[a] = 0;
            arr[b] = 1;
            return 1;
        }
        return 0;
    }

}
