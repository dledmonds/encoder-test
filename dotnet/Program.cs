using System.Text.Json;
using System.Text.Json.Serialization;
using System.Text.Encodings.Web;

var fileName =  "basic-html-encoding.txt";
var results = new Dictionary<string, string>();

using (var reader = new StreamReader($"../encoder-test-java/src/main/resources/encodingData/{fileName}"))
{
    string? line;
    while ((line = reader.ReadLine()) != null) {
        // Convert non-printing characters from template format
        var test = line.Replace("{CR}", "\r").Replace("{LF}", "\n");

        // Perform the test
        var result = System.Security.SecurityElement.Escape(test);

        // Convert non-printing characters to template format
        result = result.Replace("\r", "{CR}").Replace("\n", "{LF}");

        results[line] = result;
    }
}

var output = new {
    generatedDate = DateTimeOffset.Now.ToUnixTimeMilliseconds(),
    properties = new {
        javaDOTversion = "dotnet 6.0.400",
        osDOTname = "Linux"
    },
    results = results,
    name = "System.Security.SecurityElement.Escape",
    description = "System.Security.SecurityElement.Escape",
    dataFile = fileName,
    tags = new string[] { "dotnet-encoder", "113", "117", "System.Security.SecurityElement.Escape" },
    encoder = new {
        library = "System.Security.SecurityElement.Escape",
        className = "System.Security.SecurityElement.Escape",
        methodName = "Escape"
    }
};

var jsonOptions = new JsonSerializerOptions {
    WriteIndented = true,
    PropertyNamingPolicy = new CustomJsonNamingPolicy(),
    Encoder = JavaScriptEncoder.UnsafeRelaxedJsonEscaping
};

File.WriteAllText("../output/dotnet-System.Security.SecurityElement.Escape.json", JsonSerializer.Serialize(output, jsonOptions));
