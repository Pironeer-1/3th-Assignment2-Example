package com.pironeer.week2_1.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record TopicSliceResponse(
        @NotNull
        @Schema(description = "게시물 목록")
        List<TopicResponse> topics,
        @NotNull
        @Schema(description = "페이지 크기", example = "10")
        Integer pageSize,
        @NotNull
        @Schema(description = "커서 ID", example = "1")
        Long cursorId,
        @NotNull
        @Schema(description = "다음 페이지 존재 여부", example = "true")
        Boolean hasNext) {
    public static TopicSliceResponse of(List<TopicResponse> topics, Long cursorId, Boolean hasNext) {
        return TopicSliceResponse.builder()
                .topics(topics)
                .pageSize(topics.size())
                .cursorId(cursorId)
                .hasNext(hasNext)
                .build();
    }
}
