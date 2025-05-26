package br.com.gritti.app.infra.repository;

import br.com.gritti.app.domain.model.Transaction;
import br.com.gritti.app.domain.repository.TransactionRepository;
import br.com.gritti.app.infra.persistence.JpaTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

  private final JpaTransactionRepository jpaTransactionRespository;

  @Autowired
  public TransactionRepositoryImpl(JpaTransactionRepository jpaTransactionRespository) {
    this.jpaTransactionRespository = jpaTransactionRespository;
  }

  @Override
  public Page<Transaction> findAll(Pageable pageable) {
    return jpaTransactionRespository.findAll(pageable);
  }

  @Override
  public Optional<Transaction> findById(UUID id) {
    return jpaTransactionRespository.findById(id);
  }

  @Override
  public Page<Transaction> findAllByUsername(Pageable pageable, String username) {
    return jpaTransactionRespository.findAllByUsername(pageable, username);
  }

  @Override
  public void save(Transaction transaction) {
    jpaTransactionRespository.save(transaction);
  }

  @Override
  public void delete(Transaction transaction) {
    jpaTransactionRespository.delete(transaction);
  }
}
