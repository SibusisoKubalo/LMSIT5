package org.example.Service;

import org.example.Domain.Reservation;
import org.example.Domain.Book;
import org.example.Domain.Customer;
import org.example.Repository.ReservationRepository;
import org.example.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private BookRepository bookRepository;

    public Reservation reserveBook(Customer customer, Book book) {
        if (book.getAvailableCopies() > 0) {
            throw new RuntimeException("Book is available, no need to reserve.");
        }
        Reservation reservation = new Reservation(customer, book, LocalDate.now());
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByCustomer(int customerId) {
        return reservationRepository.findByCustomer_CustomerId(customerId);
    }

    public List<Reservation> getReservationsByBook(int bookId) {
        return reservationRepository.findByBook_BookId(bookId);
    }

    public List<Reservation> getPendingReservations() {
        return reservationRepository.findByNotifiedFalseAndFulfilledFalse();
    }

    public void notifyReservation(Reservation reservation) {
        reservation.setNotified(true);
        reservationRepository.save(reservation);
        // Integrate with email/SMS notification here if needed
    }

    public void fulfillReservation(Reservation reservation) {
        reservation.setFulfilled(true);
        reservationRepository.save(reservation);
    }
}

