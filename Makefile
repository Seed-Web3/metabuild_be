
build:
	./gradlew build

run:
	source .env && ./gradlew bootRun

run/fake:
	JWT_SECRET=test SMTP_PASSWORD=changeit SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun

docker/build:
	docker build -t seedin_be .
	docker tag seedin_be sotcsa/seedin_be
	docker push sotcsa/seedin_be

docker/run:
	docker run --name seed --rm -d -e SPRING_PROFILES_ACTIVE=dev -e JWT_SECRET=test -p 17641:17641 seedin_be
