package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession()
              .createQuery("SELECT u FROM User u JOIN FETCH u.car");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      TypedQuery<User> query = sessionFactory.getCurrentSession()
              .createQuery("SELECT u FROM User u JOIN FETCH u.car c WHERE c.model = :model AND c.series = :series", User.class);
      query.setParameter("model", model);
      query.setParameter("series", series);
      List<User> users = query.getResultList();

      if (users.isEmpty()) {
         return null;  // Если нет пользователей с таким автомобилем, возвращаем null
      }

      if (users.size() > 1) {
         // Логируем ситуацию, если найдено несколько пользователей с одинаковыми автомобилями
         System.out.println("Found more than one user with the same car: " + users);
      }

      // Возвращаем первого пользователя из списка, если есть несколько
      return users.get(0);
   }
}
