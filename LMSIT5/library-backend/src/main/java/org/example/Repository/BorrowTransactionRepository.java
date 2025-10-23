package org.example.Repository;

import org.example.Domain.BorrowTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction, Long> {
    List<BorrowTransaction> findByUser_Id(Long userId);
    List<BorrowTransaction> findByBook_BookId(int bookId);
}
