# 사전과제

## Requirements

- [JDK 1.8+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Running the application locally

```shell
Build
./gradlew bootWar

Run
./gradlew bootRun -Dencryption.encKey=member-mushinsa0 -Dencryption.encIV=member-mushinsa0
```

## Usage
### Web Pages
```shell
회원 조회
https://localhost:8080/member/home
회원 등록
https://localhost:8080/member/register
```
### API
```shell
Swagger UI
https://localhost:8080/swagger-ui.html
```