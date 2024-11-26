package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainApp {
   public static void main(String[] args) {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      // Создаем машины
      Car car1 = new Car("BMW", 5);
      Car car2 = new Car("Audi", 3);
      Car car3 = new Car("Tesla", 2);

      // Создаем пользователей с машинами
      userService.add(new User("User1", "Lastname1", "user1@mail.ru", car1));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru", car2));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru", car3));

      // Вывод всех пользователей
      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println(user);
      }

      // Получение пользователя по машине
      User userByCar = userService.getUserByCarModelAndSeries("BMW", 5);
      System.out.println("User with car: " + userByCar);


      context.close();
   }
}
