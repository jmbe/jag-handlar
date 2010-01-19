package se.jaghandlar.hibernate

import org.hibernate.FlushMode
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessResourceFailureException
import org.springframework.orm.hibernate3.SessionFactoryUtils
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter

class FlushingOpenSessionInView extends OpenSessionInViewFilter {

  def log = LoggerFactory.getLogger(FlushingOpenSessionInView.class)

  protected Session getSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
    log.debug "Returning session with flush mode auto."

    Session session = SessionFactoryUtils.getSession(sessionFactory, true);
    session.setFlushMode(FlushMode.AUTO);
    return session;
  }


  protected void closeSession(Session session, SessionFactory factory) {
    log.debug "Flushing before closing session."

    session.flush();
    super.closeSession(session, factory);
  }


}






