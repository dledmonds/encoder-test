using System.Text.Json;
using System.Text.Encodings.Web;

var inputFilePath =  "../encoder-test-java/src/main/resources/encodingData/basic-html-encoding.txt";

var jsonOptions = new JsonSerializerOptions {
    WriteIndented = true,
    PropertyNamingPolicy = new CustomJsonNamingPolicy(),
    Encoder = JavaScriptEncoder.UnsafeRelaxedJsonEscaping
};

var tests = new List<Test> {
    new Test(inputFilePath, "System.Security.SecurityElement", "Escape"),
    new Test(inputFilePath, "System.Net.WebUtility", "HtmlEncode"),
    new Test(inputFilePath, "System.Net.WebUtility", "UrlEncode"),

    // AntiXSS is .NET standard

    // dotnet add package Microsoft.AspNet.Mvc
    // error CS0120: An object reference is required for the non-static field, method, or property 'HtmlHelper.Encode(string)' [/app/dotnet/dotnet.csproj]
    // new Test(inputFilePath, "System.Web.Mvc.HtmlHelper", "Encode"),

    // 4.8
    // new Test(inputFilePath, "System.Web.HttpServerUtility", "HtmlEncode"),
    // new Test(inputFilePath, "System.Net.HttpUtility", "HtmlEncode"),
};

foreach(var test in tests) {
    var result = test.Run();
    File.WriteAllText($"../output/dotnet-{test.className}.{test.methodName}.json", JsonSerializer.Serialize(result, jsonOptions));
}