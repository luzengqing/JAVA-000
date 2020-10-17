package demo.jvm0104;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/zqlu/MyProject/JAVA-000/Week_01/src/main/java/demo/jvm0104/Hello.xlass");
        byte[] bytes = new byte[(int) file.length()];

        new FileInputStream(file).read(bytes);

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)(255 - bytes[i]);
        }

        Class<?> aClass = new HelloClassLoader().defineClass("Hello", bytes, 0, bytes.length);
        Object obj = aClass.newInstance();
        Method method = aClass.getMethod("hello");
        method.invoke(obj);


    }
}

