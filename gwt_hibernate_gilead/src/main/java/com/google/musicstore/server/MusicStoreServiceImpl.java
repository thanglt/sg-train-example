package com.google.musicstore.server;

import com.google.musicstore.client.MusicStoreService;
import com.google.musicstore.domain.Account;
import com.google.musicstore.domain.Record;
import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.core.serialization.GwtProxySerialization;
import net.sf.gilead.core.store.stateless.StatelessProxyStore;
import net.sf.gilead.gwt.PersistentRemoteService;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class MusicStoreServiceImpl extends PersistentRemoteService implements
        MusicStoreService {

    private HibernateUtil gileadHibernateUtil = new HibernateUtil();

    /**
     * Constructor
     */
    public MusicStoreServiceImpl() {
        gileadHibernateUtil.setSessionFactory(com.google.musicstore.util.HibernateUtil
                .getSessionFactory());

        PersistentBeanManager persistentBeanManager = new PersistentBeanManager();
        persistentBeanManager.setPersistenceUtil(gileadHibernateUtil);
//    persistentBeanManager.setProxyStore(new StatelessProxyStore());

        //https://forum.hibernate.org/viewtopic.php?p=2423685
        StatelessProxyStore sps = new StatelessProxyStore();
        sps.setProxySerializer(new GwtProxySerialization());
        persistentBeanManager.setProxyStore(sps);

        setBeanManager(persistentBeanManager);
    }

    public List<Account> getAccounts() {
        Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Account> accounts = new ArrayList<Account>(session.createQuery(
                "from Account").list());
        if (accounts != null) {
            for (Account account : accounts) {
                // Explicitly load records.
                account.getRecords().size();
            }
        }
        session.getTransaction().commit();
        return accounts;
    }

    public List<Record> getRecords() {
        Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Record> records = new ArrayList<Record>(session.createQuery(
                "from Record").list());
        session.getTransaction().commit();
        return records;
    }

    public Long saveAccount(Account account) {
        Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(account);
        session.getTransaction().commit();
        return account.getId();
    }

    public Long saveRecord(Record record) {
        Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(record);
        session.getTransaction().commit();
        return record.getId();
    }

    public void saveRecordToAccount(Account account, Record record) {
        Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        account = (Account) session.load(Account.class, account.getId());
        record = (Record) session.load(Record.class, record.getId());
        account.addRecord(record);
        session.save(account);
        session.getTransaction().commit();
    }
}