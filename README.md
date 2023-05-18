# encoder-test
Testing the encoding functionality of various security related encoders.

## Quick Start
If you just want to jump to the interesting bit, here's what's in the `output` directory.
* [All_HTML_Encoder_Test.html](http://htmlpreview.github.io/?https://github.com/dledmonds/encoder-test/blob/master/output/All_HTML_Encoder_Test.html) - compares the most popular HTML encoders side by side
* [All_XML_Encoder_Test.html](http://htmlpreview.github.io/?https://github.com/dledmonds/encoder-test/blob/master/output/All_XML_Encoder_Test.html) - compares the most popular XML encoders side by side
* [All_CSS_Encoder_Test.html](http://htmlpreview.github.io/?https://github.com/dledmonds/encoder-test/blob/master/output/All_CSS_Encoder_Test.html) - compares CSS encoders side by side
* [All_Javascript_Encoder_Test.html](http://htmlpreview.github.io/?https://github.com/dledmonds/encoder-test/blob/master/output/All_Javascript_Encoder_Test.html) - compares Javascript encoders side by side
* [All_URL_Encoder_Test.html](http://htmlpreview.github.io/?https://github.com/dledmonds/encoder-test/blob/master/output/All_URL_Encoder_Test.html) - compares URL encoders side by side
* [All_Java_Encoder_Test.html](http://htmlpreview.github.io/?https://github.com/dledmonds/encoder-test/blob/master/output/All_Java_Encoder_Test.html) - compares Java String encoders side by side
* [All_.NET_Encoder_Test.html](http://htmlpreview.github.io/?https://github.com/dledmonds/encoder-test/blob/master/output/All_.NET_Encoder_Test.html) - compares .NET String encoders side by side

## Building/Running locally

First generate the .NET data:
```
docker run --rm -it \
  -v "$PWD/dotnet:/app/dotnet" \
  -v "$PWD/encoder-test-java/src/main/resources/encodingData:/app/encoder-test-java/src/main/resources/encodingData" \
  -v "$PWD/output:/app/output" \
  --workdir /app/dotnet \
  mcr.microsoft.com/dotnet/sdk:6.0 dotnet run
```

Then generate the Java data and produce the reports:
```
docker run --rm -it -v "$PWD:/app" --entrypoint bash --workdir /app maven
mvn clean install
java -jar encoder-test-java/target/encoder-test-java-1-SNAPSHOT-jar-with-dependencies.jar
java -jar encoder-test-transform/target/encoder-test-transform-1-SNAPSHOT-jar-with-dependencies.jar
exit
```

The output directory will contain the results of executing the tests (JSON files) as well as the HTML comparison files. Start at `index.html`
