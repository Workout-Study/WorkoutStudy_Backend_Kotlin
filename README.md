# WorkoutStudy_Backend_Fit_Group_Service
## Intro

[Fit-Mate App Aplication](https://github.com/Workout-Study/Project-Introduction)

- 이 서비스는 위 Fit mate 프로젝트의 운동 그룹 관리, 운동 멤버 관리 역할을 담당하는 서비스 입니다.


## Period

Version 1.0.0 : 2024.03.08 ~ 2024.04.20 <br>
Version 2.0.0 : 2024.05.01 ~

## Architecture

## Used Tools

### Application

 <a href="https://github.com/JetBrains/kotlin"><img src="https://img.shields.io/badge/Kotlin-7F42FF?style=for-the-badge&logo=Kotlin&logoColor=white"/></a>
 <a href="https://openjdk.org/projects/jdk/17/"><img src="https://img.shields.io/badge/JDK 17-007396?style=for-the-badge&logo=Java&logoColor=white"/></a>
 <a href="https://spring.io/projects/spring-cloud"><img src="https://img.shields.io/badge/Spring Cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/></a><br>
 <a href="https://gradle.org/"><img src="https://img.shields.io/badge/Gradle 8.6-02303A?style=for-the-badge&logo=Gradle&logoColor=white"></a>
 <a href="https://spring.io/projects/spring-boot/"><img src="https://img.shields.io/badge/Spring Boot 3.2.3-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"></a>
 <a href="https://spring.io/projects/spring-data-jpa"><img src="https://img.shields.io/badge/Spring Data Jpa-6DB33F?style=for-the-badge&logo=Spring Data Jpa&logoColor=white"></a>
 <a href="http://querydsl.com/"><img src="https://img.shields.io/badge/Query Dsl 5.0-6DB33F?style=for-the-badge&logo=Query DSL&logoColor=white"></a>

### Database

 <a href="https://www.mysql.com/"><img src="https://img.shields.io/badge/MySql 8-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"></a>
 <a href="https://redis.io/"><img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white"></a>

### Infra

 <a href="https://kafka.apache.org/"><img src="https://img.shields.io/badge/Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white"></a>
 <a href="https://docs.docker.com/get-docker/"><img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white"></a>
 <a href="https://docs.docker.com/compose/install/"><img src="https://img.shields.io/badge/Docker_Compose-2496ED?style=for-the-badge&logo=Docker&logoColor=white"></a>

### Test

 <a href="https://junit.org/junit5/"><img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white"></a>
 <a href="https://site.mockito.org/"><img src="https://img.shields.io/badge/Mockito-25A162?style=for-the-badge&logo=Mockito&logoColor=white"></a>

### Docs

<a href="https://asciidoc.org/"><img src="https://img.shields.io/badge/asciidoc-6DB33F?style=for-the-badge&logo=asciidoctor&logoColor=white"></a>
 <a href="https://spring.io/projects/spring-restdocs"><img src="https://img.shields.io/badge/Spring Rest Docs-6DB33F?style=for-the-badge&logo=Read The Docs&logoColor=white"></a>

### Cloud

<a href="https://aws.amazon.com/ko/?nc2=h_lg"><img src="https://img.shields.io/badge/EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white"></a>

### Communication

<a href="https://github.com/"><img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"></a>
 <a href="https://slack.com/intl/ko-kr/"><img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"></a>
 <a href="https://www.notion.so/"><img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"></a>
 <a href="https://discord.com/"><img src="https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white"></a>

## Git Strategy

- Git flow

## API document

<!DOCTYPE html>
<html lang="en">
<body class="book toc2 toc-left">
<div id="header">
<h1>Fit-Group Service API Document</h1>
<div class="details">
<span id="revnumber">Version 1.0.0</span>
</div>
<div id="toc" class="toc2">
<ul class="sectlevel0">
<li><a href="#common">개발 환경 정보</a>
<ul class="sectlevel1">
<li><a href="#fit-group-Service-API">1. fit-group</a>
<ul class="sectlevel2">
<li><a href="#_register_fit_group">Register Fit Group</a></li>
<li><a href="#_get_fit_group_detail">Get Fit group detail</a></li>
<li><a href="#_update_fit_group">Update Fit Group</a></li>
<li><a href="#_delete_fit_group">Delete Fit Group</a></li>
<li><a href="#_fit_group_filter">Fit Group Filter</a></li>
<li><a href="#_fit_group_filter_by_user_id">Fit Group Filter By User id</a></li>
</ul>
</li>
<li><a href="#_2_bank_code">2. bank-code</a>
<ul class="sectlevel2">
<li><a href="#_get_bank_code_list">Get Bank-code List</a></li>
</ul>
</li>
<li><a href="#_3_fit_mate">3. fit-mate</a>
<ul class="sectlevel2">
<li><a href="#_register_fit_mate">Register Fit Mate</a></li>
<li><a href="#_get_fit_mate_list">Get Fit Mate List</a></li>
<li><a href="#_delete_fit_mate">Delete Fit Mate</a></li>
</ul>
</li>
<li><a href="#_4_fit_management">4. fit-management</a>
<ul class="sectlevel2">
<li><a href="#_kick_fit_mate">Kick Fit Mate</a></li>
</ul>
</li>
</ul>
</li>
</ul>
</div>
</div>
<div id="content">
<h1 id="common" class="sect0"><a class="link" href="#common">개발 환경 정보</a></h1>
<div class="openblock partintro">
<div class="content">
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">환경</th>
<th class="tableblock halign-left valign-top">url</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">테스트</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">http://127.0.0.1</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">운영서버</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><del>http://52.78.208.77</del></p></td>
</tr>
</tbody>
</table>
</div>
</div>
<div class="sect1">
<h2 id="fit-group-Service-API"><a class="link" href="#fit-group-Service-API">1. fit-group</a></h2>
<div class="sectionbody">
<div class="sect2">
<h3 id="_register_fit_group"><a class="link" href="#_register_fit_group">Register Fit Group</a></h3>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">POST /fit-group-service/groups HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 492
Host: localhost:8080

{"requestUserId":"testUserId","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디","penaltyAmount":5000,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-5367420","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼","cycle":null,"frequency":7,"maxFitMate":20,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>requestUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Fit group을 등록하는 User id ( Fit Leader로 등록 )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupName</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Fit group 이름</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>penaltyAmount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">운동 미인증 패널티 금액</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>penaltyAccountBankCode</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">운동 미인증 패널티 입금 은행</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>penaltyAccountNumber</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">운동 미인증 패널티 입금 계좌</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>category</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">운동 category ( 1: 등산, 2: 생활 체육, 3: 웨이트, 4: 수영, 5: 축구, 6: 농구, 7: 야구, 8: 바이킹, 9: 클라이밍, 10: 기타 )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>introduction</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">스터디 설명</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>cycle</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Null</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">운동 인증 주기 ( null시 기본값 일주일 - 1: 일주일, 2: 한달, 3: 일년 )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>frequency</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">주기별 운동 인증 필요 횟수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>maxFitMate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 최대 fit mate 수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>multiMediaEndPoints</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">멀티 미디어 end point list ( 주어진 index 순으로 return )</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"requestUserId":"testUserId","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디","penaltyAmount":5000,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-5367420","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼","cycle":null,"frequency":7,"maxFitMate":20,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/fit-group-service/groups' -i -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json' \
    -d '{"requestUserId":"testUserId","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디","penaltyAmount":5000,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-5367420","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼","cycle":null,"frequency":7,"maxFitMate":20,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]}'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>isRegisterSuccess</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">등록 성공 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"isRegisterSuccess":true}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 201 Created
Content-Length: 26
Content-Type: application/json

{"isRegisterSuccess":true}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_get_fit_group_detail"><a class="link" href="#_get_fit_group_detail">Get Fit group detail</a></h3>
<div class="paragraph">
<p>path-parameters</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<caption class="title">Table 1. /fit-group-service/groups/{fit-group-id}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fit-group-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회할 Fit group id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">GET /fit-group-service/groups/1 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Host: localhost:8080</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/fit-group-service/groups/1' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Fit group id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitLeaderUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Fit Leader User id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupName</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Fit group 이름</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>penaltyAmount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">운동 미인증 패널티 금액</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>penaltyAccountBankCode</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">운동 미인증 패널티 입금 은행</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>penaltyAccountNumber</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">운동 미인증 패널티 입금 계좌</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>category</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">운동 category ( 1: 등산, 2: 생활 체육, 3: 웨이트, 4: 수영, 5: 축구, 6: 농구, 7: 야구, 8: 바이킹, 9: 클라이밍, 10: 기타 )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>introduction</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">스터디 설명</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>cycle</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">운동 인증 주기 ( 1: 일주일, 2: 한달, 3: 일년 )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>frequency</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">주기별 운동 인증 필요 횟수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">등록 일시</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>presentFitMateCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">현재 fit mate 수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>maxFitMate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 최대 fit mate 수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>multiMediaEndPoints</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">멀티 미디어 end point list</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>state</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 상태 (false: 정상, true: 삭제)</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"presentFitMateCount":7,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":1,"fitLeaderUserId":"testUserId","fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒","penaltyAmount":5000,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-5367420","category":1,"introduction":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰덉   슫 룞 빐 빞 븳 떎怨   깮媛곹빀 땲 떎  떦 떊 룄  뿬李쎌씠  맗 떆 떎 洹쇱쑁  쑕 떇 뵲 쐢  깮媛곷룄 留덉떗 눥","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:02.819554300Z","maxFitMate":20,"state":false}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 589
Content-Type: application/json

{"presentFitMateCount":7,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":1,"fitLeaderUserId":"testUserId","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디","penaltyAmount":5000,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-5367420","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:02.819554300Z","maxFitMate":20,"state":false}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_update_fit_group"><a class="link" href="#_update_fit_group">Update Fit Group</a></h3>
<div class="paragraph">
<p>path-parameters</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<caption class="title">Table 2. /fit-group-service/groups/{fit-group-id}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fit-group-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">수정할 Fit group id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">PUT /fit-group-service/groups/1 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 492
Host: localhost:8080

{"requestUserId":"testUserId","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디","penaltyAmount":5000,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-5367420","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼","cycle":null,"frequency":7,"maxFitMate":20,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>requestUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">수정을 요청한 User id ( Fit Leader여야함 )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupName</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">수정할 Fit group 이름</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>penaltyAmount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">수정할 운동 미인증 패널티 금액</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>penaltyAccountBankCode</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">수정할 운동 미인증 패널티 입금 은행</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>penaltyAccountNumber</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">수정할  운동 미인증 패널티 입금 계좌</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>category</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">수정할 운동 category ( 1: 등산, 2: 생활 체육, 3: 웨이트, 4: 수영, 5: 축구, 6: 농구, 7: 야구, 8: 바이킹, 9: 클라이밍, 10: 기타 )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>introduction</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">수정할 스터디 설명</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>cycle</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Null</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">수정할 운동 인증 주기 ( null시 기본값 일주일 - 1: 일주일, 2: 한달, 3: 일년 )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>frequency</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">수정할 주기별 운동 인증 필요 횟수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>maxFitMate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 최대 fit mate 수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>multiMediaEndPoints</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">멀티 미디어 end point list ( 기존 기등록 멀티 미디어 list 미포함시 삭제 )</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"requestUserId":"testUserId","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디","penaltyAmount":5000,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-5367420","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼","cycle":null,"frequency":7,"maxFitMate":20,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/fit-group-service/groups/1' -i -X PUT \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json' \
    -d '{"requestUserId":"testUserId","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디","penaltyAmount":5000,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-5367420","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼","cycle":null,"frequency":7,"maxFitMate":20,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]}'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>isUpdateSuccess</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">수정 성공 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"isUpdateSuccess":true}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 24
Content-Type: application/json

{"isUpdateSuccess":true}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_delete_fit_group"><a class="link" href="#_delete_fit_group">Delete Fit Group</a></h3>
<div class="paragraph">
<p>path-parameters</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<caption class="title">Table 3. /fit-group-service/groups/{fit-group-id}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fit-group-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">삭제할 Fit group id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">DELETE /fit-group-service/groups/1 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 30
Host: localhost:8080

{"requestUserId":"testUserId"}</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>requestUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">삭제를 요청한 User id ( Fit Leader여야함 )</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"requestUserId":"testUserId"}</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/fit-group-service/groups/1' -i -X DELETE \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json' \
    -d '{"requestUserId":"testUserId"}'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>isDeleteSuccess</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">삭제 성공 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"isDeleteSuccess":true}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 24
Content-Type: application/json

{"isDeleteSuccess":true}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_fit_group_filter"><a class="link" href="#_fit_group_filter">Fit Group Filter</a></h3>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">GET /fit-group-service/groups/filters?withMaxGroup=false&amp;category=1&amp;pageNumber=1&amp;pageSize=5 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Host: localhost:8080</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/fit-group-service/groups/filters?withMaxGroup=false&amp;category=1&amp;pageNumber=1&amp;pageSize=5' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[]</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group List</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].fitGroupId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].fitLeaderUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 leader user id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].fitGroupName</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 이름</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].penaltyAmount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 패널티 금액</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].penaltyAccountBankCode</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 패널티 입금 계좌 은행코드</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].penaltyAccountNumber</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 패널티 입금 계좌번호</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].category</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 카테고리 ( 1:헬스, 2:축구, 3:농구, 4:야구, 5: 클라이밍, 6: 배드민턴, 7: 필라테스, 10: 기타 )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].introduction</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 소개글</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].cycle</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 운동 인증 주기 ( null시 기본값 일주일 - 1: 일주일, 2: 한달, 3: 일년 )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].frequency</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 주기별 인증 필요 횟수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].maxFitMate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group최대 인원 수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].presentFitMateCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group현재 인원 수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].multiMediaEndPoints</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group 멀티 미디어 end point list ( 주어진 index 순으로 return )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].state</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 상태 (false: 정상, true: 삭제)</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group 생성 일자</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">pageable object</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.pageNumber</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회 페이지 번호</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.pageSize</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회 한 size</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.sort</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">sort object</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.sort.empty</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">sort 요청 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.sort.sorted</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">sort 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.sort.unsorted</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">unsort 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.offset</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">대상 시작 번호</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.unpaged</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">unpaged</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.paged</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">paged</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>size</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">List 크기</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회 페이지 번호</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>sort</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">sort object</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>sort.empty</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">sort 요청 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>sort.sorted</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">sort 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>sort.unsorted</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">unsort 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>numberOfElements</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">numberOfElements</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>first</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">처음인지 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>last</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">마지막인지 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>empty</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">비어있는지 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"content":[{"presentFitMateCount":8,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":2,"fitLeaderUserId":"testUserId1","fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒1","penaltyAmount":5001,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674201","category":1,"introduction":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰덉   슫 룞 빐 빞 븳 떎怨   깮媛곹빀 땲 떎  떦 떊 룄  뿬李쎌씠  맗 떆 떎 洹쇱쑁  쑕 떇 뵲 쐢  깮媛곷룄 留덉떗 눥1","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.735081200Z","maxFitMate":21,"state":false},{"presentFitMateCount":9,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":3,"fitLeaderUserId":"testUserId2","fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒2","penaltyAmount":5002,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674202","category":1,"introduction":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰덉   슫 룞 빐 빞 븳 떎怨   깮媛곹빀 땲 떎  떦 떊 룄  뿬李쎌씠  맗 떆 떎 洹쇱쑁  쑕 떇 뵲 쐢  깮媛곷룄 留덉떗 눥2","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.735081200Z","maxFitMate":22,"state":false},{"presentFitMateCount":10,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":4,"fitLeaderUserId":"testUserId3","fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒3","penaltyAmount":5003,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674203","category":1,"introduction":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰덉   슫 룞 빐 빞 븳 떎怨   깮媛곹빀 땲 떎  떦 떊 룄  뿬李쎌씠  맗 떆 떎 洹쇱쑁  쑕 떇 뵲 쐢  깮媛곷룄 留덉떗 눥3","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.735081200Z","maxFitMate":23,"state":false},{"presentFitMateCount":11,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":5,"fitLeaderUserId":"testUserId4","fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒4","penaltyAmount":5004,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674204","category":1,"introduction":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰덉   슫 룞 빐 빞 븳 떎怨   깮媛곹빀 땲 떎  떦 떊 룄  뿬李쎌씠  맗 떆 떎 洹쇱쑁  쑕 떇 뵲 쐢  깮媛곷룄 留덉떗 눥4","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.735081200Z","maxFitMate":24,"state":false},{"presentFitMateCount":12,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":6,"fitLeaderUserId":"testUserId5","fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒5","penaltyAmount":5005,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674205","category":1,"introduction":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰덉   슫 룞 빐 빞 븳 떎怨   깮媛곹빀 땲 떎  떦 떊 룄  뿬李쎌씠  맗 떆 떎 洹쇱쑁  쑕 떇 뵲 쐢  깮媛곷룄 留덉떗 눥5","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.735081200Z","maxFitMate":25,"state":false}],"pageable":{"pageNumber":1,"pageSize":5,"sort":{"empty":true,"sorted":false,"unsorted":true},"offset":5,"unpaged":false,"paged":true},"size":5,"number":1,"sort":{"empty":true,"sorted":false,"unsorted":true},"first":false,"last":false,"numberOfElements":5,"empty":false}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 3255
Content-Type: application/json

{"content":[{"presentFitMateCount":8,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":2,"fitLeaderUserId":"testUserId1","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디1","penaltyAmount":5001,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674201","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼1","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.735081200Z","maxFitMate":21,"state":false},{"presentFitMateCount":9,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":3,"fitLeaderUserId":"testUserId2","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디2","penaltyAmount":5002,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674202","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼2","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.735081200Z","maxFitMate":22,"state":false},{"presentFitMateCount":10,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":4,"fitLeaderUserId":"testUserId3","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디3","penaltyAmount":5003,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674203","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼3","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.735081200Z","maxFitMate":23,"state":false},{"presentFitMateCount":11,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":5,"fitLeaderUserId":"testUserId4","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디4","penaltyAmount":5004,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674204","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼4","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.735081200Z","maxFitMate":24,"state":false},{"presentFitMateCount":12,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":6,"fitLeaderUserId":"testUserId5","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디5","penaltyAmount":5005,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674205","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼5","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.735081200Z","maxFitMate":25,"state":false}],"pageable":{"pageNumber":1,"pageSize":5,"sort":{"empty":true,"sorted":false,"unsorted":true},"offset":5,"unpaged":false,"paged":true},"size":5,"number":1,"sort":{"empty":true,"sorted":false,"unsorted":true},"first":false,"last":false,"numberOfElements":5,"empty":false}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_fit_group_filter_by_user_id"><a class="link" href="#_fit_group_filter_by_user_id">Fit Group Filter By User id</a></h3>
<div class="paragraph">
<p>path-parameters</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<caption class="title">Table 4. /fit-group-service/groups/filters/{user-id}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>user-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group 목록을 조회할 user id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">GET /fit-group-service/groups/filters/testUserId HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Host: localhost:8080</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/fit-group-service/groups/filters/testUserId' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[]</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group List</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[].fitGroupId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[].fitLeaderUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 leader user id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[].fitGroupName</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 이름</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[].penaltyAmount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 패널티 금액</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[].penaltyAccountBankCode</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 패널티 입금 계좌 은행코드</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[].penaltyAccountNumber</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 패널티 입금 계좌번호</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[].category</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 카테고리 ( 1:헬스, 2:축구, 3:농구, 4:야구, 5: 클라이밍, 6: 배드민턴, 7: 필라테스, 10: 기타 )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[].introduction</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 소개글</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[].cycle</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 운동 인증 주기 ( null시 기본값 일주일 - 1: 일주일, 2: 한달, 3: 일년 )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[].frequency</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 주기별 인증 필요 횟수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[].maxFitMate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group최대 인원 수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[].presentFitMateCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group현재 인원 수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[].multiMediaEndPoints</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group 멀티 미디어 end point list ( 주어진 index 순으로 return )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[].state</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 상태 (false: 정상, true: 삭제)</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupDetails[].createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group 생성 일자</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"fitGroupDetails":[{"presentFitMateCount":8,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":2,"fitLeaderUserId":"testUserId1","fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒1","penaltyAmount":5001,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674201","category":1,"introduction":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰덉   슫 룞 빐 빞 븳 떎怨   깮媛곹빀 땲 떎  떦 떊 룄  뿬李쎌씠  맗 떆 떎 洹쇱쑁  쑕 떇 뵲 쐢  깮媛곷룄 留덉떗 눥1","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.604430200Z","maxFitMate":21,"state":false},{"presentFitMateCount":9,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":3,"fitLeaderUserId":"testUserId2","fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒2","penaltyAmount":5002,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674202","category":1,"introduction":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰덉   슫 룞 빐 빞 븳 떎怨   깮媛곹빀 땲 떎  떦 떊 룄  뿬李쎌씠  맗 떆 떎 洹쇱쑁  쑕 떇 뵲 쐢  깮媛곷룄 留덉떗 눥2","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.604430200Z","maxFitMate":22,"state":false},{"presentFitMateCount":10,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":4,"fitLeaderUserId":"testUserId3","fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒3","penaltyAmount":5003,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674203","category":1,"introduction":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰덉   슫 룞 빐 빞 븳 떎怨   깮媛곹빀 땲 떎  떦 떊 룄  뿬李쎌씠  맗 떆 떎 洹쇱쑁  쑕 떇 뵲 쐢  깮媛곷룄 留덉떗 눥3","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.604430200Z","maxFitMate":23,"state":false},{"presentFitMateCount":11,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":5,"fitLeaderUserId":"testUserId4","fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒4","penaltyAmount":5004,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674204","category":1,"introduction":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰덉   슫 룞 빐 빞 븳 떎怨   깮媛곹빀 땲 떎  떦 떊 룄  뿬李쎌씠  맗 떆 떎 洹쇱쑁  쑕 떇 뵲 쐢  깮媛곷룄 留덉떗 눥4","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.604430200Z","maxFitMate":24,"state":false},{"presentFitMateCount":12,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":6,"fitLeaderUserId":"testUserId5","fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒5","penaltyAmount":5005,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674205","category":1,"introduction":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰덉   슫 룞 빐 빞 븳 떎怨   깮媛곹빀 땲 떎  떦 떊 룄  뿬李쎌씠  맗 떆 떎 洹쇱쑁  쑕 떇 뵲 쐢  깮媛곷룄 留덉떗 눥5","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.604430200Z","maxFitMate":25,"state":false}]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 2994
Content-Type: application/json

