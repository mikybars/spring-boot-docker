[![CircleCI](https://circleci.com/gh/mperezi/spring-tasks-docker.svg?style=svg)](https://circleci.com/gh/mperezi/spring-tasks-docker) 

# spring-tasks-docker

Demonstration of how to create a Docker image for a [simple Spring Boot application](https://github.com/mperezi/spring-tasks-app).

The first approach [uses a Dockerfile](https://github.com/mperezi/spring-tasks-docker/tree/with-dockerfile) built from scratch and crafted through several iterations enforcing some best practices along the way.

Finally the second method leverages the [Jib Gradle plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin) for automating the build process:

> Jib builds optimized Docker and [OCI](https://github.com/opencontainers/image-spec) images for your Java applications **without a Docker daemon** - and without deep mastery of Docker best-practices.

The final image is hosted at the [Docker Hub](https://hub.docker.com/repository/docker/mperezi/spring-tasks-app).

## References

* [Build containers faster with Jib, a Google image build tool for Java applications](https://youtu.be/H6gR_Cv4yWI)

* https://docs.docker.com/develop/develop-images/dockerfile_best-practices

* https://github.com/hadolint/hadolint

* https://github.com/GoogleContainerTools/jib

  
