.PHONY: build start
build:
	@mvn clean install -DskipTests

start: build
	@docker-compose build
	@docker-compose up --remove-orphans
