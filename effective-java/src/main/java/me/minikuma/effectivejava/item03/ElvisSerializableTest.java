package me.minikuma.effectivejava.item03;

import java.io.*;

public class ElvisSerializableTest {

    public byte[] serialize(Object o) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream objects = new ObjectOutputStream(bytes);
        try {
            objects.writeObject(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes.toByteArray();
    }

    public Object deserialize(byte[] serializedData) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(serializedData);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        try {
            return objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        ElvisSerializable elvis = ElvisSerializable.getInstance();
        ElvisSerializableTest test = new ElvisSerializableTest();

        byte[] serializedData = test.serialize(elvis);
        Object deserializeObject = test.deserialize(serializedData);

        System.out.println("싱글톤 객체를 직렬화 후 역직렬화한 객체 == 싱글톤 객체 (같은 객체) | " + (elvis == deserializeObject));
        System.out.println("싱글톤 객체를 직렬화 후 역직렬화한 객체.equals(싱글톤 객체) (같은 값) | " + elvis.equals(deserializeObject));
    }
}