{"fitGroupDetails":[{"presentFitMateCount":8,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":2,"fitLeaderUserId":"testUserId1","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디1","penaltyAmount":5001,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674201","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼1","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.604430200Z","maxFitMate":21,"state":false},{"presentFitMateCount":9,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":3,"fitLeaderUserId":"testUserId2","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디2","penaltyAmount":5002,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674202","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼2","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.604430200Z","maxFitMate":22,"state":false},{"presentFitMateCount":10,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":4,"fitLeaderUserId":"testUserId3","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디3","penaltyAmount":5003,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674203","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼3","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.604430200Z","maxFitMate":23,"state":false},{"presentFitMateCount":11,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":5,"fitLeaderUserId":"testUserId4","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디4","penaltyAmount":5004,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674204","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼4","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.604430200Z","maxFitMate":24,"state":false},{"presentFitMateCount":12,"multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"fitGroupId":6,"fitLeaderUserId":"testUserId5","fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디5","penaltyAmount":5005,"penaltyAccountBankCode":"090","penaltyAccountNumber":"3333-03-53674205","category":1,"introduction":"헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼5","cycle":1,"frequency":7,"createdAt":"2024-04-18T01:59:09.604430200Z","maxFitMate":25,"state":false}]}</code></pre>
</div>
</div>
</div>
</div>
</div>
<div class="sect1">
<h2 id="_2_bank_code"><a class="link" href="#_2_bank_code">2. bank-code</a></h2>
<div class="sectionbody">
<div class="sect2">
<h3 id="_get_bank_code_list"><a class="link" href="#_get_bank_code_list">Get Bank-code List</a></h3>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">GET /fit-group-service/bank-codes HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Host: localhost:8080</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/fit-group-service/bank-codes' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>bankCodeDetails[]</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">은행 코드 목록</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>bankCodeDetails[].code</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">은행 코드</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>bankCodeDetails[].codeName</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">은행명</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"bankCodeDetails":[{"code":"090","codeName":"移댁뭅 삤諭낇겕"},{"code":"089","codeName":"耳  씠諭낇겕"}]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 106
Content-Type: application/json

{"bankCodeDetails":[{"code":"090","codeName":"카카오뱅크"},{"code":"089","codeName":"케이뱅크"}]}</code></pre>
</div>
</div>
</div>
</div>
</div>
<div class="sect1">
<h2 id="_3_fit_mate"><a class="link" href="#_3_fit_mate">3. fit-mate</a></h2>
<div class="sectionbody">
<div class="sect2">
<h3 id="_register_fit_mate"><a class="link" href="#_register_fit_mate">Register Fit Mate</a></h3>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">POST /fit-group-service/mates HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 45
Host: localhost:8080

{"requestUserId":"testUserID","fitGroupId":1}</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>requestUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Fit mate로 등록을 요청한 User id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Fit group id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"requestUserId":"testUserID","fitGroupId":1}</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/fit-group-service/mates' -i -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json' \
    -d '{"requestUserId":"testUserID","fitGroupId":1}'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>isRegisterSuccess</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">등록 성공 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"isRegisterSuccess":true}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 201 Created
Content-Length: 26
Content-Type: application/json

{"isRegisterSuccess":true}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_get_fit_mate_list"><a class="link" href="#_get_fit_mate_list">Get Fit Mate List</a></h3>
<div class="paragraph">
<p>path-parameters</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<caption class="title">Table 5. /fit-group-service/mates/{fit-group-id}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fit-group-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회할 Fit group id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">GET /fit-group-service/mates/1 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Host: localhost:8080</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs"></code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/fit-group-service/mates/1' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회한 fit group id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitLeaderDetail</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 리더 detail</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitLeaderDetail.fitLeaderUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group 리더의 user id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitLeaderDetail.createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group 리더의 선정일시</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitMateDetails</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit mate list</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitMateDetails[].fitMateId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit mate id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitMateDetails[].fitMateUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit mate 의 user id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitMateDetails[].createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit mate 등록일시</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"fitGroupId":1,"fitLeaderDetail":{"fitLeaderUserId":"fitLeaderUserId","createdAt":"2024-04-18T01:59:16.782760700Z"},"fitMateDetails":[{"fitMateId":1,"fitMateUserId":"fitMateUserId1","createdAt":"2024-04-18T01:59:16.782760700Z"},{"fitMateId":2,"fitMateUserId":"fitMateUserId2","createdAt":"2024-04-18T01:59:16.782760700Z"}]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 324
Content-Type: application/json

{"fitGroupId":1,"fitLeaderDetail":{"fitLeaderUserId":"fitLeaderUserId","createdAt":"2024-04-18T01:59:16.782760700Z"},"fitMateDetails":[{"fitMateId":1,"fitMateUserId":"fitMateUserId1","createdAt":"2024-04-18T01:59:16.782760700Z"},{"fitMateId":2,"fitMateUserId":"fitMateUserId2","createdAt":"2024-04-18T01:59:16.782760700Z"}]}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_delete_fit_mate"><a class="link" href="#_delete_fit_mate">Delete Fit Mate</a></h3>
<div class="paragraph">
<p>path-parameters</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<caption class="title">Table 6. /fit-group-service/mates/{fit-group-id}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fit-group-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">탈퇴할 Fit group id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">DELETE /fit-group-service/mates/1 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 30
Host: localhost:8080

{"requestUserId":"testUserID"}</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>requestUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">탈퇴를 요청한 User id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"requestUserId":"testUserID"}</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/fit-group-service/mates/1' -i -X DELETE \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json' \
    -d '{"requestUserId":"testUserID"}'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>isDeleteSuccess</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">삭제 성공 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"isDeleteSuccess":true}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 24
Content-Type: application/json

{"isDeleteSuccess":true}</code></pre>
</div>
</div>
</div>
</div>
</div>
<div class="sect1">
<h2 id="_4_fit_management"><a class="link" href="#_4_fit_management">4. fit-management</a></h2>
<div class="sectionbody">
<div class="sect2">
<h3 id="_kick_fit_mate"><a class="link" href="#_kick_fit_mate">Kick Fit Mate</a></h3>
<div class="paragraph">
<p>path-parameters</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<caption class="title">Table 7. /fit-group-service/managements/mates/{fit-group-id}/{user-id}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fit-group-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">추방할 Fit group id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>user-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">추방할 Fit mate user id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">DELETE /fit-group-service/managements/mates/1/kickedUserId HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 32
Host: localhost:8080

{"requestUserId":"leaderUserId"}</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>requestUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">추방을 요청한 User id ( fit leader 여야함 )</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"requestUserId":"leaderUserId"}</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/fit-group-service/managements/mates/1/kickedUserId' -i -X DELETE \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json' \
    -d '{"requestUserId":"leaderUserId"}'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>isKickSuccess</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">추방 성공 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"isKickSuccess":true}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 22
Content-Type: application/json

{"isKickSuccess":true}</code></pre>
</div>
</div>
</div>
</div>
</div>
</div>
<div id="footer">
<div id="footer-text">
Version 0.0.1-SNAPSHOT<br>
Last updated 2024-04-12 10:27:36 +0900
</div>
</div>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.15.6/styles/github.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.15.6/highlight.min.js"></script>
<script>hljs.initHighlighting()</script>
</body>
</html>
