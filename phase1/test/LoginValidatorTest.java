import bookTradeSystem.LoginValidator;
import org.junit.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class LoginValidatorTest {
    // constructor
    @Test(timeout = 50)
    public void testLoginValidator(){
        Map<String, String> user = new HashMap<>();
        user.put("123", "abc");
        user.put("1234", "abcd");
        Map<String, String> admin = new HashMap<>();
        admin.put("321", "cba");
        admin.put("4321", "dcba");
        LoginValidator loginvalidator = new LoginValidator(user, admin);
    }

    // test verify login
    @Test(timeout = 50)
    public void testVerifyLogin(){
        Map<String, String> user = new HashMap<>();
        user.put("123", "abc");
        user.put("1234", "abcd");
        Map<String, String> admin = new HashMap<>();
        admin.put("321", "cba");
        admin.put("4321", "dcba");
        LoginValidator loginvalidator = new LoginValidator(user, admin);
        assertEquals("User", loginvalidator.verifyLogin("123","abc"));
        assertEquals("Admin", loginvalidator.verifyLogin("321","cba"));
        assertEquals("False", loginvalidator.verifyLogin("123","abcd"));
        assertEquals("False", loginvalidator.verifyLogin("12345","abcde"));
        assertEquals("False", loginvalidator.verifyLogin("abc","321"));
    }
}
