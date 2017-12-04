package com.youtalkwesign.repository;
import org.springframework.data.repository.CrudRepository;

import com.youtalkwesign.model.Word;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface WordRepository extends CrudRepository<Word, String> {

}