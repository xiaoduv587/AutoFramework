//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.SneakyThrows;
//
//import java.io.File;
//
//public final class UserTestData {
//  private UserTestData() {
//  }
//
//  @SneakyThrows
//  public static UserDetails getUserDetails() {
//    return new ObjectMapper()
//      .readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/post-user.json"), UserDetails.class)
//      .setName("Amuthan");
//
//  }
//}
