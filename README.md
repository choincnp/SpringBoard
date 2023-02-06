# SpringBoard

---
## 요구사항
- 전체 게시글 목록 조회 API
  - [x] 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
  - [x] 작성 날짜 기준 내림차순으로 정렬하기
- 게시글 작성 API
  - [x] 제목, 작성자명, 비밀번호, 작성 내용을 저장하고 저장된 게시글을 Client 로 반환하기 
- 선택한 게시글 조회 API
  - [x] 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기
      (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
- 선택한 게시글 수정 API
  - [x] 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
  - [x] 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기 
- 선택한 게시글 삭제 API
  - [x] 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
  - [x] 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기

## 유스케이스 다이어그램
![대지 1](https://user-images.githubusercontent.com/108874833/216953938-e6200052-fb62-41df-a654-ad90b1f0abec.png)

## API 명세서
| 기능  | Method | url            | request                                                                                                            | response                                                                                                                                                                                       |
|-----|---|----------------|--------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 작성  | POST | /api/post      | {</br>"title" : "title",</br>"content" : "content",</br>"author" : "author",</br>"password" : "password"</br>}     | {</br>"createdAt": "2022-07-25T12:43:01.226062”,</br>"modifiedAt": "2022-07-25T12:43:01.226062”,</br>"id": 1,</br>"title": "title",</br>"content": "content",</br>"author": "author"</br>}     |
| 선택조회 | GET | /api/post/{id} |                                                                                                                    | {</br>"createdAt": "2022-07-25T12:43:01.226062”,</br>"modifiedAt": "2022-07-25T12:43:01.226062”,</br>"id": 1,</br>"title": "title2",</br>"content": "content2",</br>"author": "author2"</br>}  |
| 전체조회 | GET | /api/posts     |                                                                                                                    | {</br>"createdAt": "2022-07-25T12:43:01.226062”,</br>"modifiedAt": "2022-07-25T12:43:01.226062”,</br>"id": 1,</br>"title": "title",</br>"content": "content",</br>"author": "author"</br>}</br>{</br>"createdAt": "2022-07-25T12:43:01.226062”,</br>"modifiedAt": "2022-07-25T12:43:01.226062”,</br>"id": 1,</br>"title": "title2",</br>"content": "content2",</br>"author": "author2"</br>} |
| 수정  | GET | /api/post/{id} | {</br>"title" : "title2",</br>"content" : "content2",</br>"author" : "author2",</br>"password" : "password2"</br>} | {</br>"createdAt": "2022-07-25T12:43:01.226062”,</br>"modifiedAt": "2022-07-25T12:43:01.226062”,</br>"id": 1,</br>"title": "title2",</br>"content": "content2",</br>"author": "author2"</br>} |
| 삭제  | GET | /api/post/{id} | {"password" :"password"}                                                                                           | {"success": true}                                                                                                                                                                              |

## Why
1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)
   * 수정할때의 request: ResponseDto 안에 password를 담아와서 원래 글과 비교
   * 삭제할때의 request: ResponseBody가 필요없으므로 String type으로 받음.
2. 어떤 상황에 어떤 방식의 request를 써야하나요?
   * POST, GET 상황에서 request에서는 ResponseDto 전체가 들어가야 합니다.
   * DELETE 메서드에서는 String type인 password만 lv로 설정하였습니다.
   * GET 메서드는 일부만 입력할경우 전체를 바꾸어버리기 때문에 전체 다 수정해주어야 합니다.
3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
   * RESTful API에 맞게 Method 분배는 잘 했다고 생각하지만, url을 넣는 과정에서는 별로 그렇지 않았다고 생각합니다.
   * viewAll method가 들어가는 글 전체 검색 기능을 getmapping(/api/board)에 넣어야 하고,
   * viewOne method가 들어가는 상세 페이지를 getmapping(/api/board/{id})
   * post method가 들어가는 작성 페이지를 postmapping(/api/board/post)
   * 등이 되어야 한다고 생각했지만, 글을 작성하는 지금 한 화면에 따로 작성 페이지가 없으면 이렇게 하는것도 나쁘지 않겠다는 생각이 들었습니다.
4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
   * 잘 적용했다고 생각합니다. DTO <-> Entity 변환 과정에서 Controller와 Service layer중 어떤 곳에서 변환을 해주어야 하나가 관건이었습니다.
   * 응답과 요청을 관리하는 controller, 핵심 로직은 service, 저장 기능은 repository로의 기능 분화를 잘 했다고 생각합니다.
5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!
   * 유사하게 잘 나왔습니다.