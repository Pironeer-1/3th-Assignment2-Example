package com.pironeer.week2_1.controller;

import com.pironeer.week2_1.dto.request.TopicCreateRequest;
import com.pironeer.week2_1.dto.request.TopicUpdateRequest;
import com.pironeer.week2_1.dto.response.TopicResponse;
import com.pironeer.week2_1.dto.response.TopicSliceResponse;
import com.pironeer.week2_1.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "게시물(Topic)")
@RequestMapping("/api/topic")
public class TopicController {
    private final TopicService topicService;

    @PostMapping
    @Operation(summary = "게시물 작성")
    public ResponseEntity<?> create(@Valid @RequestBody TopicCreateRequest request) {
        topicService.save(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{topicId}")
    @Operation(summary = "게시물 단건 조회")
    public ResponseEntity<TopicResponse> read(@PathVariable("topicId") Long id) {
        TopicResponse response = topicService.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    @Operation(summary = "게시물 전체 조회")
    public ResponseEntity<List<TopicResponse>> readAll() {
        List<TopicResponse> responses = topicService.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/cursor/{cursorId}")
    @Operation(summary = "게시물 페이지네이션 조회")
    public ResponseEntity<TopicSliceResponse> readPage(@PathVariable("cursorId") Long cursorId) {
        // 예제에서는 게시물 전체 조회 API 와 겹치지 않기 위해 cursorId 를 PathVariable 로 받았습니다.
        // RequestParam 으로 받아도 됩니다.
        TopicSliceResponse response = topicService.findByCursorId(cursorId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    @Operation(summary = "게시물 수정")
    public ResponseEntity<TopicResponse> update(@Valid @RequestBody TopicUpdateRequest request) {
        TopicResponse response = topicService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{topicId}")
    @Operation(summary = "게시물 삭제")
    public ResponseEntity<?> remove(@PathVariable("topicId") Long id) {
        topicService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
