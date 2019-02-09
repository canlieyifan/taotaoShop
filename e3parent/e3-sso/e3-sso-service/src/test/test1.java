import com.kk.pojo.User;
import com.kk.sso.UserService;
import com.kk.utils.E3Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/*")
public class test1 {

    @Autowired
    private UserService userService;  
    
    @Test
    public void fun(){
        User user = new User();
        user.setUsername("zhangming");
        user.setPassword("1213");


        E3Result e3Result = userService.checkUserAccount(user);
        System.out.println("e3Result = " + e3Result);

    }
    
    
    
}
