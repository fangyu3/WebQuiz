package com.fangyu3.webquiz.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fangyu3.webquiz.entity.Completion;
import com.fangyu3.webquiz.entity.User;

public interface CompletionRepository extends JpaRepository<Completion, Integer> {
	
    @Query("SELECT c FROM Completion c where c.user.email = :userEmail order by c.completedAt desc")
	public Page<Completion> findAllByUserEmail(String userEmail,Pageable paging);
}
