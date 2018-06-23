# Thread 정리

## 이 레퍼지토리의 목적

 이 레퍼지토리를 만든 목적은 단순히 내 지식을 정리하자는 것이었다. 하지만 주위에 쓰레드에 대해 잘 모르는 사람들이 많이 있고, 다음 학기에 하는 수업에 프로젝트가 있는데, 그때 설명용을 위해서라도 누군가에게 알려주기 위한 레퍼지토리로 만들어야겠다는 생각을 하였다.  그래서 처음 쓰레드를 접하는 사람이 알 수 있도록 상세하게 적어보면서 스스로도 더 공부를 해야겠다는 생각으로 다시 적어보기로 한다. 그렇기 때문에 완성되기 전에는 많이 더러울 수 있다.

## 목차
 0. 프로세스란?
 1. 쓰레드란?
 2. 쓰레드의 비동기화와 동기화?
 3. 쓰레드의 제어는 어떻게 할까?
 4. 쓰레드 풀이란?
 5. 실제 우리가 사용하는 서버는 어떻게 쓰레드를 사용할까? (Tomcat)
 6. 쓰레드 concurrency 패턴들에는 무엇이 있을까?
 7. 쓰레드를 어떻게 사용해야할까?

> 여기서 이야기하는 쓰레드는 모두 자바 쓰레드를 기준으로 한다.

## 0. 프로세스란?

쓰레드를 알기 위해서는 먼저 프로세스를 알아야한다. 프로세스란 메모리 상에 올라가 실행되고 있는 우리들이 만든 프로그램이다. 코드를 작성하여 컴파일과 빌드를 하기만 하면 그것은 프로세스라고 부를 수 없다. 메모리에 올라가 실제로 실행이 되어야 프로세스라고 부를 수 있다.

프로세스가 메모리를 차지하는 영역은 크게 4가지로 나눌 수 있다.

