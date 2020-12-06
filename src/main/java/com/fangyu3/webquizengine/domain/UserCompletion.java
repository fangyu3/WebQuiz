package com.fangyu3.webquizengine.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quizId;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp completedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
