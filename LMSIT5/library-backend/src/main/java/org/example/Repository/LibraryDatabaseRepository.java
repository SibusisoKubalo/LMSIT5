package org.example.Repository;

import org.example.Domain.LibraryDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryDatabaseRepository extends JpaRepository<LibraryDatabase, Integer> {

}