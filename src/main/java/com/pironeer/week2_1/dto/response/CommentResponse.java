package com.pironeer.week2_1.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pironeer.week2_1.repository.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommentResponse(
        @NotNull
        @Schema(description = "댓글 ID", example = "1")
        Long id,
        @NotNull
        @Schema(description = "게시물 ID", example = "1")
        Long topicId,
        @Schema(description = "부모 댓글 ID", example = "null")
        Long parentCommentId,
        @NotBlank
        @Schema(description = "댓글 내용", example = "댓글입니다")
        String content,
        @NotNull
        @Schema(description = "댓글 작성 시간", example = "2024-10-10 10:10:00")
        LocalDateTime createdAt,
        @NotNull
        @Schema(description = "댓글 수정 시간", example = "2024-10-13 10:10:00")
        LocalDateTime updatedAt,
        @Schema(description = "대댓글 목록")
        List<CommentResponse> childComments) {
    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .topicId(comment.getTopic().getId())
                .parentCommentId(getParentCommentId(comment))
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    public static CommentResponse of(Comment comment, List<CommentResponse> childComments) {
        return CommentResponse.builder()
                .id(comment.getId())
                .topicId(comment.getTopic().getId())
                .parentCommentId(getParentCommentId(comment))
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .childComments(childComments)
                .build();
    }

    private static Long getParentCommentId(Comment comment) {
        return comment.getParentComment() != null ? comment.getParentComment().getId() : null;
    }
}
