.PHONY: build

build:
	sam build

TEST_DIRS := hello-world process-image
test:
	@$(foreach dir, $(TEST_DIRS), (cd $(dir) && go test) || exit 1;)

deploy:
	sam deploy --no-confirm-changeset --no-fail-on-empty-changeset