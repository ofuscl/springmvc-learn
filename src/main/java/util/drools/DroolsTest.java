package util.drools;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.*;
import org.drools.command.CommandFactory;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.ClassPathResource;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import org.drools.command.Command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yunfan on 2018/4/24.
 */
public class DroolsTest {


    public static void main(String[] args) {
        DroolsTest test = new DroolsTest();
        test.test1();
    }

    private void test1(){
        KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kb.add(new ClassPathResource("test/test.drl"), ResourceType.DRL);
        Collection collection =kb.getKnowledgePackages();
        KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
        knowledgeBase.addKnowledgePackages(collection);
        StatefulKnowledgeSession statefulKnowledgeSession = knowledgeBase.newStatefulKnowledgeSession();
        Customer cus1 = new Customer();
        cus1.setName("张一");
        Customer cus2 = new Customer();
        cus2.setName("李二");
        Customer cus3 = new Customer();
        cus3.setName("颜三");
        Customer cus4 = new Customer();
        cus4.setName("张四");
        statefulKnowledgeSession.insert(cus1);
        statefulKnowledgeSession.insert(cus2);
        statefulKnowledgeSession.insert(cus3);
        statefulKnowledgeSession.insert(cus4);
        statefulKnowledgeSession.fireAllRules(1);
        statefulKnowledgeSession.dispose();
        System.out.println("end...........................");
    }

    private void test2(){
        KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kb.add(new ClassPathResource("test/test.drl"), ResourceType.DRL);
        if(kb.hasErrors()){
            System.out.println("规则有错误");
        }
        KnowledgeBuilderErrors knowledgeBuilderError = kb.getErrors();

        for(Iterator iter = knowledgeBuilderError.iterator();iter.hasNext();){
            System.out.println(iter.next());
        }

        Collection<KnowledgePackage> collection =kb.getKnowledgePackages();
    }

    private void test3(){

        KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kb.add(ResourceFactory.newClassPathResource("test/test.drl"), ResourceType.DRL);
        Collection<KnowledgePackage> kpackages =kb.getKnowledgePackages();

        KnowledgeBaseConfiguration kbConfig = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        kbConfig.setProperty("org.drools.sequential","true");
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kbConfig);
        kbase.addKnowledgePackages(kpackages);

        StatelessKnowledgeSession statelessKnowledgeSession = kbase.newStatelessKnowledgeSession();
        Customer cus1 = new Customer();
        cus1.setName("张一");
        Customer cus2 = new Customer();
        cus2.setName("李二");
        Customer cus3 = new Customer();
        cus3.setName("颜三");
        Customer cus4 = new Customer();
        cus4.setName("张四");
        List list = new ArrayList<>();
        list.add(cus1);
        list.add(cus2);
        list.add(cus3);
        list.add(cus4);
        statelessKnowledgeSession.execute(list);


        System.out.println("end...........................");
    }

    private void test4(){

        KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kb.add(ResourceFactory.newClassPathResource("test/test.drl"), ResourceType.DRL);
        Collection<KnowledgePackage> kpackages =kb.getKnowledgePackages();

        KnowledgeBaseConfiguration kbConfig = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        kbConfig.setProperty("org.drools.sequential","true");
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kbConfig);
        kbase.addKnowledgePackages(kpackages);

        StatelessKnowledgeSession statelessKnowledgeSession = kbase.newStatelessKnowledgeSession();
        Customer cus1 = new Customer();
        cus1.setName("张一");
        cus1.setAge(30);
        Customer cus2 = new Customer();
        cus2.setName("李二");
        cus2.setAge(40);
        Customer cus3 = new Customer();
        cus3.setName("颜三");
        cus3.setAge(50);
        Customer cus4 = new Customer();
        cus4.setName("张四");
        cus4.setAge(60);
        List<Command> list = new ArrayList<>();
        list.add(CommandFactory.newInsert(cus1));
        list.add(CommandFactory.newInsert(cus2));
        list.add(CommandFactory.newSetGlobal("key3",cus3));
        list.add(CommandFactory.newSetGlobal("key4",cus4));
        statelessKnowledgeSession.execute(list);


        System.out.println("end...........................");
    }
}
