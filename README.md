# encoder-test
Testing the encoding functionality of various security related encoders.

## Quick Start
If you just want to jump to the interesting bit, head to the `output` folder
* `All_HTML_Encoder_Test.html` - compares the most popular HTML encoders side by side
* `Apache_StringEscapeUtils_escapeHtml3_All_Versions_Test.html` - compares all versions of StringEscapeUtils escapeHtml3 method
* `OWASP_ESAPI_legacy_encodeForHTML_All_Versions_Test.html` - compares all versions of ESAPI java legacy encodeForHTML method
* `OWASP_Java-Encoder_forHtmlAttribute_All_Versions_Test.html` - compares all versions of OWASP java-encoder forHtmlAttribute method
* `OWASP_Java-Encoder_forHtmlContent_All_Versions_Test.html` - compares all versions of OWASP java-encoder forHtmlContent method
* `OWASP_Java-Encoder_forHtml_All_Versions_Test.html` - compares all versions of OWASP java-encoder forHtml method

## Building/Running locally
You need maven to build the project locally. Download/clone the repository to your local disk.
* `cd encoder-test-wrapper && mvn clean install`
* `cd ../encoder-test-core && mvn clean package`
* `cd .. && java -jar encoder-test-core/target/encoder-test-core-1.0-SNAPSHOT-jar-with-dependencies.jar encoder-test-core/src/main/resources/*.json`
The output directory will contain the results of executing the tests defined in the json files (generated date is in the footer)
