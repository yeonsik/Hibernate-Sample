package hello;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

public class HelloWorld {

    public static void main(String[] args) {
    	
    	Session session = null;
    	Transaction transaction = null;
    	Message message = null;
    	List messages = null;
    	
    	try {
    		// 저장하기
            session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();

            message = new Message("Hello World");
            session.save(message);
            
            System.out.println( "입력 " );

            transaction.commit();
    	} catch (HibernateException e) {
    		transaction.rollback();
    		e.printStackTrace();
    	} finally {
    		// 세션 닫기
    		HibernateUtil.closeSession();
    	}
    	
    	try {
    		// 목록 불러오기
    		session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();
            
    		messages =
    	            session.createQuery("from Message m order by m.text asc").list();

    			System.out.println( "출력" );
    	        System.out.println( messages.size() + " message(s) found:" );

    	        for ( Iterator iter = messages.iterator(); iter.hasNext(); ) {
    	            Message loadedMsg = (Message) iter.next();
    	            System.out.println( loadedMsg.getText() );
    	        }

    	        transaction.commit();
    	} catch (HibernateException e) {
    		transaction.rollback();
    		e.printStackTrace();
    	} finally {
    		// 세션 닫기
    		HibernateUtil.closeSession();
    	}
    	
    	try {
    		// 변경하기
    		session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();
            
    		// message.getId() holds the identifier value of the first message
            Message loadedMessage = (Message) session.get( Message.class, message.getId());
            loadedMessage.setText( "Greetings Earthling" );
            loadedMessage.setNextMessage(
                new Message( "Take me to your leader (please)" )
            );
            
            System.out.println( "변경" );

            transaction.commit();
    	} catch (HibernateException e) {
    		transaction.rollback();
    		e.printStackTrace();
    	} finally {
    		// 세션 닫기
    		HibernateUtil.closeSession();
    	}
    	
    	try {
    		// 목록 불러오기
    		session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();
            
            messages =
                    session.createQuery("from Message m order by m.text asc").list();
            	
            System.out.println( "출력" );
            System.out.println( messages.size() + " message(s) found:" );

            for ( Iterator iter = messages.iterator(); iter.hasNext(); ) {
                Message loadedMsg = (Message) iter.next();
                System.out.println( loadedMsg.getText() );
            }

            transaction.commit();
    	} catch (HibernateException e) {
    		transaction.rollback();
    		e.printStackTrace();
    	} finally {
    		// 세션 닫기
    		HibernateUtil.closeSession();
    	}
    	
    	try {
    		// 삭제하기
    		session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();
            
    		// message.getId() holds the identifier value of the first message
            Message loadedMessage = (Message) session.get( Message.class, message.getId());
            
            session.delete(loadedMessage);
            
            System.out.println( "삭제" );

            transaction.commit();
    	} catch (HibernateException e) {
    		transaction.rollback();
    		e.printStackTrace();
    	} finally {
    		// 세션 닫기
    		HibernateUtil.closeSession();
    	}
    	
    	try {
    		// 목록 불러오기
    		session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();
            
            messages =
                    session.createQuery("from Message m order by m.text asc").list();

            System.out.println( "출력" );
            System.out.println( messages.size() + " message(s) found:" );

            for ( Iterator iter = messages.iterator(); iter.hasNext(); ) {
                Message loadedMsg = (Message) iter.next();
                System.out.println( loadedMsg.getText() );
            }

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
