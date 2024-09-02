package com.example.task_tracker.entity;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "Users")
public class User {

    @Id
    private String id;

    private String username;

    private String email;

}
