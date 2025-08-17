🧱 객체 지향 벽돌깨기 게임 (Breakout Game)
간단한 벽돌깨기 게임을 만들면서 객체 지향 프로그래밍(OOP) 원리를 탐구하는 프로젝트입니다. 자바의 객체지향과 추상개념에 대해 공부하면서 구현한거라 기간은 일주일정도 걸렸던 것 같습니다. 


주요 특징
게임 요소: 공, 벽돌, 패들 등 게임 구성 요소를 클래스로 만들었습니다.


디자인 패턴: Template Method 패턴, Factory Method 패턴

충돌 처리: 다양한 객체들이 서로 충돌할 때 자연스럽게 반응하도록 구현했습니다.

설계 원칙: 상속의 한계를 극복하고 인터페이스와 합성을 활용하는 방법을 보여줍니다.

기술 스택
Java: 게임 로직 구현

JavaFX: 그래픽과 UI 처리

JUnit 5: 코드 테스트

클래스 다이어그램
이 게임의 설계는 Movable, Paintable 같은 인터페이스를 사용해 기능을 유연하게 조합하였습니다. 이 인터페이스 때문에 깔끔해지고 확장하기 쉬워집니다!

<img width="1450" height="1056" alt="image" src="https://github.com/user-attachments/assets/ca6777c6-d6fd-4e76-b47a-23f8f5db7498" />

🛠️ 시작하기
JDK 설치: Java를 설치해주세요.

JavaFX 설정: Maven 또는 Gradle에 JavaFX를 추가해주세요.

실행: BreakoutWorldApp 클래스를 실행하면 됩니다.
