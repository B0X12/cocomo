package cocomo.restserver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainControlBean
{
    private String message;

}

// @Data
//  클래스 내의 모든 메소드에 대해 getter-setter가 자동 세팅되도록 함

// @AllArgsConstructor
//  Constructor (생성자) 만들어서 매개변수 세팅 안해줘도 됨.
//  ex) public MainControlBean(String message)
//      {
//         this.message = message;
//      }

// @NoArgsConstructor
//  default 생성자 생성
