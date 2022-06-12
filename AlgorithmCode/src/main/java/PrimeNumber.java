/**
 * 统计素数个数
 */
public class PrimeNumber {
    public static void main(String[] args) {
//        int count = normal(100);
        int count = efficient(100);
        System.out.println(count);
    }

    private static int efficient(int i) {
        int count = 0;
        boolean[] flag = new boolean[i];
        for (int i1 = 2; i1 < i; i1++) {
            if (!flag[i1]){
                count++;
                for (int i2 = i1; i2*i1 < i; i2++) {
                    flag[i1*i2] = true;
                }
            }
        }
        return count;
    }

    private static int normal(int num) {
        int count = 0;
        for (int i = 2; i <= num; i++) {
            if (isPrime(i)) count++;
        }
        return count;
    }

    private static boolean isPrime(int i) {
        for (int i1 = 2; i1*i1 < i; i1++) {
            if (i % i1 == 0 && i != i1) {
                return false;
            }
        }
        return true;
    }
}