>  Data 영역을 아직 초기화되지 않은 변수가 있는 BBS(Block Started by Symbol) 영역과 초기화된 변수가 있는 Data 영역으로 나눌 수 있지만 여기서는 크게 4가지 기준으로만 살펴본다.
>  
 - 데이터 영역
 - 텍스트(코드) 영역
 - 스택 영역
 - 힙 영역

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
**힙과 스택 오버플로우**
[여기](https://github.com/shouwn/Thread/blob/master/basic/stack_heap%20area.md)를 참조


### JVM에서의 메모리 구조

기본적으로 프로세스가 차지하는 영역은 위와 같이 4가지라고 볼 수 있다. 하지만 우리가 사용하고 있는 것은 JVM이다. 그렇다면 JVM이 작동하는 과정까지는 모르더라도 JVM이 메모리 영역을 어떻게 관리하고 있는지는 알아야 한다. JVM에서는 운영체제로부터 할당 받아 프로그램을 돌리기 위해 사용하는 메모리 영역을 Run-time Data Area라고 한다.  그 영역은 다음과 같이 나뉘어진다.

 - 메소드 에리어 (Method Area)
 - 힙 (Heap)
 - JVM 스택 (Java Virtual Machine Stacks)
 - PC 레지스터 (PC Registers)
 - Native 메소드 스택 (Native Method Stacks)

![Run-time Data Area](https://github.com/shouwn/Thread/blob/master/images/Run-time-Data-Area.gif)

#### Method Area

메소드 에리어에는 Type에 대한 정보가 들어간다.

![Method Area](https://github.com/shouwn/Thread/blob/master/images/Method-Area.gif)

각 영역의 상세 사항은 여기서 다루는 주제가 아니기 때문에 크게 3가지로 나누어서 보자. 

> 이렇게 3가지로 나눈 것은 내가 임의적으로 나눈 것이다. 나중에 기회가 있다면 각각의 상세 사항에 대해 적으려고 한다.

 - 정보
	 + Type Information
	 + Field Information
	 + Method Information
 - 클래스 변수 (static 변수)
 - Constant Pool

**정보**
정보에는 클래스의 정보나 클래스 멤버 변수의 정보 메소드의 정보가 들어 있다. 

**클래스 변수(static 변수)**
클래스 변수는 우리가 클래스 내에서 static 으로 선언한 멤버 변수를 말한다.

**Constant Pool**
우리가 흔히 C에서 사용하는 것처럼 어느 데이터에 접근하려면 메모리 주소를 알아야 한다. 하지만 자바에서는 그렇게 하지 않는다. Symbolic reference는 메모리 주소가 아닌 참조하고자 하는 대상의 이름으로 그 정보가 들어 있는 위치를 지칭하는 것이다. Constant Pool은 Symbolic reference를 담고 있어 찾는 대상의 이름으로 찾으면 그 데이터를 찾을 수 있다. 내부적으로는 index를 이용하여 참조된다.  여기에는 type, field, method symbolic reference가 모두 들어가기 때문에 자바 프로그래밍에서 동적 링킹의 중심 역할을 한다.

Method Area 영역은 모든 쓰레드가 **공유**한다.

> 참고로 Method Area도 자바 가비지 컬렉션의 대상이 된다.  클래스에 대한 정보를 가지고 있기 때문에 안 될 거라 생각할 수 있지만 그렇지 않다. 클래스는 참조가 안 될 때가 있다. 클래스가 참조되지 않을 때는 더 이상 메모리에 로드되어 있을 필요가 없기 때문에 Method Area를 최소화 하기 위해 가비지 컬렉트를 한다. 

#### Heap
힙 영역은 프로세스의 힙 영역과 동일하다고 생각하면 된다. 여기에는 자바에 있는 객체들이 들어있다. 그리고 각 객체는 가비지 컬렉션의 대상이 된다.

Heap 영역은 모든 쓰레드가 **공유**한다.

> 가비지 컬렉션의 내용은 나중에 다룰 기회가 있다면 살펴보도록 한다.

#### JVM Stacks
JVM Stacks은 일반적인 스택이라고 생각하면 된다. 하지만 JVM의 Stacks은 메소드가 호출될 때마다 stack frame 이라는 메소드의 정보를 가지고 있는 frame을 단위로 push 하고 pop 하게 된다. 

JVM Stack은 쓰레드가 생성될 때마다 그 쓰레드의 stack이 **개별**적으로 생성된다. 

#### PC Registers
PC 레지스터는 프로그램 카운터와 마찬가지로 보면 된다.

PC Registers 는 쓰레드가 하나 생성될 때마다 그 쓰레드의 PC Register가 **개별**적으로 생성된다. 

#### Native Method Stacks

자바에서는 C로 짜여진 메소드를 호출할 수 있다. 하지만 JVM Stacks에서 실행하지 않는다. Native Method를 위한 Stacks이 따로 있다. 외부 언어를 위한 환경이 따로 마련되어 있다.  그 환경이 Native Method Stacks 이다. Native Method 와는 native method interface를 통해 소통한다. 이는 JVM 자체가 C로 만들어져 있기 때문에 JVM이 돌아가는 프로세스 상에서 사용하는 Stack을 사용한다는 의미가 된다.

만약 쓰레드가 native method를 호출하게 되면 그 쓰레드의 PC 레지스터의 값은 정의되지 않는다. native method를 실행하고 있기 때문이다.

지금까지 프로세스의 메모리 구조와 JVM의 메모리 구조를 한번 살펴보았다. 다음에는 쓰레드란 무엇인지 살펴보자.

## 1. 쓰레드란?

**쓰레드는 하나의 실행 흐름이다.**

우리가 코드를 작성하 후 빌드하여 저장하면, 이는 프로그램이다. 이를 실행해서 메모리 상에 올려, CPU가 코드를 읽어가면서 실행하고 있는 것이 프로세스이다. 여기서 한 줄씩 코드를 읽어가면서 실행하고 있는 것이 쓰레드이다. 

이를 위에서 배운 메모리를 활용하여 이해하자. 먼저 쓰레드가 생기면 쓰레드를 위한 Stack 에리어가 하나 생기게된다. Stack은 우리가 알 듯이 메소드가 호출되면 생성되어 실행되는 영역이다. 맨 처음 쓰레드는 메소드를 호출하게 된다. main 쓰레드의 경우 main 메소드를 실행하고, 그 외의 쓰레드는 run 메소드를 실행한다. 이는 Stack에 push 되어 실행되어진다. 그리고 이 메소드가 다른 메소드를 호출하면 또 다시 그 메소드가 push 되어가며 실행된다. 이렇게 하나의 실행 흐름이 생기게 된다. 이것이 쓰레드가 하는 일이다. 그리고 모든 메소드가 끝나면 Thread는 종료된다.

![java stack image](https://github.com/shouwn/Thread/blob/master/images/Java-Stack.gif)

### 1) 쓰레드의 메모리 공유


## 참조
[# [Symbolic references in Java]](https://stackoverflow.com/questions/17406159/symbolic-references-in-java)
[# The Architecture of the Java Virtual Machine](https://www.artima.com/insidejvm/ed2/jvm2.html)
[# The Java Virtual Machine - The Method Area](https://www.artima.com/insidejvm/ed2/jvm5.html)
[# What Are Runtime Data Areas?](http://www.herongyang.com/JVM/Data-Area-What-Are-Runtime-Data-Areas.html)
[# JVM의 Runtime Data Area](http://www.holaxprogramming.com/2013/07/16/java-jvm-runtime-data-area/)
[# [JAVA/JVM] 메모리 구조](http://stophyun.tistory.com/37)
[# Data segment_wikipedia](https://en.wikipedia.org/wiki/Data_segment)
[# (C/C++) 참고용 정리 - 메모리 영역](https://blog.perfectacle.com/2017/02/09/c-ref-004/)
