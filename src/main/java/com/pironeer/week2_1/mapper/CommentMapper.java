package com.pironeer.week2_1.mapper;

import com.pironeer.week2_1.dto.request.CommentCreateRequest;
import com.pironeer.week2_1.repository.domain.Comment;
import com.pironeer.week2_1.repository.domain.Topic;

import java.time.LocalDateTime;

public class CommentMapper {
    public static Comment from(CommentCreateRequest request, Topic topic, Comment parentComment) {
        LocalDateTime now = LocalDateTime.now();
        return Comment.builder()
                .topic(topic)
                .parentComment(parentComment)
                .content(request.content())
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
