# SpringBoard

---
## 요구사항
- 전체 게시글 목록 조회 API
  - [ ] 제목, 작성자명`(username)`, 작성 내용, 작성 날짜를 조회하기
  - [ ] 작성 날짜 기준 내림차순으로 정렬하기
- 게시글 작성 API
  - [ ] 제목, 작성자명`(username)`, 비밀번호, 작성 내용을 저장하고 저장된 게시글을 Client 로 반환하기
  - [ ] `토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능`
- 선택한 게시글 조회 API
  - [ ] 선택한 게시글의 제목, 작성자명`(username)`, 작성 날짜, 작성 내용을 조회하기
      (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
- 선택한 게시글 수정 API
  - [ ] `토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능`
  - [ ] 제목, ~~작성자명~~, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기 
- 선택한 게시글 삭제 API
  - [ ] ~~삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후~~
  - [ ] `토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 삭제 가능`
  - [ ] 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기
- 회원 가입 API
  - [ ] username, password를 Client에서 전달받기
  - [ ] username은  최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 한다.
  - [ ] password는  최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 한다.
  - [ ] DB에 중복된 username이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환하기
- 로그인 API
  - [ ] username, password를 Client에서 전달받기
  - [ ] DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기
  - [ ] - 로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급하고,
          발급한 토큰을 Header에 추가하고 성공했다는 메시지, 상태코드 와 함께 Client에 반환하기

## 유스케이스 다이어그램
![제목 없는 다이어그램 drawio](https://user-images.githubusercontent.com/108874833/220567930-4019ba1e-eb16-43c7-9704-2a4041221b70.png)

## Conceptual Data Modeling
![논리](https://user-images.githubusercontent.com/108874833/219086355-29a9cd66-3356-42da-b39c-2b392016ff7c.jpg)

## ERD
![erd](https://user-images.githubusercontent.com/108874833/220891156-4a4f672f-aceb-418a-94c3-c139801b5828.png)

## API 명세서
| 기능  | Method | url            | request header                                                                                                     | request body                                                                                                                                                                                                                                                                                                                                                                                 |
|-----|---|----------------|--------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|회원 가입| POST | /api/auth/signup |                                                                                                                    | {</br>"username": "bin1234",</br>"password": "Bin@12345"</br>}                                                                                                                                                                                                                                                                                                                               |
|로그인| POST | /api/auth/login |                                                                                                                    | {</br>"username": "bin1234",</br>"password": "Bin@12345"</br>}                                                                                                                                                                                                                                                                                                                               |
| 작성  | POST | /api/post      | {</br>"title" : "title",</br>"content" : "content",</br>"author" : "author",</br>"password" : "password"</br>}     | {</br>"createdAt": "2022-07-25T12:43:01.226062”,</br>"modifiedAt": "2022-07-25T12:43:01.226062”,</br>"id": 1,</br>"title": "title",</br>"content": "content",</br>"author": "author"</br>}                                                                                                                                                                                                   |
| 선택조회 | GET | /api/post/{id} |                                                                                                                    | {</br>"createdAt": "2022-07-25T12:43:01.226062”,</br>"modifiedAt": "2022-07-25T12:43:01.226062”,</br>"id": 1,</br>"title": "title2",</br>"content": "content2",</br>"author": "author2"</br>}                                                                                                                                                                                                |
| 전체조회 | GET | /api/posts     |                                                                                                                    | {</br>"createdAt": "2022-07-25T12:43:01.226062”,</br>"modifiedAt": "2022-07-25T12:43:01.226062”,</br>"id": 1,</br>"title": "title",</br>"content": "content",</br>"author": "author"</br>}</br>{</br>"createdAt": "2022-07-25T12:43:01.226062”,</br>"modifiedAt": "2022-07-25T12:43:01.226062”,</br>"id": 1,</br>"title": "title2",</br>"content": "content2",</br>"author": "author2"</br>} |
| 수정  | GET | /api/post/{id} | {</br>"title" : "title2",</br>"content" : "content2",</br>"author" : "author2",</br>"password" : "password2"</br>} | {</br>"createdAt": "2022-07-25T12:43:01.226062”,</br>"modifiedAt": "2022-07-25T12:43:01.226062”,</br>"id": 1,</br>"title": "title2",</br>"content": "content2",</br>"author": "author2"</br>}                                                                                                                                                                                                |
| 삭제  | GET | /api/post/{id} | {"password" :"password"}                                                                                           | {"success": true}                                                                                                                                                                                                                                                                                                                                                                            |
[API 명세서](https://www.notion.so/edc75b030d4b411b8134c0f58e1898ba)


## Why
1. 처음 설계한 API 명세서에 변경사항이 있었나요? 변경 되었다면 어떤 점 때문 일까요? 첫 설계의 중요성에 대해 작성해 주세요!
2. ERD를 먼저 설계한 후 Entity를 개발했을 때 어떤 점이 도움이 되셨나요?
3. JWT를 사용하여 인증/인가를 구현 했을 때의 장점은 무엇일까요?
4. 반대로 JWT를 사용한 인증/인가의 한계점은 무엇일까요?
5. 만약 댓글 기능이 있는 블로그에서 댓글이 달려있는 게시글을 삭제하려고 한다면 무슨 문제가 발생할까요? Database 테이블 관점에서 해결방법이 무엇일까요?
6. IoC / DI 에 대해 간략하게 설명해 주세요!
