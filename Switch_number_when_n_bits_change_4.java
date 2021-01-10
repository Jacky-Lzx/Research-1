import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Switch_number_when_n_bits_change_4 {
    int[][] arr;
    List<List<int[]>> switches = new ArrayList<>(9);
    public static void main(String[] args) {
        Switch_number_when_n_bits_change_4 p = new Switch_number_when_n_bits_change_4();

        for (int i = 0; i < 5; i++) {
            p.switches.add(new ArrayList<>());
        }
        p.get_all_conditions();


//        for (double i = 0; i < 1; i += 0.05) {
//            p.simulation(i, 0, 0);
//        }
/*        for (double i = 0; i < 1; i += 0.05) {
            for (double j = 0; j < 1; j += 0.05) {
                p.simulation(-0.2, i, j);
            }
        }*/

//        sum and average switch num for n bits change in the input
        for (int i = 0; i < p.switches.size(); i++) {
            int sum_switch = 0;
            int min = 100;
            int max = 0;
            for (int[] a : p.switches.get(i)) {
                sum_switch += a[2];
                min = Math.min(min, a[2]);
                max = Math.max(max, a[2]);
            }
            System.out.println(i + "," + sum_switch + "," + (double)sum_switch / p.switches.get(i).size());
            System.out.println("\t" + min);
            System.out.println("\t" + max);
        }

        int[] temps = new int[12];
        

//      data for plotting the discrete graph
/*        for (int i = 0; i < p.switches.size(); i++) {
            for (int[] a : p.switches.get(i)) {
                System.out.print("{" + i + "," + a[2] + "},");
            }
            System.out.println();
        }*/
    }

    boolean[][] count;
    final int TOTAL_NUM = 1000000;
    void simulation(double probability, double prob2, double prob1) {
        count = new boolean[4][4];

        Random rand = new Random();

        double split1 = prob2;
        double split2 = 1 - prob1;

        long sum = 0;

        initiate(new int[]{0, 0, 0, 0, 0, 0, 0, 0});
        for (int i = 0; i < TOTAL_NUM; i++) {
            int[] next = new int[4];
            if (probability < 0) {
                for (int j = 0; j < 2; j++) {
                    double t = rand.nextDouble();
                    if (t < split1) {
                        next[j * 2] = 0;
                        next[j * 2 + 1] = 0;
                    } else if (t < split2) {
                        next[j * 2] = 1;
                        next[j * 2 + 1] = 0;
                    } else {
                        next[j * 2] = 1;
                        next[j * 2 + 1] = 1;
                    }
                }
            } else {
                for (int j = 0; j < 4; j++) {
                    double t = rand.nextDouble();
                    if (t < probability) {
                        next[j] = 1;
                    } else
                        next[j] = 0;
                }
            }
            sum += update(next, true);
        }
        if (probability < 0) {
            System.out.print("{" + prob2 + "," + prob1 + "," + (double)sum/ TOTAL_NUM + "},");
        } else {
            System.out.print("{" + probability + "," + (double)sum/ TOTAL_NUM + "},");
        }
    }

    void get_all_conditions() {
        helper_first(new int[4], 0);
    }

    int first_num = 0;
    void helper_first(int[] origin, int start) {
        if (start == 4) {
            initiate(origin);
            System.out.println(Arrays.toString(origin) + ":");
            first_num = convert(origin);
            helper_second(new int[4], 0);
            return;
        }

        origin[start] = 0;
        helper_first(origin, start + 1);
        origin[start] = 1;
        helper_first(origin, start + 1);
    }

    void helper_second(int[] second, int start) {
        if (start == 4) {

            int sum = update(second, false);
            int t = first_num;
//            for (int i = 0; i < 4; i++) {
//                if ((t & 3) == 1)
//                    return;
//                t = t >> 2;
//            }
            t = convert(second);
//            for (int i = 0; i < 4; i++) {
//                if ((t & 3) == 1)
//                    return;
//                t = t >> 2;
//            }
            System.out.println("\t" + Arrays.toString(second) + "   ---   " + sum);


//            calculate how many bits are changed
            int tNum = 0;
            int tFirst = first_num;
            for (int i = 3; i >= 0; i--) {
                if ((tFirst & 1) != second[i])
                    tNum++;
                tFirst >>= 1;
            }
            switches.get(tNum).add(new int[]{first_num, t, sum});

//            System.out.print("{" + first_num + "," + convert(second) + "," + sum + "},");
            return;
        }

        second[start] = 0;
        helper_second(second, start + 1);
        second[start] = 1;
        helper_second(second, start + 1);
    }

    int convert(int[] array) {
        int num = 0;
        for (int i = 0; i < 4; i++)
            num = num * 2 + array[i];

        return num;
    }

    void initiate(int[] origin) {
        arr = new int[4][4];
        arr[0] = Arrays.copyOf(origin, 4);

        int sum = 0;
//        step 1
        arr[1] = Arrays.copyOf(arr[0], 4);
        sum += arrow(1, 0, 1) + arrow(1, 3, 2);

//        step 2
        arr[2] = Arrays.copyOf(arr[1], 4);
        sum += arrow(2, 0, 2) + arrow(2, 1, 3);

//        step 3
        arr[3] = Arrays.copyOf(arr[2], 4);
        sum += arrow(3, 0, 1) + arrow(3, 2, 3);

//        System.out.println(sum);
    }

    int update(int[] next_origin, boolean update) {
        int[] next = Arrays.copyOf(next_origin, 4);

        int sum = 0;
        sum += get_sum(next, 0, update);

        int temp = 0;
//        step 1
        temp += arrow(next, 0, 1) + arrow(next, 3, 2);
        sum += get_sum(next, 1, update);

//        step 2
        temp += arrow(next, 0, 2) + arrow(next, 1, 3);
        sum += get_sum(next, 2, update);

//        step 3
        temp += arrow(next, 0, 1) + arrow(next, 2, 3);
        sum += get_sum(next, 3, update);

        return sum;
    }


/*    int get_sum(int[] next, int index, boolean update) {
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            if (arr[index][i] != next[i])
                sum++;
        }
//        copy
        if (update)
            arr[index] = Arrays.copyOf(next, 8);

*//*        sum = 0;
        for (int i = 0; i < 8; i++) {
            if (count[index][i] && arr[index][i] == 1)
                sum++;
        }*//*

        return sum;
    }*/
    int get_sum(int[] next, int index, boolean update) {
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            if (arr[index][i] != next[i]) {
                sum++;
            }
        }
//        copy
        if (update) {
            arr[index] = Arrays.copyOf(next, 4);
        }
        return sum;
    }

    int arrow(int index, int from, int to) {
        if (arr[index][from] == 0 && arr[index][to] == 1) {
            arr[index][from] = 1;
            arr[index][to] = 0;
            return 1;
        }
        return 0;
    }

    int arrow(int[] array, int from, int to) {
        if (array[from] == 0 && array[to] == 1) {
            array[from] = 1;
            array[to] = 0;
            return 1;
        }
        return 0;
    }
}
