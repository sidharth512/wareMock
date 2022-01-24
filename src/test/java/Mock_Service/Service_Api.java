package Mock_Service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class Service_Api {
    private static final String HOST="local host";
    private static final int PORT= 9999;
    private static WireMockServer server=new WireMockServer(options().port(9999));
    public static RequestSpecification httprequest;

    @BeforeClass
    public  void setup() {
        //Start the Wiremock server
        server.start();
        WireMock.configureFor(HOST,PORT);
        //creating the mockresponse
        ResponseDefinitionBuilder mockresponse  =new ResponseDefinitionBuilder();
       mockresponse.withStatus(200);
        mockresponse.withHeader("Content-type","application/json");
       mockresponse.withBody("user1");
        //Creating the mockrequest
        WireMock.stubFor(WireMock.get("/emps/1").willReturn(mockresponse));

    }
  //  @Test
    public void Tc1(){
        String uri=("http://localhost:"+PORT+"/emps/1");
        System.out.println("the mockservice is"+uri);
        RestAssured.baseURI="http://localhost:9999/" +
                "";
        httprequest=RestAssured.given();
        Response response=httprequest.request(Method.GET,"/emps/1");
        int code=response.statusCode();
        Assert.assertEquals(code,200);
        response.then().statusCode(200).log().all();
    }
    @Test
    public void testcode(){
        String testApi = "http://localhost:" + PORT + "/emps/1" ;
        System.out.println("service to be hit:" + testApi);
        Response response=RestAssured.given().get("http://localhost:9999/emps/1").then().statusCode(200).
                extract().response();
        System.out.println(response);
    }
}
