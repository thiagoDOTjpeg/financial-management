package br.com.gritti.app.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Invoice implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private Date dueDate;

  private String status;

  private String referenceMonth;

  @Column(name = "created_at")
  private Date createdAt;

  @Column(name = "created_by")
  private Date createdBy;

  @Column(name = "updated_at")
  private Date updatedAt;

  @Column(name = "updated_by")
  private Date updatedBy;

  @ManyToOne
  @JoinColumn(name = "id_credit_card")
  private CreditCard creditCard;

  @OneToMany(mappedBy = "idInvoice")
  private List<CreditTransaction> transactions = new ArrayList<>();

}
