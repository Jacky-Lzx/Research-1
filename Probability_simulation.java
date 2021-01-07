import java.util.Arrays;
import java.util.Random;


public class Probability_simulation {

    static final int NUM = 1000000;

    public static void main(String[] args) {
        Random ran = new Random();
        double split1 = 0.9;
        double split2 = 0.95;

//        one_input(ran, split1, split2);
        four_input(ran, split1, split2);
    }

    static void one_input(Random ran, double split1, double split2) {
        int[] arr = new int[2];
        int case1 = 0;
        int case2 = 0;
        for (int i = 0; i < NUM; i++) {
            int a = generate(ran, split1, split2);
            switch (a) {
                case -1:
                    arr = new int[]{0, 0};
                    break;
                case 0:
                    arr = new int[]{1, 0};
                    break;
                case 1:
                    arr = new int[]{1, 1};
                    break;
                default:
                    System.out.println("Wrong");
            }
            case1 += Utils.up(Arrays.copyOf(arr, 2), 0, 1);
            case2 += Utils.down(Arrays.copyOf(arr, 2), 0, 1);
        }

        System.out.println("----One-input:");

        System.out.println("Case1: " + case1);
        System.out.println("\tAverage1: " + (double) case1 / NUM);

        System.out.println("Case2: " + case2);
        System.out.println("\tAverage2: " + (double) case2 / NUM);
    }

    static void helper(int[] arr, int num, int now) {
        if (now == num) {
            return;
        }

        for (int j = -1; j <= 1; j++) {
            switch (j) {
                case -1 -> {
                    arr[now * 2] = 0;
                    arr[now * 2 + 1] = 0;
                }
                case 0 -> {
                    arr[now * 2] = 1;
                    arr[now * 2 + 1] = 0;
                }
                case 1 -> {
                    arr[now * 2] = 1;
                    arr[now * 2 + 1] = 1;
                }
                default -> System.out.println("Wrong");
            }

            helper(arr, num, now + 1);
        }
    }

    static void input_table(int num) {
        int len = num * 2;
        int[] arr = new int[len];
        helper(arr, num, 0);
    }

    static void two_input(Random ran, double split1, double split2) {
        int[] arr = new int[4];
        int case1 = 0;
        int case2 = 0;
        for (int i = 0; i < NUM; i++) {
            for (int j = 0; j < 2; j++) {
                int a = generate(ran, split1, split2);
                switch (a) {
                    case -1:
                        arr[j * 2] = 0;
                        arr[j * 2 + 1] = 0;
                        break;
                    case 0:
                        arr[j * 2] = 1;
                        arr[j * 2 + 1] = 0;
                        break;
                    case 1:
                        arr[j * 2] = 1;
                        arr[j * 2 + 1] = 1;
                        break;
                    default:
                        System.out.println("Wrong");
                }
            }
            int[] arr1 = Arrays.copyOf(arr, 4);
            int[] arr2 = Arrays.copyOf(arr, 4);
            case1 += Utils.down(arr1, 0, 1) + Utils.up(arr1, 2, 3) + Utils.up(arr1, 0, 2) + Utils.up(arr1, 1, 3) + Utils.up(arr1, 0, 1) + Utils.up(arr1, 2, 3);
            case2 += Utils.down(arr2, 0, 1) + Utils.up(arr2, 2, 3) + Utils.down(arr2, 0, 2) + Utils.down(arr2, 1, 3) + Utils.down(arr2, 0, 1) + Utils.down(arr2, 2, 3);
        }

        System.out.println("----Two-input:");

        System.out.println("Case1: " + case1);
        System.out.println("\tAverage1: " + (double) case1 / NUM);

        System.out.println("Case2: " + case2);
        System.out.println("\tAverage2: " + (double) case2 / NUM);
    }

    static void four_input(Random ran, double split1, double split2) {
        int[] arr = new int[8];
        int case1 = 0;
        int case2 = 0;
        for (int i = 0; i < NUM; i++) {
            for (int j = 0; j < 4; j++) {
                int a = generate(ran, split1, split2);
                switch (a) {
                    case -1:
                        arr[j * 2] = 0;
                        arr[j * 2 + 1] = 0;
                        break;
                    case 0:
                        arr[j * 2] = 1;
                        arr[j * 2 + 1] = 0;
                        break;
                    case 1:
                        arr[j * 2] = 1;
                        arr[j * 2 + 1] = 1;
                        break;
                    default:
                        System.out.println("Wrong");
                }
            }
            int[] arr1 = Arrays.copyOf(arr, 8);
            int[] arr2 = Arrays.copyOf(arr, 8);
            case1 += Utils.down(arr1, 0, 1) + Utils.up(arr1, 2, 3) + Utils.down(arr1, 4, 5) + Utils.up(arr1, 6, 7);
//        2
            case1 += Utils.down(arr1, 0, 2) + Utils.down(arr1, 1, 3) + Utils.up(arr1, 4, 6) + Utils.up(arr1, 5, 7);
//        3
            case1 += Utils.down(arr1, 0, 1) + Utils.down(arr1, 2, 3) + Utils.up(arr1, 4, 5) + Utils.up(arr1, 6, 7);

//        4
            case1 += Utils.down(arr1, 0, 4) + Utils.down(arr1, 1, 5) + Utils.down(arr1, 2, 6) + Utils.down(arr1, 3, 7);
//        5
            case1 += Utils.down(arr1, 0, 2) + Utils.down(arr1, 1, 3) + Utils.down(arr1, 4, 6) + Utils.down(arr1, 5, 7);
//        6
//        Optimized
//            case1 += Utils.down(arr1, 2, 3) + Utils.down(arr1, 4, 5);
//        Unoptimized
            case1 += Utils.down(arr1, 0, 1) + Utils.down(arr1, 2, 3) + Utils.down(arr1, 4, 5) + Utils.down(arr1, 6, 7);
//            case2 += Utils.down(arr2, 0, 1) + Utils.up(arr2, 2, 3) + Utils.down(arr2, 0, 2) + Utils.down(arr2, 1, 3) + Utils.down(arr2, 0, 1) + Utils.down(arr2, 2, 3);
        }

        System.out.println("----Four-input:");

        System.out.println("Case1: " + case1);
        System.out.println("\tAverage1: " + (double) case1 / NUM);

//        System.out.println("Case2: " + case2);
//        System.out.println("\tAverage2: " + (double) case2 / NUM);
    }


    static int generate(Random ran, double split1, double split2) {
        assert 0 <= split1 && split1 <= split2 && split2 <= 1;

        double r = ran.nextDouble();
        if (r < split1)
            return -1;
        else if (r < split2)
            return 0;
        else
            return 1;
    }
}
