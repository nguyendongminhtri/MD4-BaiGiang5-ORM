package service;

import model.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class CustomerService {
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;
    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.conf.xml").buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e){
            e.printStackTrace();
        }
    }
    public List<Customer> findAll(){
        String queryStr = "SELECT c FROM Customer AS c";
        TypedQuery<Customer> query = entityManager.createQuery(queryStr,Customer.class);
        return query.getResultList();
    }
    public Customer findById(Long id){
        String queryStr = "SELECT c FROM Customer AS c WHERE c.id =:id";
        Customer customer = entityManager.createQuery(queryStr,Customer.class).setParameter("id",id).getSingleResult();
        return customer;
    }
    public Customer save(Customer customer){
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Customer customer1 = findById(customer.getId());
            customer1.setName(customer.getName());
            customer1.setAddress(customer.getAddress());
            session.saveOrUpdate(customer1);
            transaction.commit();
            return customer1;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        finally {
            if(session !=null){
                session.close();
            }
        }
        return null;
    }

}
