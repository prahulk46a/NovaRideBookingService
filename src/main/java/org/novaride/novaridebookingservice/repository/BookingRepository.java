package org.novaride.novaridebookingservice.repository;

import jakarta.transaction.Transactional;
import org.novaride.modelentity.models.Booking;
import org.novaride.modelentity.models.BookingStatus;
import org.novaride.modelentity.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Modifying  //annotation responsible to allow update action in db
    @Transactional
    @Query("UPDATE Booking b SET b.bookingStatus=:status, b.driver = :driver  WHERE b.id = :id")
    void updateBookingStatusAndDriverById(@Param("id") Long id, @Param("status") BookingStatus status, @Param("driver") Driver driver);
}
