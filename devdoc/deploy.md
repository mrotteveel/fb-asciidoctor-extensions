Deploying
=========

1. Update the artifact version in the root `build.gradle.kts`

2. Commit all changes and create a signed tag using

    ```
    git tag -s v1.0 -m 'fb-asciidoctor-extensions 1.0'
    ```
   
3. Push tag using

    ```
    git push origin v1.0
    ```

4. To deploy to Maven use

    ```
    ./gradlew publish -PcentralPassword=<value>
    ```

This requires the proper Sonatype credentials to be set, see also the next section.

Publishing
----------

To publish to Maven use

```
gradlew clean publish -PcentralPassword=<value>
```

This command will prompt for you GPG key password if it's not already cached in
your current session.

Publishing to Maven Central (non-SNAPSHOT releases) requires the following
additional steps:

1. Promote the published artifacts to Central Portal through the SwaggerUI <https://ossrh-staging-api.central.sonatype.com/swagger-ui/>
2. An explicit close through <https://central.sonatype.com/publishing/deployments>.

To be able to deploy, you need the following:

a `<homedir>/.gradle/gradle.properties` with the following properties:

```
signing.gnupg.keyName=<short keyid>

centralUsername=<Central Portal usertoken name>
# Only needed if you don't want to specify -PcentralPassword=... on commandline
centralPassword=<your Central Portal usertoken password>
```

(It's possible `signing.gnupg.keyName` also accepts long key-ids, we haven't checked.)

Make sure the file is only readable and writable by you (chmod 600). If the
password contains backslashes, make sure to escape them by doubling. If the
password contains characters not in ISO 8859-1, make sure to use a Java Unicode
escape.

See https://github.com/etiennestuder/gradle-credentials-plugin for details on
credentials.

See https://central.sonatype.org/publish/publish-portal-maven/ for details on
Maven publishing.
 