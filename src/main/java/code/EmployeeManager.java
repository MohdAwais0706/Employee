package code;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class EmployeeManager {
	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();

		addEmployee(session);

		System.out.println("Displaying required data ");
		System.out.println("Employee with salart grater then 50000");
		getEmployeesBySalary(session, 50000);
		System.out.println("Success");

		System.out.println("Displaying Required data:");
		System.out.println("Employees with specific designation");
		getEmployeesByDesignation(session, "devloper");
	}

	private static void getEmployeesByDesignation(Session session, String designation) {
		Criteria criteria = session.createCriteria(Employees.class);
		criteria.add(Restrictions.eq("designation", designation));
		List<Employees> emps = criteria.list();

		for (Employees employees : emps) {
			System.out.println(employees);
		}
	}

	private static void getEmployeesBySalary(Session session, int salary) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Employees> query = builder.createQuery(Employees.class);
		Root<Employees> root = query.from(Employees.class);
		query.select(root).where(builder.gt(root.get("salary"), 50000));
		List<Employees> emps = session.createQuery(query).getResultList();
		for (Employees employees : emps) {
			System.out.println(employees);
		}

//		Criteria criteria = session.createCriteria(Employees.class);
//		criteria.add(Restrictions.gt("salary", salary));
//		List<Employees> employees= criteria.list();
//		for (Employees employees2 : employees) {
//			System.out.println(employees2);
//		}

	}

	private static void addEmployee(Session session) {
		Transaction tx = session.beginTransaction();

		Employees e1 = new Employees();
		e1.setName("Awais");
		e1.setDesignation("Devloper");
		e1.setSalary(60000);
		Employees e2 = new Employees();
		e2.setName("Rakesh");
		e2.setDesignation("Manager");
		e2.setSalary(80000);
		session.save(e1);
		session.save(e2);
		tx.commit();

	}
}
