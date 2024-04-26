# 실행 권한 확인
if [ ! -x "$0" ]; then
    echo "에러: 스크립트가 실행 가능한 권한을 가지고 있지 않습니다."
    exit 1
fi

# 1단계: application 빌드
echo "application 빌드 중..."
# os가 m1인 경우 ./gradlew bootJar -PjibArchitecture=arm64 jibDockerBuild 실행
# 아니려면 ./gradlew bootJar jibDockerBuild 실행
if [ "$(uname -m)" == "arm64" ]; then
    echo "amd64 아키텍처에서 빌드 중..."
    if ! ./gradlew bootJar -PjibArchitecture=arm64 jibDockerBuild; then
        echo "에러: application 빌드에 실패했습니다."
        exit 1
    else
        echo "성공: application 빌드 완료."
    fi
else
    echo "x86 아키텍처에서 빌드 중..."
    if ! ./gradlew bootJar jibDockerBuild; then
        echo "에러: application 빌드에 실패했습니다."
        exit 1
    else
        echo "성공: application 빌드 완료."
    fi
fi

# 2단계: docker-compose 실행
echo "docker-compose 실행 중..."
if ! docker-compose -f ./src/main/docker/docker-compose.yml up -d; then
    echo "에러: docker-compose 실행에 실패했습니다."
    exit 1
else
    echo "성공: docker-compose 실행 완료."
fi

# 3단계: dangling 이미지 삭제
echo "Dangling 이미지 삭제 중..."
if ! docker image ls -f "dangling=true" -q | xargs -I {} docker image rm -f {}; then
    echo "에러: Dangling 이미지 삭제에 실패했습니다."
    exit 1
else
    echo "성공: Dangling 이미지 삭제 완료."
fi