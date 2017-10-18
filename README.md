# cj.software.experiments.arquillian
Some of my experiments with Arquillian.

"mvn clean" will unpack a Wildfly server into your target directory.
"mvn install" will install it, but not execute the integration tests. To achieve that the integration tests are executed, start "mvn install" with the profile "wildfly-managed" switched on
