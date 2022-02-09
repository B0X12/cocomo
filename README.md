  
  
**COCOMO**</br>
2022 Winter project  
  
---
  
2022년 겨울방학 단기 프로젝트 (10주) 관련 문서를 정리해둔 파일입니다.  
백업 용도로 사용되고 있으니 참고바랍니다.  
</br></br>
  
  
  
## 파일 구성
  
- ***android***  
	QR 코드 스캐너입니다.  
	JSON 형식의 데이터를 파싱할 수 있습니다.  
	
- ***cocomo***  
	QR 코드 생성기입니다.  
	JSON 형식의 데이터를 기반으로 QR을 생성합니다.  
	
- ***server***  
	Spring boot의 MVC 패턴을 통해 작성된 서버입니다.  
	*android - client - db* 각각의 통신에 사용됩니다.  
	~~(차후 Rest API로 간소화 될 수 있습니다.)~~  
</br></br>
  
  
## 사용 기술 및 라이브러리
  
**IDE, DB**  
 - Intelij & Eclipse (Java 개발에 사용)  
 - Android Studio  
 - H2 Database (`\H2\bin>h2.bat`로 실행)  
  
**ETC**  
 - Java 11  
 - Java swing (GUI)  
 - Spring boot  
 - Rest API  
 - zxing  
 - lombok
  
