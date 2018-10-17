package base.aop;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public void add(){
        System.out.println("UserService add()");
    }

    public boolean delete(){
        System.out.println("UserService delete()");
        return true;
    }

    public void edit(){
        System.out.println("UserService edit()");
        int i = 5/0;
    }


}
