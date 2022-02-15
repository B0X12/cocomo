
**COCOMO**</br>
2022 Winter project  
  
---
  
2022년 겨울방학 단기 프로젝트 (2주...) 관련 문서를 정리해둔 파일입니다.  
백업 용도로 사용되고 있으니 참고바랍니다.  
</br></br>
  
  
  
## 파일 구성
  
- ***android***  
	회원가입 및 OTP/QR/지문 인증을 수행하기 위한 목적의 앱입니다.
	앱에 로그인 한 user의 PC를 원격으로 스크린락하는 기능을 포함합니다.
	
- ***rest-server***  
	~~Spring boot의 MVC 패턴을 통해 작성된 서버입니다.~~  
	-> 현재 REST API를 사용한 RESTful Web으로 수정하였습니다.
	*android - client - db*의 통신에 사용됩니다.  
	  
</br></br>
  
  
## 사용 기술 및 라이브러리
  
**IDE, DB**  
 - Intelij & Eclipse 
 - Visual Studio 2019
 - Android Studio  
 - H2 Database (`\H2\bin>h2.bat`로 실행)  
  
**ETC**  
 - C#
 - Java 11  
 - ~~Java swing~~
  - Rest API  
 - Spring  
 - JPA  
 - hateoas  
 - hibernate  
 - zxing (QR)    
 - HMAC (OTP)    
 - biometric (Fingerprint)
 - lombok  
  

