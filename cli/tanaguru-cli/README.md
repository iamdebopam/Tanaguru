1) Optimized some redundant codes
2) Cleanup the codes
3) The warnings highlight attempts to access static methods (`withDescription`, `hasArg`, `isRequired`, `create`) from `OptionBuilder` using an instance reference. In Apache Commons CLI, these methods should be accessed statically. I've corrected this by using `Option.builder()` instead of `OptionBuilder`, adhering to the correct usage pattern.

For updating this we needed 
**<dependency>
    <groupId>commons-cli</groupId>
    <artifactId>commons-cli</artifactId>
    <version>1.4</version> <!-- Adjust version as needed -->
</dependency>**

4) BasicParser is deprecated so it's recommended to migrate to DefaultParser to ensure compatibility with future versions of Apache Commons CLI and to avoid using deprecated APIs.
