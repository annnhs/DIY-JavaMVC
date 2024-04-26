package study.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());

        for(Field field : carClass.getDeclaredFields()){
            logger.debug(field.getName());
        }

        for(Method method : carClass.getDeclaredMethods()) {
            logger.info(method.getName());
        }

    }

    @Test
    @DisplayName("test로 시작하는 메서드 실행")
    void testMethodRun() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {

        String test = "test";
        Class<Car> carClass = Car.class;

        for (Method method : carClass.getDeclaredMethods()) {
            if (method.getName().startsWith(test)) {
                logger.debug(method.getName());
                
                Object invoke = method.invoke(carClass.getDeclaredConstructor().newInstance());
                logger.debug(invoke.toString());
            }
        }

    }

    @Test
    @DisplayName("@PrintView 어노테이션 메서드 실행")
    void testAnnotationMethodRun() throws NoSuchMethodException, SecurityException {
        String printView = "printView";
        Class<Car> carClass = Car.class;

        Method printViewMethod = carClass.getMethod(printView);
        printViewMethod.isAnnotationPresent(PrintView.class);
    }

    @Test
    @DisplayName("private field에 값 할당")
    void privateFieldAccess() throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        String name = "name";
        String price = "price";
        String newName = "BMW";
        int newPrice = 130000000;

        Class<Car> carClass = Car.class;
        Car carInstance = carClass.getDeclaredConstructor().newInstance();

        Field nameField = carClass.getDeclaredField(name);
        Field priceField = carClass.getDeclaredField(price);
        nameField.setAccessible(true);
        priceField.setAccessible(true);
        nameField.set(carInstance, newName);
        priceField.set(carInstance, newPrice);

        Assertions.assertThat(carInstance.getName()).isEqualTo(newName);
        Assertions.assertThat(carInstance.getPrice()).isEqualTo(newPrice);
    }

}
