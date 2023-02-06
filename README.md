# SpringBoard

---
## 요구사항
- 전체 게시글 목록 조회 API
  - [ ] 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
  - [ ] 작성 날짜 기준 내림차순으로 정렬하기
- 게시글 작성 API
  - [ ] 제목, 작성자명, 비밀번호, 작성 내용을 저장하고 저장된 게시글을 Client 로 반환하기 
- 선택한 게시글 조회 API
  - [ ] 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기
      (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
- 선택한 게시글 수정 API
  - [ ] 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
  - [ ] 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기 
- 선택한 게시글 삭제 API
  - [ ] 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
  - [ ] 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기

## 유스케이스 다이어그램
![대지 1](https://user-images.githubusercontent.com/108874833/216953938-e6200052-fb62-41df-a654-ad90b1f0abec.png)

## API 명세서
| 기능  | Method | url       | request                                                                                                            | response                                                                                                                                                                                       |
|-----|---|-----------|--------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 작성  | POST | /api/post | {</br>"title" : "title",</br>"content" : "content",</br>"author" : "author",</br>"password" : "password"</br>}     | {</br>"createdAt": "2022-07-25T12:43:01.226062”,</br>"modifiedAt": "2022-07-25T12:43:01.226062”,</br>"id": 1,</br>"title": "title",</br>"content": "content",</br>"author": "author"</br>}     |
| 선택조회 | GET | /api/post/{id} |                                                                                                                    | {</br>"createdAt": "2022-07-25T12:43:01.226062”,</br>"modifiedAt": "2022-07-25T12:43:01.226062”,</br>"id": 1,</br>"title": "title2",</br>"content": "content2",</br>"author": "author2"</br>}  |
| 전체조회 | GET | /api/post |                                                                                                                    | {</br>"createdAt": "2022-07-25T12:43:01.226062”,</br>"modifiedAt": "2022-07-25T12:43:01.226062”,</br>"id": 1,</br>"title": "title",</br>"content": "content",</br>"author": "author"</br>}</br>{</br>"createdAt": "2022-07-25T12:43:01.226062”,</br>"modifiedAt": "2022-07-25T12:43:01.226062”,</br>"id": 1,</br>"title": "title2",</br>"content": "content2",</br>"author": "author2"</br>} |
| 수정  | GET | /api/post/{id} | {</br>"title" : "title2",</br>"content" : "content2",</br>"author" : "author2",</br>"password" : "password2"</br>} | {</br>"createdAt": "2022-07-25T12:43:01.226062”,</br>"modifiedAt": "2022-07-25T12:43:01.226062”,</br>"id": 1,</br>"title": "title2",</br>"content": "content2",</br>"author": "author2"</br>} |
| 삭제  | GET | /api/post/{id} | {"password" :"password"}                                                                                           | {"success": true}                                                                                                                                                                              |

## Why
1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)
2. 어떤 상황에 어떤 방식의 request를 써야하나요?
3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!