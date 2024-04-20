# 코드로 배우는 리액트 실습 책

## 프로젝트 구조

```
/backend - Spring Boot 3.2.4
/docker - Mariadb 10.6
/frontend - React 18
```

# 목표 기간

```
 24.03.25 Start
 24.04.13 까지 1회독
```

### React

= Page : 라우팅 경로
= Component : 실제 작업 처리

| Router

URL을 지정해준다.

- 사용하지 않은 모든 페이지를 렌더링 해놓고 사용할건가? (X) -> lazy / Suspense 로 해결
- 공통 Layout에서 특정 부분만 갈아 끼우는 방법 -> Outlet
  - children이 많아서 Router가 지저분해진다 -> children 내의 Router 정보만 외부로 빼낸다
  - 특정 RUL은 기본 경로로 리다이렉트 시키고 싶다 -> Navigate
    - Link, Navigate [정적 이동] useNavigate[동적 이동]
- 쿼리스트링의 상태를 다른페이지로 갔다가 빠져나올때도 동일하게 유지

URL로, 정보를 보내는 이유
-> 사용자들이 링크를 이용하여 동일한 페이지를 보도록 하기 위함
===

| Axios

- use Effect
  [ 컴포넌트 실행 중. 한번만 실행 / 컴포넌트 상태 중 특정 상태 변경 시 비동기 처리]

| <b>Side Effect</b>

함수형 프로그래밍으로 부터 나온 용어

`외부 세계와의 상호작용이나 가변 데이터의 변경 등을 포함하는 코드` 라고 한다

예시를 들면

1. DataBase 변경
2. 외부 API 호출
3. 전역 변수 / 클래스 변수 변경
4. 콘솔 로깅

그래서 side Effect와 useEffect Hook의 관계는?

```
// a변수를 선언하고, setA를 통해 값을 변경시킨다.
const [a, setA]= useState()


// a변수가 변하면 기능을 동작시킨다.
useEffect(() => {
  // 값이 변경되면 실행될 작업
  return () => {
  // 작업끝[다음 렌더링 시작 전, unMount 시] 처리 claen up
},[감시할 변수]})

```

---

### Spring

- JPA
- pageable & pageRequest 객체를 통한 페이징 처리
- Object Mapping (DTO - Entity 간 변환)
- RestControllerAdvice (Custom 예외처리)
- Data Formatter, CORS
