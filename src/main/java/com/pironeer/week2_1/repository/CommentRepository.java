package com.pironeer.week2_1.repository;

import com.pironeer.week2_1.repository.domain.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CommentRepository {
    private final AtomicLong commentIdxGenerator = new AtomicLong(0);
    private final Map<Long, Comment> commentMap = new HashMap<>();

    public void save(Comment comment) {
        if(comment.getId() == null) {
            Long id = commentIdxGenerator.incrementAndGet();
            comment.setId(id);
            commentMap.put(id, comment);
        } else {
            commentMap.replace(comment.getId(), comment);
        }
    }

    public Optional<Comment> findById(Long id) {
        Assert.notNull(id, "ID MUST NOT BE NULL");
        return Optional.ofNullable(commentMap.get(id));
    }

    public List<Comment> findParentByTopicId(Long topicId) {
        Assert.notNull(topicId, "TOPIC ID MUST NOT BE NULL");
        return commentMap
                .values()
                .stream()
                .filter(item -> item.getTopic().getId().equals(topicId) && item.getParentComment() == null)
                .toList();
    }

    public void deleteById(Long id) {
        Assert.notNull(id, "ID MUST NOT BE NULL");
        commentMap.remove(id);
    }

    public List<Comment> findByParentId(Long parentId) {
        Assert.notNull(parentId, "ID MUST NOT BE NULL");
        List<Comment> result = commentMap
                .values()
                .stream()
                .filter(item -> item.getParentComment() != null
                        && item.getParentComment().getId().equals(parentId))
                .toList();
        return result.isEmpty() ? null : result;
    }
}
