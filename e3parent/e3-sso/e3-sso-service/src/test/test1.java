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
        E3Result zhangsan = userService.checkData("13955107197", 2);
        System.out.println("zhangsan.getData().toString() = " + zhangsan.getData().toString());
    }    
    
    
    
}
