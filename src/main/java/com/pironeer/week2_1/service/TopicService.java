package com.pironeer.week2_1.service;

import com.pironeer.week2_1.dto.request.TopicCreateRequest;
import com.pironeer.week2_1.dto.request.TopicUpdateRequest;
import com.pironeer.week2_1.dto.response.TopicResponse;
import com.pironeer.week2_1.dto.response.TopicSliceResponse;
import com.pironeer.week2_1.mapper.TopicMapper;
import com.pironeer.week2_1.repository.TopicRepository;
import com.pironeer.week2_1.repository.domain.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;

    public void save(TopicCreateRequest request) {
        topicRepository.save(TopicMapper.from(request));
    }

    public TopicResponse findById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TOPIC NOT FOUND"));
        return TopicResponse.of(topic);
    }

    public List<TopicResponse> findAll() {
        List<Topic> topics = topicRepository.findAll();
        return topics.stream().map(TopicResponse::of).toList();
    }

    public TopicSliceResponse findByCursorId(Long cursorId) {
        // 11개를 읽어들인 뒤 1개는 제외하고 반환합니다.
        // 11개보다 적게 읽은 경우 더이상 게시물이 존재하지 않습니다.
        List<Topic> topics = topicRepository.findByCursorId(cursorId);
        boolean hasNext = false;
        if (topics.size() > 10) {
            topics = new ArrayList<>(topics);
            cursorId = topics.get(topics.size() - 1).getId();
            topics.remove(topics.size() - 1);
            hasNext = true;
        }
        List<TopicResponse> topicResponses = topics.stream().map(TopicResponse::of).toList();
        return TopicSliceResponse.of(topicResponses, cursorId, hasNext);
    }

    public TopicResponse update(TopicUpdateRequest request) {
        Topic topic = topicRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("TOPIC NOT FOUND"));
        topicRepository.save(topic.update(request));
        return TopicResponse.of(topic);
    }

    public void deleteById(Long id) {
        topicRepository.deleteById(id);
    }
}
