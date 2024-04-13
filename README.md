## MSA 마이크로 서비스 아키텍처 설계 예제 프로젝트

-----  

`Spring 에 MSA 를 적용하는 방법을 공부하는 프로젝트`


----

### 프로젝트 구조

- board-service : 게시판 도메인과 관련한 모든 소스코드
- member-service: 멤버 도메인과 관련한 모든 소스코드
- fl-common : 전역으로 공유했었던 소스 코드들(ex 인증, 구성, 전역 예외처리)

**MSA 에서 멀티 모듈을 설계할 때, 도메인별로 모듈을 분리하자**

(1) Root 모듈 구성 방법

가장 최상단의 경로를 Root 로 정했다.

빌드는 항상 root 프로젝트를 기준으로 진행할 예정이기에 root 를 제외한 나머지 모듈에선 gradle, gradlew 등의 파일이 없고, build.gradle 과 src 폴더만 존재한다.

1) 기존 소스 코드인 `src` 폴더 삭제
2) build.gradle 에서 하위 모듈을 설정
3) settings.gradle 에서 하위 모듈 추가

**root 의 setting.gradle**

```
rootProject.name = 'find-life'
include 'member-service'
include 'board-service'
include 'fl-common'
include 'discovery-server'
```

하위 모듈을 include 로 선언해 Root 모듈에서 설정할 수 있도록 설정

**build.gradle**

```
plugins {
	id 'java'
	id 'org.springframework.boot' version '3.2.4'
	id 'io.spring.dependency-management' version '1.1.4'
}

repositories {
	mavenCentral()
}

subprojects {
	group = 'com.shboard'
	version = '0.0.1'
	sourceCompatibility = '17'

	apply plugin: 'java'
	apply plugin: 'org.springframework.boot'
	apply plugin: 'io.spring.dependency-management'
	apply plugin: 'java-test-fixtures'

	repositories {
		mavenCentral()
	}

	ext {
		set('springCloudVersion', "2023.0.0")
	}

	dependencyManagement {
		imports {
			mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
		}
	}

	bootJar { enabled = false }
	jar { enabled = true }
}

// fl-common, member-service 모듈을 의존한다.
project(':board-service') {
	dependencies {
		implementation project(':fl-common')
		implementation project(':member-service')
		implementation(testFixtures(project(":fl-common")))
	}
}

// fl-common 모듈을 의존한다.
project(':member-service') {
	dependencies {
		implementation project(':fl-common')
		implementation(testFixtures(project(":fl-common")))
	}
}

```

subprojects : setting.gradle 에서 선언한 하위 모듈들의 설정을 관리, 하위 모듈들에 모두 적용되는 설정 적용  
project : 하위 모듈을 지정해 해당 모듈에 들어갈 설정 커스텀