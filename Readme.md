


- 레트로핏 2.x 에서 응답을 String으로 받아오기
    - 참고 : https://onlyfor-me-blog.tistory.com/179


### 2022년 11월 27일 중간 완성
#### 완성된 기능
- 웹에서 새로운 userid 를 받아와서 SQLite에 저장. (CREATE)
- 웹에서 keyword List를 받아옴 (READ)
- 웹 DB에 키워드를 추가 (CREATE)
- 웹 DB에 키워드 삭제 (DELETE)
- 내부 SQLite 테이블 초기화
  <img width="200" alt = "작동화면" src="./src/22_11_27첫번째시연동작.gif>

#### 완성되지 못한 기능
- Job 스케줄러로 웹에 키워드 알람 Request 요청 보내기
- 위의 요청을 받고 응답을 알람을 띄어줌
