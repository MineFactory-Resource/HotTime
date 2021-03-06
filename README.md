HotTime
=============
----
플러그인 소개
-------------
서버 기준으로 특정 시간대마다 명령어를 실행시키는 방식으로, 서버에 접속 중인 플레이어를 대상으로 이벤트를 자동으로 진행해주는 플러그인입니다.

----
플러그인 적용 방법
-------------
1. [release](https://github.com/MineFactory-Resource/HotTime/releases) 항목에서 최신 버전의 플러그인 파일을 다운로드
2. 서버의 plugins 폴더에 다운로드 받은 *.jar 파일을 넣기

----
플러그인 config.yml 사용 방법
-------------
양식)
```yaml
<이벤트이름>:
  time: '<시간대>'
  before: '<메세지>'
  after: '<메세지>'
  commands:
    - '[명령어1]'
    - '[명령어2]'
    - '[명령어3]'
    ...
```
이벤트 이름은 중복없이 마음껏 작성 가능하며, 시간대는 HH:mm 양식(00:00 ~ 23:59)으로 작성하여야 합니다.  
명령어는 '/'없이 작성하여야 하며, 이벤트 안에 작성한 모든 명령어가 실행됩니다.  
또한 명령어가 전체 보상이 아닌 개인 보상일 경우, 플레이어 닉네임을 입력해야되는 부분에 **%player%**를 작성하면 됩니다.  
  
이벤트 시작 1분 전에 before 부분의 메세지가 전송되며, 이벤트 명령어가 실행된 후에는 after 부분의 메세지가 전송됩니다.  
_before 부분이나 after 부분을 공백으로 남겨둘 경우, 메세지를 전송하지 않습니다._

예제)
```yaml
events:
  test1:
    time: '00:00'
    before: '&e60&f초 후 &c핫타임 &f아이템이 지급됩니다.'
    after: '&c핫타임 &f아이템을 지급하였습니다.'
    commands:
      - 'say example reward'
      - 'give %player% minecraft:stone 3'

  깜짝이벤트:
    time: '12:30'
    before: '' # 전송되지 않는 메세지
    after: '&d깜짝 이벤트!'
    commands:
      - 'give %player% minecraft:stone 1'
```
----
Votifier 플러그인 지원
-------------

Votifier 플러그인을 통해 해당 플러그인의 투표를 한 사람과 안한 사람을 구별하여 지급여부를 정할 수 있습니다. 투표 서비스 사이트를 service란에 입력해야 합니다.  
투표를 한 플레이어일 경우 **%vote_player%**, 안한 플레이어일 경우 **%no_vote_player%** 를 개인에게 보상을 지급하는 명령어의 닉네임란에 입력해야합니다.  
예제)
```yaml
votifier:
  service: 'Example.kr'
events:
  vote:
    time: '01:00'
    before: '&e60&f초 후 &c핫타임 &f아이템이 지급됩니다.'
    after: '&c핫타임 &f아이템을 지급하였습니다.'
    commands:
      - 'say example reward'
      - 'give %no_vote_player% minecraft:stone 3'
      - 'give %vote_player% minecraft:stone 10'
```