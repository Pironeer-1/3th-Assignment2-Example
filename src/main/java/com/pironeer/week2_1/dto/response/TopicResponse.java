package com.pironeer.week2_1.dto.response;

import com.pironeer.week2_1.repository.domain.Topic;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TopicResponse(
        @NotNull
        @Schema(description = "게시물 ID", example = "1")
        Long id,
        @NotBlank
        @Schema(description = "게시물 제목", example = "제목입니다")
        String title,
        @Schema(description = "게시물 내용", example = "내용입니다")
        String content,
        @NotNull
        @Schema(description = "게시물 생성 시간", example = "2024-10-10 10:10:00")
        LocalDateTime createdAt,
        @NotNull
        @Schema(description = "게시물 수정 시간", example = "2024-10-13 10:10:00")
        LocalDateTime updatedAt) {
    public static TopicResponse of(Topic topic) {
        return TopicResponse.builder()
                .id(topic.getId())
                .title(topic.getTitle())
                .content(topic.getContent())
                .createdAt(topic.getCreatedAt())
                .updatedAt(topic.getUpdatedAt())
                .build();
    }
}
