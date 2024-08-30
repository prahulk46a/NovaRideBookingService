package org.novaride.novaridebookingservice.repository;

import org.novaride.modelentity.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
