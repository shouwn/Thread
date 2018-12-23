# DI 와 AOP

## DI

예를 들어 다음과 같이 두 개의 서비스 클래스가 있고, 둘 사이에 연관관계가 있다고 가정해보자

    class WrapServie {
        BoxService boxService = new BoxService();
    }

    class BoxService {
    
    }

WrapService 는 BoxService의 참조를 가지고 있다. 


