package com.fangyu3.webquizengine.repositories;


import com.fangyu3.webquizengine.domain.UserCompletion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserCompletionRepository extends PagingAndSortingRepository<UserCompletion,Integer> {

    @Query("SELECT c FROM UserCompletion c WHERE c.user.id = :userId")
    Page<UserCompletion> findAllByUser(Integer userId, Pageable paging);
}
