# 댓글, 대댓글 구현하기

[세션 자료 및 문제 참고](https://even-channel-ff2.notion.site/SpringBoot-463dd998b6984ed6a9af61f3c104a5be?pvs=4)

## ERD

자기자신을 참조하게 했을 때, 복잡도가 높아질 것 같지만 오히려 효율적이어집니다.
1. 댓글 테이블과 대댓글 테이블이 분리되어 있다면, 게시물의 댓글을 조회할 때 두 개의 테이블을 조회해야 합니다.
2. 게다가 두 테이블의 조회 결과를 시간 순서대로 정렬해야 합니다.
3. 자기자신을 참조하게 되면, 대댓글의 깊이를 무한히 확장할 수 있으므로 확장성 있는 설계가 됩니다.

## 특별히 추가된 점

- 게시물 댓글 조회 시, 대댓글은 댓글의 하위 리스트 객체로 포함되어 응답합니다.
- 깊이 우선 탐색을 조금 적용하여 구현해 봤는데, 더 좋은 방법이 있을지 토론하고 알려주셔도 좋아요!

    <details>
    <summary>펼치기</summary>
    
    ```json
    [
      {
        "id": 1,
        "topicId": 1,
        "content": "댓글입니다",
        "createdAt": "2024-09-20T23:54:27.767183",
        "updatedAt": "2024-09-20T23:54:27.767183",
        "childComments": [
          {
            "id": 3,
            "topicId": 1,
            "parentCommentId": 1,
            "content": "1번 댓글의 대댓글입니다",
            "createdAt": "2024-09-20T23:54:46.674945",
            "updatedAt": "2024-09-20T23:54:46.674945",
            "childComments": [
              {
                "id": 6,
                "topicId": 1,
                "parentCommentId": 3,
                "content": "1번 댓글의 1번 대댓글의 대댓글입니다",
                "createdAt": "2024-09-20T23:55:31.926002",
                "updatedAt": "2024-09-20T23:55:31.926002"
              }
            ]
          },
          {
            "id": 4,
            "topicId": 1,
            "parentCommentId": 1,
            "content": "1번 댓글의 두 번째 대댓글입니다",
            "createdAt": "2024-09-20T23:54:51.98049",
            "updatedAt": "2024-09-20T23:54:51.98049"
          }
        ]
      },
      {
        "id": 2,
        "topicId": 1,
        "content": "두 번째 댓글입니다",
        "createdAt": "2024-09-20T23:54:38.09307",
        "updatedAt": "2024-09-20T23:54:38.09307",
        "childComments": [
          {
            "id": 5,
            "topicId": 1,
            "parentCommentId": 2,
            "content": "2번 댓글의 대댓글입니다",
            "createdAt": "2024-09-20T23:55:00.709714",
            "updatedAt": "2024-09-20T23:55:00.709714"
          }
        ]
      },
      {
        "id": 7,
        "topicId": 1,
        "content": "세 번째 댓글입니다",
        "createdAt": "2024-09-20T23:55:48.599364",
        "updatedAt": "2024-09-20T23:55:48.599364"
      }
    ]
    ```
    </details>

- `@JsonInclude(JsonInclude.Include.NON_NULL)` 를 응답 객체에 추가함으로써, null 인 필드는 제외하고 응답할 수 있습니다.
- 게시물의 경우 양이 많아질 때를 대비하여, 페이지네이션을 위한 API 를 추가하였습니다.
- 페이지네이션 코드에서는 cursorId 를 기준으로 데이터를 조회하게 됩니다.
- cursorId는 조회의 기준점이 되는 topicId 라고 이해하시면 됩니다.

## Swagger

<details>
<summary>펼치기</summary>

</details>