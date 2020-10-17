package demo.jvm0104;

public class Hello {

    public static void main(String[] args) {
        int a = 1;
        int b = 10;
        boolean flag = true;

        for (int i = 0; i < b; i++) {
            a+=i;
        }

        if (flag) {
            a+=100;
        }
    }
}
