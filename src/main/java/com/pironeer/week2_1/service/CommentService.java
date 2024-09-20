package com.pironeer.week2_1.service;

import com.pironeer.week2_1.dto.request.CommentCreateRequest;
import com.pironeer.week2_1.dto.request.CommentUpdateRequest;
import com.pironeer.week2_1.dto.response.CommentResponse;
import com.pironeer.week2_1.mapper.CommentMapper;
import com.pironeer.week2_1.repository.CommentRepository;
import com.pironeer.week2_1.repository.TopicRepository;
import com.pironeer.week2_1.repository.domain.Comment;
import com.pironeer.week2_1.repository.domain.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final TopicRepository topicRepository;
    private final CommentRepository commentRepository;

    public void save(CommentCreateRequest request) {
        Topic topic = topicRepository.findById(request.topicId())
                .orElseThrow(() -> new RuntimeException("TOPIC NOT FOUND"));
        Comment parentComment = null;
        if (request.parentCommentId() != null) {
            parentComment = commentRepository.findById(request.parentCommentId())
                    .orElseThrow(() -> new RuntimeException("COMMENT NOT FOUND"));
        }
        commentRepository.save(CommentMapper.from(request, topic, parentComment));
    }

    public CommentResponse findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("COMMENT NOT FOUND"));
        return CommentResponse.of(comment);
    }

    public List<CommentResponse> findByTopicId(Long topicId) {
        // 부모 댓글 객체 조회
        List<Comment> comments = commentRepository.findParentByTopicId(topicId);
        List<CommentResponse> result = new CopyOnWriteArrayList<>();
        // 자식 댓글 객체 리스트 조회
        for (Comment comment : comments) {
            List<CommentResponse> child = findChild(comment.getId());
            CommentResponse commentResponse = CommentResponse.of(comment, child);
            result.add(commentResponse);
        }
        return result;
    }

    public CommentResponse update(CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("COMMENT NOT FOUND"));
        commentRepository.save(comment.update(request));
        return CommentResponse.of(comment);
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    private List<CommentResponse> findChild(Long parentId) {
        List<CommentResponse> result = new CopyOnWriteArrayList<>();
        List<Comment> comments = commentRepository.findByParentId(parentId);
        if (comments == null) {
            return null;
        }
        for (Comment comment : comments) {
            List<CommentResponse> child = findChild(comment.getId());
            result.add(CommentResponse.of(comment, child));
        }
        return result;
    }
}
