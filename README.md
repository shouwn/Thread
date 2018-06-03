# Thread 정리

## 이 레퍼지토리의 목적

 이 레퍼지토리를 만든 목적은 단순히 내 지식을 정리하자는 것이었다. 하지만 주위에 쓰레드에 대해 잘 모르는 사람들이 많이 있고, 다음 학기에 하는 수업에 프로젝트가 있는데, 그때 설명용을 위해서라도 누군가에게 알려주기 위한 레퍼지토리로 만들어야겠다는 생각을 하였다.  그래서 처음 쓰레드를 접하는 사람이 알 수 있도록 상세하게 적어보면서 스스로도 더 공부를 해야겠다는 생각으로 다시 적어보기로 한다. 그렇기 때문에 완성되기 전에는 많이 더러울 수 있다.

## 목차
 0. 프로세스란?
 1. 쓰레드란?
 2. 쓰레드의 비동기화와 동기화?
 3. 쓰레드의 제어는 어떻게 할까?
 4. 쓰레드 풀이란?
 5. 실제 우리가 사용하는 서버는 어떻게 쓰레드를 사용할까? (톰캣)
 6. 쓰레드 concurrency 패턴들에는 무엇이 있을까?
 7. 쓰레드를 어떻게 사용해야할까?

> 여기서 이야기하는 쓰레드는 모두 자바 쓰레드를 기준으로 한다.

## 0. 프로세스란?

쓰레드를 알기 위해서는 먼저 프로세스를 알아야한다. 프로세스란 메모리 상에 올라가 실행되고 있는 우리들이 만든 프로그램이다. 코드를 작성하여 컴파일과 빌드를 하기만 하면 그것은 프로세스라고 부를 수 없다. 메모리에 올라가 실제로 실행이 되어야 프로세스라고 부를 수 있다.

프로세스가 메모리를 차지하는 영역은 크게 4가지로 나눌 수 있다.

 - 데이터 영역
 - 텍스트(코드) 영역
 - 스택 영역
 - 힙 영역

>  Data 영역을 아직 초기화되지 않은 변수가 있는 BBS(Block Started by Symbol) 영역과 초기화된 변수가 있는 Data 영역으로 나눌 수 있지만 여기서는 크게 4가지 기준으로만 살펴본다.
>  

**데이터 영역**

데이터 영역에는 전역 변수나 static 변수들이 들어가 있다.

**텍스트 영역**

텍스트 영역에는 우리가 작성한 코드가 들어있다. PC(프로그램 카운터)가 가르키고 있는 곳이 이 텍스트 영역에서 다음 실행될 코드가 있는 줄이다.

**스택 영역**

자바 기준으로 메소드가 호출될 때 거기에 있는 지역 변수나 매개 변수가 차지하고 있는 영역이다. 함수가 종료되면 차지하고 있던 영역을 해제한다. 

**힙 영역**

자바 기준으로 new 하면서 객체를 생성할 때 차지하고 있는 영역이다.

> 자바 기준으로 배열은 모두 객체로 생성되기 때문에 힙 영역에 생기지만, C 기준으로 배열은 지역 변수이기 때문에 스택 영역에 생긴다. 그래서 C에서 malloc을 호출하여 배열을 생성하지 않았다면 스택에 차지하게 된다.
> 
> 
**힙과 스택 오버플로우**

[여기](https://github.com/shouwn/Thread/blob/master/basic/stack_heap%20area.md)를 참조

기본적으로 프로세스가 차지하는 영역은 위와 같이 4가지라고 볼 수 있다. 하지만 우리가 사용하고 있는 것은 JVM이다. 그렇다면 JVM이 작동하는 과정까지는 모르더라도 JVM이 메모리 영역을 어떻게 관리하고 있는지는 알아야 한다. JVM에서는 운영체제로부터 할당 받아 프로그램을 돌리기 위해 사용하는 메모리 영역을 Runtime Data Area라고 한다.  그 영역은 다음과 같이 나뉘어진다.

 - 메소드 에리어 (Method Area)
 - 힙 (Heap)
 - JVM 스택 (Java Virtual Machine Stacks)
 - PC 레지스터 (PC Registers)
 - Native 메소드 스택 (Native Method Stacks)

## 참조
[# [Symbolic references in Java]](https://stackoverflow.com/questions/17406159/symbolic-references-in-java)

[# The Architecture of the Java Virtual Machine](https://www.artima.com/insidejvm/ed2/jvm2.html)

[# The Java Virtual Machine - The Method Area](https://www.artima.com/insidejvm/ed2/jvm5.html)

[# What Are Runtime Data Areas?](http://www.herongyang.com/JVM/Data-Area-What-Are-Runtime-Data-Areas.html)

[# JVM의 Runtime Data Area](http://www.holaxprogramming.com/2013/07/16/java-jvm-runtime-data-area/)

[# [JAVA/JVM] 메모리 구조](http://stophyun.tistory.com/37)

[# Data segment_wikipedia](https://en.wikipedia.org/wiki/Data_segment)

[# (C/C++) 참고용 정리 - 메모리 영역](https://blog.perfectacle.com/2017/02/09/c-ref-004/)
