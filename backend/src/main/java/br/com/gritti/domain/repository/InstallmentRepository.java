package br.com.gritti.domain.repository;

import br.com.gritti.domain.model.Installment;
import br.com.gritti.shared.dto.response.CancelInstallmentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InstallmentRepository extends JpaRepository<Installment, UUID> {
  @Query("UPDATE Installment it SET it.deletedAt = CURRENT_TIMESTAMP ")
  @Modifying
  void cancelInstallment(UUID id);


  @Query(value = """
        SELECT * FROM json_to_record(cancel_installment(:userId, :installmentId))
        AS r(
            "installmentId" UUID,
            description TEXT,
            "cancelledTransactions" INTEGER,
            "totalAmount" DECIMAL(10, 2),
            message TEXT
        )
    """,
    nativeQuery = true
  )
  @Modifying
  CancelInstallmentResponse cancelInstallmentManual(@Param("installmentId") UUID installmentId,
                                                    @Param("userId") UUID userId);
}
