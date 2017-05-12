local-env:
	docker-compose -f docker-compose-local.yml up -d

stop:
	docker-compose -f docker-compose-local.yml -f docker-compose.yml stop
	docker-compose -f docker-compose-local.yml -f docker-compose.yml rm -f

build-ci:stop
	docker-compose -f docker-compose.yml build

ci:build-ci
	docker-compose -f docker-compose.yml run --rm test

run:build-ci
	docker-compose -f docker-compose.yml run --rm -p 8080:8080 app
