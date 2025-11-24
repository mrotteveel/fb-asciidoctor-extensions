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
    ./gradlew publish -PcredentialsPassphrase=<credentials password>
    ```
    
    For snapshots we can forego signing and generating javadoc + sources using:
    
    ```
    ./gradlew publish
    ```

This requires the proper Sonatype credentials to be set, see also the next section.

Publishing
----------

To publish to Maven use

```
gradlew clean publish -PcredentialsPassphrase=<credentials password>
```
Where `<credentials password>` is the password used to add the credentials (see
also below).

Publishing to Maven Central (non-SNAPSHOT releases) requires the following
additional steps:

1. Promote the published artifacts to Central Portal through the SwaggerUI <https://ossrh-staging-api.central.sonatype.com/swagger-ui/>
2. An explicit close through <https://central.sonatype.com/publishing/deployments>.

To be able to deploy, you need the following:

a `<homedir>/.gradle/gradle.properties` with the following properties:

```
signing.keyId=<gpg key id>
signing.secretKeyRingFile=<path to your secring.gpg> 

centralUsername=<Central Portal usertoken name>
```

In addition, you need to set the following credentials

```
./gradlew addCredentials --key signing.password --value <your secret key password> -PcredentialsPassphrase=<credentials password> 
./gradlew addCredentials --key centralPassword --value <your Central Portal usertoken password> -PcredentialsPassphrase=<credentials password> 
```

See https://github.com/etiennestuder/gradle-credentials-plugin for details on
credentials.

See https://central.sonatype.org/publish/publish-portal-maven/ for details on
Maven publishing.
 