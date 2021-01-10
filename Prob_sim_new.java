import java.util.Arrays;
import java.util.Random;

public class Prob_sim_new {
    int[][] arr;
    public static void main(String[] args) {

        Prob_sim_new p = new Prob_sim_new();
        p.get_all_conditions();
//        for (double i = 0; i < 1; i += 0.05) {
//            p.simulation(i, 0, 0);
//        }
/*        for (double i = 0; i < 1; i += 0.05) {
            for (double j = 0; j < 1; j += 0.05) {
                p.simulation(-0.2, i, j);
            }
        }*/
    }

    boolean[][] count;
    final int TOTAL_NUM = 1000000;
    void simulation(double probability, double prob2, double prob1) {
        count = new boolean[7][8];
        count[3][0] = true;

        Random rand = new Random();

        double split1 = prob2;
        double split2 = 1 - prob1;

        long sum = 0;

        initiate(new int[]{0, 0, 0, 0, 0, 0, 0, 0});
        for (int i = 0; i < TOTAL_NUM; i++) {
            int[] next = new int[8];
            if (probability < 0) {
                for (int j = 0; j < 4; j++) {
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
                for (int j = 0; j < 8; j++) {
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
        Prob_sim_new m = new Prob_sim_new();
        m.helper_first(new int[8], 0);
    }

    int first_num = 0;
    void helper_first(int[] origin, int start) {
        if (start == 8) {
            initiate(origin);
            System.out.println(Arrays.toString(origin) + ":");
            first_num = convert(origin);
            helper_second(new int[8], 0);
            return;
        }

        origin[start] = 0;
        helper_first(origin, start + 1);
        origin[start] = 1;
        helper_first(origin, start + 1);
    }

    void helper_second(int[] second, int start) {
        if (start == 8) {

            int sum = update(second, false);
            int t = first_num;
            for (int i = 0; i < 4; i++) {
                if ((t & 3) == 1)
                    return;
                t = t >> 2;
            }
            t = convert(second);
            for (int i = 0; i < 4; i++) {
                if ((t & 3) == 1)
                    return;
                t = t >> 2;
            }
            System.out.println("\t" + Arrays.toString(second) + "   ---   " + sum);
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
        for (int i = 0; i < 8; i++)
            num = num * 2 + array[i];

        return num;
    }

    void initiate(int[] origin) {
        arr = new int[7][8];
        arr[0] = Arrays.copyOf(origin, 8);

        int sum = 0;
//        step 1
        arr[1] = Arrays.copyOf(arr[0], 8);
        sum += arrow(1, 0, 1) + arrow(1, 3, 2) + arrow(1, 4, 5) + arrow(1, 7, 6);

//        step 2
        arr[2] = Arrays.copyOf(arr[1], 8);
        sum += arrow(2, 0, 2) + arrow(2, 1, 3) + arrow(2, 6, 4) + arrow(2, 7, 5);

//        step 3
        arr[3] = Arrays.copyOf(arr[2], 8);
        sum += arrow(3, 0, 1) + arrow(3, 2, 3) + arrow(3, 5, 4) + arrow(3, 7, 6);

//        step 4
        arr[4] = Arrays.copyOf(arr[3], 8);
        sum += arrow(4, 0, 4) + arrow(4, 1, 5) + arrow(4, 2, 6) + arrow(4, 3, 7);

//        step 5
        arr[5] = Arrays.copyOf(arr[4], 8);
        sum += arrow(5, 0, 2) + arrow(5, 1, 3) + arrow(5, 4, 6) + arrow(5, 5, 7);

//        step 6
        arr[6] = Arrays.copyOf(arr[5], 8);
        sum += arrow(6, 0, 1) + arrow(6, 2, 3) + arrow(6, 4, 5) + arrow(6, 6, 7);

//        System.out.println(sum);
    }

    int update(int[] next_origin, boolean update) {
        int[] next = Arrays.copyOf(next_origin, 8);

        int sum = 0;
        sum += get_sum(next, 0, update);

        int temp = 0;
//        step 1
        temp += arrow(next, 0, 1) + arrow(next, 3, 2) + arrow(next, 4, 5) + arrow(next, 7, 6);
        sum += get_sum(next, 1, update);

//        step 2
        temp += arrow(next, 0, 2) + arrow(next, 1, 3) + arrow(next, 6, 4) + arrow(next, 7, 5);
        sum += get_sum(next, 2, update);

//        step 3
        temp += arrow(next, 0, 1) + arrow(next, 2, 3) + arrow(next, 5, 4) + arrow(next, 7, 6);
        sum += get_sum(next, 3, update);

//        step 4
        temp += arrow(next, 0, 4) + arrow(next, 1, 5) + arrow(next, 2, 6) + arrow(next, 3, 7);
        sum += get_sum(next, 4, update);

//        step 5
        temp += arrow(next, 0, 2) + arrow(next, 1, 3) + arrow(next, 4, 6) + arrow(next, 5, 7);
        sum += get_sum(next, 5, update);

//        step 6
        temp += arrow(next, 0, 1) + arrow(next, 2, 3) + arrow(next, 4, 5) + arrow(next, 6, 7);
        sum += get_sum(next, 6, update);

        return sum;
    }


    int get_sum(int[] next, int index, boolean update) {
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            if (arr[index][i] != next[i])
                sum++;
        }
//        copy
        if (update)
            arr[index] = Arrays.copyOf(next, 8);

/*        sum = 0;
        for (int i = 0; i < 8; i++) {
            if (count[index][i] && arr[index][i] == 1)
                sum++;
        }*/

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
