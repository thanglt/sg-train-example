package com.google.musicstore.server;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.hibernate.Session;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.musicstore.client.MusicStoreService;
import com.google.musicstore.client.dto.AccountDTO;
import com.google.musicstore.client.dto.RecordDTO;
import com.google.musicstore.domain.Account;
import com.google.musicstore.domain.Record;
import com.google.musicstore.util.HibernateUtil;

public class MusicStoreServiceImpl extends RemoteServiceServlet implements
    MusicStoreService {

  public List<AccountDTO> getAccounts() {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    List<Account> accounts = new ArrayList<Account>(session.createQuery(
        "from Account").list());
    List<AccountDTO> accountDTOs = new ArrayList<AccountDTO>(
        accounts != null ? accounts.size() : 0);
    if (accounts != null) {
      for (Account account : accounts) {
        accountDTOs.add(DozerBeanMapperSingletonWrapper.getInstance().map(
            account, AccountDTO.class));
      }
    }
    session.getTransaction().commit();
    return accountDTOs;
  }

  public List<RecordDTO> getRecords() {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    List<Record> records = new ArrayList<Record>(session.createQuery(
        "from Record").list());
    List<RecordDTO> recordDTOs = new ArrayList<RecordDTO>(
        records != null ? records.size() : 0);
    if (records != null) {
      for (Record record : records) {
        recordDTOs.add(DozerBeanMapperSingletonWrapper.getInstance().map(
            record, RecordDTO.class));
      }
    }
    session.getTransaction().commit();
    return recordDTOs;
  }

  public Long saveAccount(AccountDTO accountDTO) {
    Account account = DozerBeanMapperSingletonWrapper.getInstance().map(
        accountDTO, Account.class);
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    session.save(account);
    session.getTransaction().commit();
    return account.getId();
  }

  public Long saveRecord(RecordDTO recordDTO) {
    DozerBeanMapper mapper = new DozerBeanMapper();
    Record record = mapper.map(recordDTO, Record.class);
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    session.save(record);
    session.getTransaction().commit();
    return record.getId();
  }

  public void saveRecordToAccount(AccountDTO accountDTO, RecordDTO recordDTO) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    Account account = (Account) session.load(Account.class, accountDTO.getId());
    Record record = (Record) session.load(Record.class, recordDTO.getId());
    account.addRecord(record);
    session.save(account);
    session.getTransaction().commit();
  }

  public List<AccountDTO> getAllAccountRecords() {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    List<Account> accounts = new ArrayList<Account>(session.createQuery(
        "from Account").list());
    List<AccountDTO> accountDTOs = new ArrayList<AccountDTO>(
        accounts != null ? accounts.size() : 0);
    if (accounts != null) {
      for (Account account : accounts) {
        accountDTOs.add(DozerBeanMapperSingletonWrapper.getInstance().map(
            account, AccountDTO.class));
      }
    }
    session.getTransaction().commit();
    return accountDTOs;
  }
}