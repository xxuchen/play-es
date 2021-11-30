package priv.xuchen.base;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAppBase {

    @Before
    public void init(){
        System.out.println("============[App]测试开始=============");
    }

    @After
    public void after(){
        System.out.println("============[App]测试结束=============");
    }
}
