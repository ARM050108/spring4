package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   @Autowired
   private UserDao userDao;

   @Autowired
   private SessionFactory sessionFactory;  // Добавляем доступ к SessionFactory

   @Transactional
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   @Transactional(readOnly = true)
   public User getUserByCarModelAndSeries(String model, int series) {
      Session session = sessionFactory.getCurrentSession();  // Получаем текущую сессию
      String hql = "FROM User u JOIN u.car c WHERE c.model = :model AND c.series = :series";
      return (User) session.createQuery(hql)
              .setParameter("model", model)
              .setParameter("series", series)
              .uniqueResult();
   }
}
