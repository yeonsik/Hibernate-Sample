import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class Test {
	public static void main(String[] args) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			// 세션 열기
			session = HibernateUtil.getCurrentSession();
			// 트랜잭션 시작
			transaction = session.beginTransaction();
			
			// processing...
			
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			// 세션 닫기
			HibernateUtil.closeSession();
		}
	}
}
