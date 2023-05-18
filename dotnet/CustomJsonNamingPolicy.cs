using System.Text.Json;

public class CustomJsonNamingPolicy : JsonNamingPolicy
{
  public override string ConvertName(string name)
  {
    return name.Replace("DOT", ".");
  }
}