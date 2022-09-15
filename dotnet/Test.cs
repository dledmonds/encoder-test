using System.Text.Json;
using System.Text.Json.Serialization;
using System.Text.Encodings.Web;
using System;
using System.Reflection;

public class Test {
    private string inputFilePath;
    public string className;
    public string methodName;

    public Test(string inputFilePath, string className, string methodName) {
        this.inputFilePath = inputFilePath;
        this.className = className;
        this.methodName = methodName;
    }

    public dynamic Run() {
        var results = new Dictionary<string, string>();

        using (var reader = new StreamReader(this.inputFilePath))
        {
            string? line;
            while ((line = reader.ReadLine()) != null) {
                // Convert non-printing characters from template format
                var test = line.Replace("{CR}", "\r").Replace("{LF}", "\n");

                // Perform the test
                var result = (string) Type.GetType(this.className).InvokeMember(this.methodName, BindingFlags.InvokeMethod | BindingFlags.Public |
                BindingFlags.Static, null, null, new object[]{test});

                // Convert non-printing characters to template format
                results[line] = result.Replace("\r", "{CR}").Replace("\n", "{LF}");
            }
        }

        return ToResult(results);
    }

    private dynamic ToResult(Dictionary<string, string> results) {
        return new {
            generatedDate = DateTimeOffset.Now.ToUnixTimeMilliseconds(),
            properties = new {
                javaDOTversion = "dotnet 6.0.400",
                osDOTname = "Linux"
            },
            results = results,
            name = $"{this.className}.{this.methodName}",
            description = this.className,
            dataFile = Path.GetFileName(this.inputFilePath),
            tags = new string[] { "dotnet-encoder", "113", "117", this.className },
            encoder = new {
                library = this.className,
                className = this.className,
                methodName = this.methodName
            }
        };
    }
}