package org.example.Repository;

import org.example.Domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCustomer_CustomerId(int customerId);
    List<Reservation> findByBook_BookId(int bookId);
    List<Reservation> findByNotifiedFalseAndFulfilledFalse();
}

