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

            // DB 테이블의 MESSAGE_TEXT 필드안에 Hello World로 저장이 된다. 
            // MESSAGE_ID는 자동값이 들어가고 NEXT_MESSAGE_ID에는 값이 들어가지 않는다. (null)
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
            
            // DB에 저장되어 있는 데이터들의 MESSAGE_TEXT 내용을 보여준다.
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
            // 앞에서 저장한 Message 클래스의 아이디(MESSGAE_ID) 값으로 영속화된 객체를 가져온다.
            // 가져온 데이터의 MESSAGE_TEXT 값을 Greetings Earthling으로 변경하고,
            // Take me to your leader (please)값을 가진 데이터를 새로 입력한다.
            // 이 때 변경된 기존 데이터의 NEXT_MESSAGE_ID 값에는 
            // setNextMessage로 새로 입력된 객체의 아이디 값이 들어가게 된다.
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
            // 삭제시에 DB에 저장된 두개의 데이터 모두 삭제가 되는데 그 이유는 아직 정확히 모름.
            // 처음에 저장된 객체의 id값으로 가져오는 것이라서 처음에 저장한 객체만 삭제될 것으로 생각됐는데 둘 다 지워짐.
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
