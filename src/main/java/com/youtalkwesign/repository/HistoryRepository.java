package com.youtalkwesign.repository;

import org.springframework.data.repository.CrudRepository;

import com.youtalkwesign.model.History;
import com.youtalkwesign.model.HistoryKey;

public interface HistoryRepository extends CrudRepository<History, HistoryKey> {

}
