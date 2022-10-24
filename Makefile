run:
	source .env && ./gradlew bootRun

run/fake:
	JWT_SECRET=test SMTP_PASSWORD=changeit SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun
